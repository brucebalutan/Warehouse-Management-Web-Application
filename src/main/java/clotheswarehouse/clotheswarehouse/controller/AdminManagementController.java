package clotheswarehouse.clotheswarehouse.controller;

import clotheswarehouse.clotheswarehouse.model.Item;
import clotheswarehouse.clotheswarehouse.model.dto.DistributionCentreDto;
import clotheswarehouse.clotheswarehouse.model.form.ItemRequestForm;
import clotheswarehouse.clotheswarehouse.repository.ItemRepository;
import clotheswarehouse.clotheswarehouse.repository.ItemRepositoryPaginated;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/adminManagement")
@CrossOrigin(origins = "http://localhost:8082")
public class AdminManagementController {
    private static final int PAGE_SIZE = 6;
    private final RestTemplate restTemplate;

    private DistributionCentreDto[] centres; // Declare as a class-level variable


    private ItemRepository itemRepository;

    private ItemRepositoryPaginated itemRepositoryPaginated;

    public AdminManagementController(ItemRepository itemRepository,
                              ItemRepositoryPaginated itemRepositoryPaginated,
                                     RestTemplate restTemplate) {
        this.itemRepository = itemRepository;
        this.itemRepositoryPaginated = itemRepositoryPaginated;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String showItems(Model model) {
        ResponseEntity<DistributionCentreDto[]> response = restTemplate.getForEntity(
                "http://localhost:8082/api/distribution",
                DistributionCentreDto[].class
        );

        centres = response.getBody();

        model.addAttribute("centres", Arrays.asList(centres));
        model.addAttribute("requestForm", new ItemRequestForm());


        return "adminManagement";}

    @ModelAttribute
    public void items(Model model) {
        var itemPage = itemRepositoryPaginated.findAll(PageRequest.of(0,PAGE_SIZE));
        model.addAttribute("items", itemPage);
        model.addAttribute("currentPage", itemPage.getNumber());
        model.addAttribute("totalPages", itemPage.getTotalPages());
    }

    @ModelAttribute("brands")
    public Item.Brand[] populateBrands() {
        return Item.Brand.values();
    }

    @PostMapping("/processRequest")
    public String processItemRequest(@ModelAttribute ItemRequestForm requestForm, Model model) {
        // Get the closest distribution centre
        // Retrieve item information from the form
        String itemName = requestForm.getName();
        Item.Brand brand = requestForm.getBrand();
        int quantity = requestForm.getQuantity();
        // Fetch the item details from all distribution centers
        ResponseEntity<List<DistributionCentreDto>> responseEntity = restTemplate.exchange(
                "http://localhost:8082/api/distribution",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DistributionCentreDto>>() {}
        );

        List<DistributionCentreDto> allCentres = responseEntity.getBody();

        // Find the centres that have the requested item
        List<DistributionCentreDto> centresWithItem = allCentres.stream()
                .filter(centre -> centre.getItems().stream()
                        .anyMatch(item -> item.getName().equals(itemName) && item.getBranding() == brand))
                .collect(Collectors.toList());
        DistributionCentreDto closestCentre = findClosestCentre(10.0, 10.0, centresWithItem);

        if (closestCentre != null) {


            try {
                // Fetch the item details from the distribution center
                ResponseEntity<List<Item>> responseEntityItem = restTemplate.exchange(
                        "http://localhost:8082/api/distribution/{centreId}/items",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Item>>() {},
                        closestCentre.getId()
                );



                List<Item> itemsInDistributionCentre = responseEntityItem.getBody();

                // Find the item in the distribution centre by name and brand
                // Find the item in the distribution centre by name and brand
                Optional<Item> itemInDistributionCentre = itemsInDistributionCentre.stream()
                        .filter(item -> {
                            System.out.println("Item Name: " + item.getName() + ", Requested Name: " + itemName);
                            System.out.println("Item Brand: " + item.getBranding() + ", Requested Brand: " + brand);
                            return item.getName().equals(itemName) && item.getBranding().equals(brand);
                        })
                        .findFirst();

                if (itemInDistributionCentre.isPresent()) {
                    Item existingItem = itemInDistributionCentre.get();


                    // Update the item quantity
                    existingItem.setQuantity(existingItem.getQuantity() - quantity);

                    // Update the item directly using the API endpoint on distribution center
                    restTemplate.put("http://localhost:8082/api/distribution/{centreId}/items/{itemId}",
                            existingItem, closestCentre.getId(), existingItem.getId());

                    Item itemTwo = restTemplate.getForObject("http://localhost:8080/api/items/findByName/{name}", Item.class, itemName);
                    int quan = itemTwo.getQuantity();

                    // Update the item quantity in the item service
                    existingItem.setQuantity(quan + quantity);

                    restTemplate.put("http://localhost:8080/api/items/findName/{name}",
                            existingItem, existingItem.getName());

                    return "redirect:/adminManagement/success";
                } else {
                    // Handle case where the item is not found in the distribution centre
                    System.out.println("error, item not found");
                    model.addAttribute("error", "Item not found in the distribution centre.");
                    return "redirect:/adminManagement/error";
                }
            } catch (RestClientException e) {
                // Handle RestClientException, for example, log the error
                e.printStackTrace();
                System.out.println("error, communicating with api");
                model.addAttribute("error", "Error communicating with the APIs.");
                return "redirect:/adminManagement/error";
            }
        } else {
            // Redirect to the error page with a message
            System.out.println("error, stock couldnt be replenehist");
            model.addAttribute("error", "Stock couldn’t be replenished.");
            return "redirect:/adminManagement/error";
        }
    }



    @GetMapping("/switchPage")
    public String switchPage(Model model,
                             @RequestParam("pageToSwitch") Optional<Integer> pageToSwitch) {
        var page = pageToSwitch.orElse(0);
        var totalPages = (int) model.getAttribute("totalPages");
        if (page < 0 || page >= totalPages) {
            return "adminManagement";
        }
        var itemPage = itemRepositoryPaginated.findAll(PageRequest.of(pageToSwitch.orElse(0), PAGE_SIZE));
        model.addAttribute("items", itemPage.getContent());
        model.addAttribute("currentPage", itemPage.getNumber());
        return "adminManagement";

    }
    @PostMapping("/deleteItem")
    public String deleteItem(@RequestParam("itemId") Long itemId) {
        // Perform deletion in the backend
        itemRepository.deleteById(itemId);
        return "redirect:/adminManagement";
    }

    private DistributionCentreDto findClosestCentre(double warehouseLat, double warehouseLon, List<DistributionCentreDto> centres) {
        DistributionCentreDto closestCentre = null;
        double minDistance = Double.MAX_VALUE;
        System.out.println("Warehouse Coordinates: " + warehouseLat + ", " + warehouseLon);
        for (DistributionCentreDto centre : centres) {
            double distance = calculateDistance(warehouseLat, warehouseLon, centre.getLatitude(), centre.getLongitude());
            System.out.println("Centre " + centre.getId() + " Coordinates: " + centre.getLatitude() + ", " + centre.getLongitude());
            if (distance < minDistance) {
                minDistance = distance;
                closestCentre = centre;
            }
        }
        System.out.println(closestCentre);
        return closestCentre;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Haversine formula to calculate distance between two coordinates
        double R = 6371; // Radius of the Earth in kilometers

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }


    private DistributionCentreDto findDistributionCentreById(int distributionCentreId) {
        for (DistributionCentreDto centre : centres) {
            if (centre.getId() == distributionCentreId) {
                return centre;
            }
        }
        return null; // Distribution centre not found
    }

    @GetMapping("/success")
    public String successPage() {
        return "success";
    }

    @GetMapping("/error")
    public String errorPage(Model model) {
        // You can customize the error message to display on the error page
        String errorMessage = (String) model.getAttribute("error");
        model.addAttribute("error", errorMessage != null ? errorMessage : "An error occurred.");
        return "error";
    }
}
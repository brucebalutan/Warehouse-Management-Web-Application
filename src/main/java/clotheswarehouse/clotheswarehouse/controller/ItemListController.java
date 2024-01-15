package clotheswarehouse.clotheswarehouse.controller;

import clotheswarehouse.clotheswarehouse.model.Item;
import clotheswarehouse.clotheswarehouse.model.dto.ItemSearchByBrand;
import clotheswarehouse.clotheswarehouse.repository.ItemRepository;
import clotheswarehouse.clotheswarehouse.repository.ItemRepositoryPaginated;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/itemList")
public class ItemListController {
    private static final int PAGE_SIZE = 6;
    private ItemRepository itemRepository;

    private ItemRepositoryPaginated itemRepositoryPaginated;

    public ItemListController(ItemRepository itemRepository,
                              ItemRepositoryPaginated itemRepositoryPaginated) {
        this.itemRepository = itemRepository;
        this.itemRepositoryPaginated = itemRepositoryPaginated;
    }

    @GetMapping
    public String showItems(Model model) {return "itemList";}

    @ModelAttribute
    public void items(Model model) {
        var itemPage = itemRepositoryPaginated.findAll(PageRequest.of(0,PAGE_SIZE));
        model.addAttribute("items", itemPage);
        model.addAttribute("currentPage", itemPage.getNumber());
        model.addAttribute("totalPages", itemPage.getTotalPages());
    }

    @ModelAttribute
    public void itemsByBrand(Model model) {
        model.addAttribute("itemsByBrand", new ItemSearchByBrand());
    }

    @ModelAttribute("brands")
    public Item.Brand[] populateBrands() {
        return Item.Brand.values();
    }

    @PostMapping("/searchItemsByBrand")
    public String searchItemsByBrand(@ModelAttribute ItemSearchByBrand itemSearchByBrand,
                                         Model model) {
        var brand = itemSearchByBrand.getBrand();
        List<Item> items = itemRepository.findByBrandingAndYearOfCreation(brand, 2022);
        model.addAttribute("items", items);
        return "itemList";
    }

    @PostMapping("/sortItemsByBrand")
    public String sortItemsByBrand(Model model, @RequestParam("pageToSwitch") Optional<Integer> pageToSwitch) {
        var page = pageToSwitch.orElse(0);
        var totalPages = (int) model.getAttribute("totalPages");

        if (page < 0 || page >= totalPages) {
            return "itemList";
        }

        // Fetch all sorted items
        List<Item> sortedItems = itemRepository.findByOrderByBranding();

        // Calculate the start and end indexes for the current page
        int start = page * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, sortedItems.size());

        // Slice the items for the current page
        List<Item> items = sortedItems.subList(start, end);

        model.addAttribute("items", items);
        model.addAttribute("currentPage", page);

        return "itemList";
    }

    @GetMapping("/switchPage")
    public String switchPage(Model model,
                             @RequestParam("pageToSwitch") Optional<Integer> pageToSwitch) {
        var page = pageToSwitch.orElse(0);
        var totalPages = (int) model.getAttribute("totalPages");
        if (page < 0 || page >= totalPages) {
            return "itemList";
        }
        var itemPage = itemRepositoryPaginated.findAll(PageRequest.of(pageToSwitch.orElse(0), PAGE_SIZE));
        model.addAttribute("items", itemPage.getContent());
        model.addAttribute("currentPage", itemPage.getNumber());
        return "itemList";

    }
}

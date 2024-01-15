package clotheswarehouse.clotheswarehouse.controller;

import clotheswarehouse.clotheswarehouse.model.Item;
import clotheswarehouse.clotheswarehouse.model.dto.ItemDto;
import clotheswarehouse.clotheswarehouse.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "http://localhost:8080")
public class ItemController {

    private final ItemRepository itemRepository;
    private final RestTemplate restTemplate;

    private static final String DISTRIBUTION_API_URL = "http://localhost:8082/api/distribution";

    @Autowired
    public ItemController(ItemRepository itemRepository, RestTemplate restTemplate) {
        this.itemRepository = itemRepository;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<List<ItemDto>> getAllItems() {
        List<ItemDto> itemDtos = StreamSupport
                .stream(itemRepository.findAll().spliterator(), false)
                .map(item -> new ItemDto(
                        item.getId(),
                        item.getName(),
                        item.getBranding(),
                        item.getYearOfCreation(),
                        item.getPrice(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(itemDtos);
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    @GetMapping("/findByName/{name}")
    public Item getItemByName(@PathVariable String name) {
        return itemRepository.findByName(name).get(0);
    }

    @PostMapping
    public Item addItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item updatedItem) {
        Item existingItem = itemRepository.findById(id).orElse(null);

        if (existingItem != null) {
            // Update the existing item properties
            existingItem.setName(updatedItem.getName());
            existingItem.setBranding(updatedItem.getBranding());
            existingItem.setYearOfCreation(updatedItem.getYearOfCreation());
            existingItem.setPrice(updatedItem.getPrice());
            existingItem.setQuantity(updatedItem.getQuantity());

            return itemRepository.save(existingItem);
        }

        return null; // Handle the case where the item with the given id is not found
    }

    @PutMapping("/findName/{name}")
    public Item updateItemName(@PathVariable String name, @RequestBody Item updatedItem) {
        List<Item> Items = itemRepository.findByName(name);
        Item existingItem = Items.get(0);
        if (existingItem != null) {
            // Update the existing item properties
            existingItem.setName(updatedItem.getName());
            existingItem.setBranding(updatedItem.getBranding());
            existingItem.setYearOfCreation(updatedItem.getYearOfCreation());
            existingItem.setPrice(updatedItem.getPrice());
            existingItem.setQuantity(updatedItem.getQuantity());

            return itemRepository.save(existingItem);
        }

        return null; // Handle the case where the item with the given id is not found
    }


    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
    }

    @PostMapping("/deductAndAddToWarehouse")
    public ResponseEntity<String> deductAndAddToWarehouse(@RequestBody Item item) {
        // Logic to deduct from distribution centre using RestTemplate
        // ...

        // Logic to add to warehouse (ItemRepository)
        Item savedItem = itemRepository.save(item);

        return ResponseEntity.ok("Item deducted and added to the warehouse successfully.");
    }

    // Other methods for communication with other services if needed
}

package clotheswarehouse.clotheswarehouse.controller;

import clotheswarehouse.clotheswarehouse.model.Item;
import clotheswarehouse.clotheswarehouse.model.Item.Brand;
import clotheswarehouse.clotheswarehouse.repository.ItemRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.EnumSet;

@Controller
@Slf4j
@RequestMapping("/addItem")
public class AddItemController {

    @Autowired
    private ItemRepository itemRepository;
    @GetMapping
    public String addItem() {
        return "addItem";
    }

    @ModelAttribute
    public void items(Model model) {
        var items = EnumSet.allOf(Brand.class);
        model.addAttribute("items", items);
        log.info("Items converted to string:  {}", items);
    }
    @ModelAttribute
    public Item item() {
        return Item
                .builder()
                .build();
    }

    @PostMapping
    public String addItem(@Valid Item item, BindingResult result) {
        log.info("Received request to add item: {}", item);
        if (result.hasErrors()) {
            log.info("Processing Item: {}", item);
            return "addItem";
        }
        log.info("Processing Item: {}", item);
        itemRepository.save(item);
        return "redirect:/itemList";
    }

}

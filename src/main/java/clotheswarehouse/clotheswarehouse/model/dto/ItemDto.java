package clotheswarehouse.clotheswarehouse.model.dto;
import clotheswarehouse.clotheswarehouse.model.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private int id;
    private String name;
    private Item.Brand branding;
    private int yearOfCreation;
    private BigDecimal price;
    private int quantity;

    // Constructor to convert from Item entity to ItemDto
    public ItemDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.branding = item.getBranding();
        this.yearOfCreation = item.getYearOfCreation();
        this.price = item.getPrice();
        this.quantity = item.getQuantity();
    }
}

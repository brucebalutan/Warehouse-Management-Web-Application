package clotheswarehouse.clotheswarehouse.model.form;

import clotheswarehouse.clotheswarehouse.model.Item;
import lombok.Data;

@Data
public class ItemRequestForm {
    private String name;
    private Item.Brand brand;
    private int quantity;
}

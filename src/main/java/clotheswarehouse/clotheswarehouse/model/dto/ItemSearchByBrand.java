package clotheswarehouse.clotheswarehouse.model.dto;

import clotheswarehouse.clotheswarehouse.model.Item;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemSearchByBrand {
    public int year_of_creation;
    public Item.Brand brand;
}

package clotheswarehouse.clotheswarehouse.repository;

import clotheswarehouse.clotheswarehouse.model.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ItemRepository extends CrudRepository<Item, Long> {
    List<Item> findByBranding(Item.Brand brand);
    List<Item> findByNameAndBranding(String name, Item.Brand brand);
    List<Item> findByName(String Name);

    List<Item> findByBrandingAndYearOfCreation(Item.Brand brand, int yearOfCreation);
    List<Item> findByOrderByBranding();
}

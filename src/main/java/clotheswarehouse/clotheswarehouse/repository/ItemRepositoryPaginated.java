package clotheswarehouse.clotheswarehouse.repository;

import clotheswarehouse.clotheswarehouse.model.Item;
import org.springframework.data.repository.PagingAndSortingRepository;
public interface ItemRepositoryPaginated extends PagingAndSortingRepository<Item, Long> {
}

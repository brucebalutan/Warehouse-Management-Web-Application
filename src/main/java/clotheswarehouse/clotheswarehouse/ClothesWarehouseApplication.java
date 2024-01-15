package clotheswarehouse.clotheswarehouse;

import clotheswarehouse.clotheswarehouse.controller.HomeController;
import clotheswarehouse.clotheswarehouse.model.Item;
import clotheswarehouse.clotheswarehouse.model.Item.Brand;
import clotheswarehouse.clotheswarehouse.repository.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@SpringBootApplication
public class ClothesWarehouseApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ClothesWarehouseApplication.class, args);
		HomeController controller = context.getBean(HomeController.class);
	}


	@Bean public RestTemplate restTemplate(){ return new RestTemplate(); }

	@Bean
	public CommandLineRunner dataLoader(ItemRepository repository) {
		return args -> {
			repository.save(Item.builder()
					.name("Hoodie One")
					.branding(Brand.Balenciaga)
					.yearOfCreation(2022)
					.price(new BigDecimal(1001)).quantity(0).build());

			repository.save(Item.builder()
					.name("Shirt One")
					.branding(Brand.Stone_Island)
					.yearOfCreation(2022)
					.price(new BigDecimal(1005)).quantity(1).build());

			repository.save(Item.builder()
					.name("Pants One")
					.branding(Brand.Dior)
					.yearOfCreation(2022)
					.price(new BigDecimal(1050)).quantity(2).build());

			repository.save(Item.builder()
					.name("Hoodie Two")
					.branding(Brand.Gucci)
					.yearOfCreation(2022)
					.price(new BigDecimal(1030)).quantity(0).build());

			repository.save(Item.builder()
					.name("Hoodie Two")
					.branding(Brand.Balenciaga)
					.yearOfCreation(2023)
					.price(new BigDecimal(1003)).quantity(0).build());

			repository.save(Item.builder()
					.name("Hoodie Three")
					.branding(Brand.Dior)
					.yearOfCreation(2023)
					.price(new BigDecimal(1003)).quantity(1).build());

			repository.save(Item.builder()
					.name("Shirt Two")
					.branding(Brand.Stone_Island)
					.yearOfCreation(2023)
					.price(new BigDecimal(1003)).quantity(0).build());

			repository.save(Item.builder()
					.name("Socks One")
					.branding(Brand.Gucci)
					.yearOfCreation(2023)
					.price(new BigDecimal(1034)).quantity(1).build());

			repository.save(Item.builder()
					.name("Socks Two")
					.branding(Brand.Gucci)
					.yearOfCreation(2022)
					.price(new BigDecimal(1044)).quantity(0).build());
			repository.save(Item.builder()
					.name("Socks Three")
					.branding(Brand.Gucci)
					.yearOfCreation(2022)
					.price(new BigDecimal(1034)).quantity(2).build());

			repository.save(Item.builder()
					.name("Socks Four")
					.branding(Brand.Gucci)
					.yearOfCreation(2023)
					.price(new BigDecimal(1044)).quantity(1).build());
		};
	}
}

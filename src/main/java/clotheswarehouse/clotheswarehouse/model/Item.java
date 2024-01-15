package clotheswarehouse.clotheswarehouse.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String name;
    private Brand branding;
    @NotNull
    @Min(value=2022)
    private int yearOfCreation;
    @NotNull
    @DecimalMin(value="1000.00", inclusive=false)
    private BigDecimal price;
    private int quantity;

    public enum Brand {
        Balenciaga("Balenciaga"), Stone_Island("Stone Island"), Dior("Dior"), Gucci("Gucci");

        private String title;

        private Brand(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}

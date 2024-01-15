package clotheswarehouse.clotheswarehouse.model.dto;

import clotheswarehouse.clotheswarehouse.model.Item;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DistributionCentreDto {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private List<Item> items;

    public DistributionCentreDto(int id, String name, double latitude, double longitude, List<Item> items) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.items = items;
    }

    // Getters and setters...
}
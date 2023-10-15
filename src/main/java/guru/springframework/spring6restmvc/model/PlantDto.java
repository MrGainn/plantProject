package guru.springframework.spring6restmvc.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class PlantDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID plantId;
    private String plantName;
}

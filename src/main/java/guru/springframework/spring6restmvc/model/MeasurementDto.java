package guru.springframework.spring6restmvc.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MeasurementDto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer measurementId;

    private LocalDateTime date;

    private Float temp;

    private Float humidity;

    private Float uv;

}

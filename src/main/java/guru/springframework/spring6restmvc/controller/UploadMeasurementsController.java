package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.Plant;
import guru.springframework.spring6restmvc.mappers.PlantMapper;
import guru.springframework.spring6restmvc.model.MeasurementDto;
import guru.springframework.spring6restmvc.model.PlantDto;
import guru.springframework.spring6restmvc.services.MeasurementService;
import guru.springframework.spring6restmvc.services.PlantService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/measurement")
public class UploadMeasurementsController {

    private final MeasurementService measurementService;

    private final PlantService plantService;

    private final PlantMapper plantMapper;

    @GetMapping("/plantId")
    public UUID listMeasurements(){

        return UUID.randomUUID();
    }

//    @GetMapping
//    public UUID getPlantId(){
//
//        return measurementService.getFirstMeasurementId();
//    }


    @PostMapping(value = "{plantId}")
    public ResponseEntity handlePost(@PathVariable UUID plantId, @RequestBody MeasurementDto measurement){

        Optional<PlantDto> plantOptional = plantService.getPlantById(plantId);
        if (plantOptional.isEmpty()){
            return (ResponseEntity) ResponseEntity.status(HttpStatus.CONFLICT);
        }
        Plant plant = plantMapper.plantDtoToPlant(plantOptional.get());

        MeasurementDto Measurement = measurementService.saveNewMeasurement(plant, measurement);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/plant/");

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PatchMapping("{measurementId}")
    public ResponseEntity patchMeasurementById(@PathVariable("measurementId") UUID measurementId, @RequestBody MeasurementDto plant){

        measurementService.patchMeasurementById(measurementId, plant);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{measurementId}")
    public ResponseEntity deleteById(@PathVariable("measurementId") UUID measurementId){

        measurementService.deleteMeasurementById(measurementId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
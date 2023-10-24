package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.entities.Measurement;
import guru.springframework.spring6restmvc.entities.Plant;
import guru.springframework.spring6restmvc.mappers.MeasurementMapper;
import guru.springframework.spring6restmvc.model.MeasurementDto;
import guru.springframework.spring6restmvc.repositories.MeasurementRepository;
import guru.springframework.spring6restmvc.repositories.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class MeasurementServiceJPA implements MeasurementService {

    private final MeasurementRepository measurementRepository;

    private final PlantRepository plantRepository;

    private final MeasurementMapper measurementMapper;

    @Override
    public List<MeasurementDto> listAllMeasurementsByPlant(Plant plant) {
        return measurementRepository.findAllByPlant(plant)
                .stream()
                .map(measurement -> {
                    MeasurementDto measurementDto = measurementMapper.measurementToMeasurementDto(measurement);
                    measurementDto.setPlant(null);
                    return measurementDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MeasurementDto> listAllMeasurements() {
        return measurementRepository.findAll().stream()
                .map(measurement -> {
                    MeasurementDto measurementDto = measurementMapper.measurementToMeasurementDto(measurement);
                    measurementDto.setPlant(null);
                    return measurementDto;
                }).collect(Collectors.toList());



    }

    @Override
    public Optional<MeasurementDto> getMeasurementById(UUID measurementId) {
        MeasurementDto measurementDto = measurementMapper
                .measurementToMeasurementDto(measurementRepository.findById(measurementId).orElse(null));

        measurementDto.setPlant(null);

        return Optional.of(measurementDto);
    }

    @Override
    public MeasurementDto saveNewMeasurement(Plant plant, MeasurementDto measurement) {

        Measurement savedMeasurement = measurementRepository.save(measurementMapper.measurementDtoToMeasurement(measurement));
        Plant savedPlant = plantRepository.save(plant);

        savedMeasurement.setPlant(savedPlant);

        Set<Measurement> measurements = savedPlant.getMeasurements();
        measurements.add(savedMeasurement);
        savedPlant.setMeasurements(measurements);

        measurementRepository.save(savedMeasurement);
        plantRepository.save(savedPlant);

        return measurementMapper.measurementToMeasurementDto(savedMeasurement);
    }

    @Override
    public Boolean deleteMeasurementById(UUID measurementId) {
        if(measurementRepository.existsById(measurementId)){
            measurementRepository.deleteById(measurementId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<MeasurementDto> patchMeasurementById(UUID measurementId, MeasurementDto measurement) {
        AtomicReference<Optional<MeasurementDto>> atomicReference = new AtomicReference<>();

        measurementRepository.findById(measurementId).ifPresentOrElse(foundMeasurement -> {
            if (measurement.getUv() != null){
                foundMeasurement.setUv(measurement.getUv());
            }
            if (measurement.getTemp() != null){
                foundMeasurement.setTemp(measurement.getTemp());
            }
            if (measurement.getHumidity() != null){
                foundMeasurement.setHumidity(measurement.getHumidity());
            }
            atomicReference.set(Optional.of(measurementMapper
                    .measurementToMeasurementDto((measurementRepository.save(foundMeasurement)))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }
    }


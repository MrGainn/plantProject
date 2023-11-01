package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.entities.Measurement;
import guru.springframework.spring6restmvc.entities.Plant;
import guru.springframework.spring6restmvc.mappers.MeasurementMapper;
import guru.springframework.spring6restmvc.model.MeasurementDto;
import guru.springframework.spring6restmvc.repositories.MeasurementRepository;
import guru.springframework.spring6restmvc.repositories.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    private final static int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 20;

    private final PlantRepository plantRepository;

    private final MeasurementMapper measurementMapper;

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize){
        int queryPageNumber;
        int queryPageSize;

        if(pageNumber != null && pageNumber > 0){
            queryPageNumber = pageNumber - 1;
        }
        else {
            queryPageNumber = DEFAULT_PAGE;
        }
        if(pageSize != null && pageSize > 0){
            queryPageSize = pageSize - 1;
        }
        else {
            queryPageSize = DEFAULT_PAGE_SIZE;
        }

        Sort sort = Sort.by(Sort.Order.desc("date"));

        return PageRequest.of(queryPageNumber, queryPageSize, sort);

    }


    @Override
    public Page<MeasurementDto> listAllMeasurementsByPlant(Plant plant, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        Page<Measurement> measurementPage = measurementRepository.findAllByPlant(plant, pageRequest);

        return measurementPage.map(measurement -> {
            MeasurementDto measurementDto = measurementMapper.measurementToMeasurementDto(measurement);
            measurementDto.setPlant(null);
            return measurementDto;
        });

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

        savedMeasurement.setPlant(plant);

        Set<Measurement> measurements = plant.getMeasurements();
        measurements.add(savedMeasurement);
        plant.setMeasurements(measurements);

        measurementRepository.save(savedMeasurement);
        plantRepository.save(plant);

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


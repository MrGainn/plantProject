package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.entities.Measurement;
import guru.springframework.spring6restmvc.entities.Plant;
import guru.springframework.spring6restmvc.entities.User;
import guru.springframework.spring6restmvc.mappers.PlantMapper;
import guru.springframework.spring6restmvc.mappers.UserMapper;
import guru.springframework.spring6restmvc.model.PlantDto;
import guru.springframework.spring6restmvc.repositories.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Created by jt, Spring Framework Guru.
 */
@Service
@Primary
@RequiredArgsConstructor
public class PlantServiceJPA implements PlantService {
    private final PlantRepository plantRepository;
    private final PlantMapper plantMapper;

    private final UserMapper userMapper;

    private final UserService userService;

    @Override
    public List<PlantDto> listAllPlants() {
        return plantRepository.findAll()
                .stream()
                .map(plantMapper::plantToPlantDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PlantDto> getPlantById(UUID id) {
        return Optional.ofNullable(plantMapper.plantToPlantDto(plantRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public PlantDto saveNewPlant(User user, PlantDto plant) {

        plant.setUsers(new HashSet<>(Collections.singletonList(user)));

        Plant savedPlant = plantRepository.save(plantMapper.plantDtoToPlant(plant));

        Set<Plant> plants = user.getPlants();
        plants.add(savedPlant);
        user.setPlants(plants);

        userService.saveNewUser(userMapper.UserToUserDto(user));

        return plantMapper.plantToPlantDto(savedPlant);
    }

    @Override
    public Optional<PlantDto> updatePlantById(UUID plantId, PlantDto plant) {
        AtomicReference<Optional<PlantDto>> atomicReference = new AtomicReference<>();

        plantRepository.findById(plantId).ifPresentOrElse(foundPlant -> {
            foundPlant.setPlantName(plant.getPlantName());
            atomicReference.set(Optional.of(plantMapper
                    .plantToPlantDto(plantRepository.save(foundPlant))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }

    @Override
    public Boolean deletePlantById(UUID plantId) {
        if (plantRepository.existsById(plantId)) {
            plantRepository.deleteById(plantId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<PlantDto> patchPlantById(UUID plantId, PlantDto plant) {
        AtomicReference<Optional<PlantDto>> atomicReference = new AtomicReference<>();

        plantRepository.findById(plantId).ifPresentOrElse(foundPlant -> {
            if (StringUtils.hasText(plant.getPlantName())){
                foundPlant.setPlantName(plant.getPlantName());
            }
            atomicReference.set(Optional.of(plantMapper
                    .plantToPlantDto(plantRepository.save(foundPlant))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }

    @Override
    public List<PlantDto> getPlantsByUserId(User user) {
        return plantRepository.findAllByUsers(user)
                .stream()
                .map(plantMapper::plantToPlantDto)
                .collect(Collectors.toList());

    }

    @Override
    public Optional<Measurement> getPlantMeasurement(UUID measurementId) {
        return Optional.empty();
    }
}

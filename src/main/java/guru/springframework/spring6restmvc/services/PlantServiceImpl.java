package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.PlantDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class PlantServiceImpl implements PlantService {

    private Map<UUID, PlantDto> plantMap;

    public PlantServiceImpl(){
//        this.plantMap = new HashMap<>();
//
//       PlantDto plant1 = PlantDto.builder()
//                .plantId(UUID.randomUUID())
//                .plantName("Catus")
//                .build();
//
//        PlantDto plant2 = PlantDto.builder()
//                .plantId(UUID.randomUUID())
//                .plantName("Fiddle Leaf Fig")
//                .build();
//
//       PlantDto plant3 = PlantDto.builder()
//                .plantId(UUID.randomUUID())
//                .plantName("Spider Plant ")
//                .build();
//
//        plantMap.put(plant1.getPlantId(), plant1);
//        plantMap.put(plant2.getPlantId(), plant2);
//        plantMap.put(plant3.getPlantId(), plant3);
    }
    @Override
    public List<PlantDto> listAllPlants() {
        return new ArrayList<>(plantMap.values());
    }

    @Override
    public Optional<PlantDto> getPlantById(UUID plantId) {
        return Optional.ofNullable(plantMap.get(plantId));
    }

    @Override
    public PlantDto saveNewPlant(PlantDto plant) {
        plant.setPlantId(UUID.randomUUID());
        plantMap.put(plant.getPlantId(), plant);
        return plant;
    }

    @Override
    public Optional<PlantDto> updatePlantById(UUID plantId, PlantDto plant) {

        return null;
    }

    @Override
    public Boolean deletePlantById(UUID plantId) {

        return null;
    }

    @Override
    public Optional<PlantDto> patchPlantById(UUID plantId, PlantDto beer) {

        return null;
    }

}

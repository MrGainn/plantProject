package guru.springframework.spring6restmvc.bootstrap;


import guru.springframework.spring6restmvc.entities.Measurement;
import guru.springframework.spring6restmvc.entities.Plant;
import guru.springframework.spring6restmvc.entities.User;
import guru.springframework.spring6restmvc.repositories.MeasurementRepository;
import guru.springframework.spring6restmvc.repositories.PlantRepository;
import guru.springframework.spring6restmvc.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final PlantRepository plantRepository;
    private final UserRepository userRepository;

    private final MeasurementRepository measurementRepository;

    Plant plant1;
    Plant plant2;
    Plant plant3;

    @Override
    public void run(String... args) throws Exception {
        loadPlantData();
        loadUserData();
        loadMeasurement();
    }

    private void loadPlantData() {

        if (plantRepository.count() == 0){
            plant1 = Plant.builder()
                    .plantName("Catus")
                    .build();

            plant2 = Plant.builder()
                    .plantName("Fiddle Leaf Fig")
                    .build();

            plant3 = Plant.builder()
                    .plantName("Spider Plant ")
                    .build();

            plantRepository.save(plant1);
            plantRepository.save(plant2);
            plantRepository.save(plant3);
        }
    }

    private void loadUserData() {
        if (userRepository.count() == 0) {
            User user1 = User.builder()
                    .username("tico")
                    .email("tico@gmail.com")
                    .admin(Boolean.FALSE)
                    .createDate(LocalDateTime.now())
                    .hashedpassword("123")
                    .plants(new HashSet<>(Arrays.asList(plant1, plant2, plant3)))
                    .build();
            User user2 = User.builder()
                    .email("kazi@gmail.com")
                    .username("Kazi")
                    .admin(Boolean.FALSE)
                    .createDate(LocalDateTime.now())
                    .hashedpassword("123")
                    .plants(new HashSet<>(Arrays.asList(plant1)))
                    .build();
            User user3 = User.builder()
                    .email("Nikita@gmail.com")
                    .username("nikita")
                    .admin(Boolean.TRUE)
                    .createDate(LocalDateTime.now())
                    .hashedpassword("123")
                    .plants(new HashSet<>(Arrays.asList(plant2)))
                    .build();

            User user4 = User.builder()
                    .email("Pieter@gmail.com")
                    .username("Pieter")
                    .admin(Boolean.TRUE)
                    .createDate(LocalDateTime.now())
                    .plants(new HashSet<>(Arrays.asList(plant3, plant2)))
                    .hashedpassword("I smoke")
                    .build();

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);
        }
    }

    private void loadMeasurement() {
        if (measurementRepository.count() == 0) {
            Measurement measurement1 = Measurement.builder()
                    .measurementId(UUID.randomUUID())
                    .uv(5F)
                    .humidity(6F)
                    .temp(7f)
                    .water(10f)
                    .plant(plant1)
                    .build();
            Measurement measurement2 = Measurement.builder()
                    .uv(5F)
                    .humidity(7F)
                    .temp(5f)
                    .plant(plant2)
                    .build();
            Measurement measurement3 = Measurement.builder()
                    .measurementId(UUID.randomUUID())
                    .date(LocalDateTime.now())
                    .plant(plant2)
                    .uv(7F)
                    .humidity(9F)
                    .temp(3f)
                    .build();

            Measurement measurement4 = Measurement.builder()
                    .measurementId(UUID.randomUUID())
                    .plant(plant2)
                    .uv(2F)
                    .humidity(8F)
                    .temp(5f)
                    .build();

            Measurement measurement5 = Measurement.builder()
                    .measurementId(UUID.randomUUID())
                    .plant(plant2)
                    .uv(6F)
                    .humidity(5F)
                    .temp(6f)
                    .build();

            Measurement measurement6 = Measurement.builder()
                    .measurementId(UUID.randomUUID())
                    .plant(plant2)
                    .uv(7F)
                    .humidity(6F)
                    .temp(7f)
                    .build();
            Measurement measurement7 = Measurement.builder()
                    .measurementId(UUID.randomUUID())
                    .uv(9F)
                    .humidity(6F)
                    .temp(7f)
                    .plant(plant1)
                    .build();
            Measurement measurement8 = Measurement.builder()
                    .measurementId(UUID.randomUUID())
                    .uv(5F)
                    .humidity(3F)
                    .temp(3f)
                    .plant(plant1)
                    .build();
            Measurement measurement9 = Measurement.builder()
                    .measurementId(UUID.randomUUID())
                    .uv(7F)
                    .humidity(6F)
                    .temp(9f)
                    .plant(plant1)
                    .build();

            Measurement measurement10 = Measurement.builder()
                    .measurementId(UUID.randomUUID())
                    .uv(4F)
                    .humidity(7F)
                    .temp(5f)
                    .plant(plant3)
                    .build();
            Measurement measurement11 = Measurement.builder()
                    .measurementId(UUID.randomUUID())
                    .uv(7F)
                    .humidity(6F)
                    .temp(9f)
                    .plant(plant3)
                    .build();
            Measurement measurement12 = Measurement.builder()
                    .measurementId(UUID.randomUUID())
                    .uv(6F)
                    .humidity(4F)
                    .temp(3f)
                    .plant(plant3)
                    .build();



            measurementRepository.save(measurement1);
            measurementRepository.save(measurement2);
            measurementRepository.save(measurement3);
            measurementRepository.save(measurement4);
            measurementRepository.save(measurement5);
            measurementRepository.save(measurement6);
            measurementRepository.save(measurement7);
            measurementRepository.save(measurement8);
            measurementRepository.save(measurement9);
            measurementRepository.save(measurement10);
            measurementRepository.save(measurement11);
            measurementRepository.save(measurement12);


        }
    }
}

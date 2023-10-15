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

    @Override
    public void run(String... args) throws Exception {
        loadPlantData();
        loadUserData();
        loadMeasurement();
    }

    private void loadPlantData() {

        if (plantRepository.count() == 0){
            Plant plant1 = Plant.builder()
                    .plantName("Catus")
                    .build();

            Plant plant2 = Plant.builder()
                    .plantName("Fiddle Leaf Fig")
                    .build();

            Plant plant3 = Plant.builder()
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
                    .id(UUID.randomUUID())
                    .username("Tico")
                    .email("tico@gmail.com")
                    .admin(Boolean.FALSE)
                    .createDate(LocalDateTime.now())
                    .hashedpassword("This is my password")
                    .build();
            User user2 = User.builder()
                    .id(UUID.randomUUID())
                    .email("kazi@gmail.com")
                    .username("Kazi")
                    .admin(Boolean.FALSE)
                    .createDate(LocalDateTime.now())
                    .hashedpassword("dog")
                    .build();
            User user3 = User.builder()
                    .id(UUID.randomUUID())
                    .email("Nikita@gmail.com")
                    .username("nikita")
                    .admin(Boolean.TRUE)
                    .createDate(LocalDateTime.now())
                    .hashedpassword("cat")
                    .build();

            User user4 = User.builder()
                    .id(UUID.randomUUID())
                    .email("Pieter@gmail.com")
                    .username("Pieter lamlich")
                    .admin(Boolean.TRUE)
                    .createDate(LocalDateTime.now())
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
                    .humidity(5F)
                    .build();
            Measurement measurement2 = Measurement.builder()
                    .uv(8F)
                    .humidity(75F)
                    .build();
            Measurement measurement3 = Measurement.builder()
                    .measurementId(UUID.randomUUID())
                    .date(LocalDateTime.now())
                    .uv(587876F)
                    .humidity(521F)
                    .build();


            measurementRepository.save(measurement1);
            measurementRepository.save(measurement2);
            measurementRepository.save(measurement3);

        }
    }
}

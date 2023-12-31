package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Plant;
import guru.springframework.spring6restmvc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface PlantRepository extends JpaRepository<Plant, UUID> {

    List<Plant> findAllByUsers(User user);
}
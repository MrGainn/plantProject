package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.entities.User;
import guru.springframework.spring6restmvc.model.NotificationDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationService {

    List<NotificationDTO> listAllByUserId(User user);

    Optional<NotificationDTO> patchNotificationById(UUID notificationId, NotificationDTO notificationDTO);


}

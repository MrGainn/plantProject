package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.entities.User;
import guru.springframework.spring6restmvc.model.NotificationDTO;

import java.util.List;
import java.util.Optional;

public interface NotificationService {

    List<NotificationDTO> listAllByUserId(User user);

    Optional<NotificationDTO> patchNotificationByUserId(User user, NotificationDTO notificationDTO);


}

package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.entities.Notification;
import guru.springframework.spring6restmvc.entities.User;
import guru.springframework.spring6restmvc.mappers.NotificationMapper;
import guru.springframework.spring6restmvc.model.NotificationDTO;
import guru.springframework.spring6restmvc.model.UserDto;
import guru.springframework.spring6restmvc.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;

    private final NotificationRepository notificationRepository;

    @Override
    public List<NotificationDTO> listAllByUserId(User user) {


        List<Notification> notifications = notificationRepository.findAllByUser(user);

        return notifications.stream()
                .map(notification -> {
                    NotificationDTO notificationDTO = notificationMapper.NotificationToNotificationDto(notification);
                    notificationDTO.setUser(null);
                    return notificationDTO;
                })
                .collect(Collectors.toList());

    }

    @Override
    public Optional<NotificationDTO> patchNotificationById(UUID notificationId, NotificationDTO notificationDTO) {

        AtomicReference<Optional<NotificationDTO>> atomicReference = new AtomicReference<>();

        notificationRepository.findById(notificationId).ifPresentOrElse(foundNotification -> {
            if (StringUtils.hasText(String.valueOf(notificationDTO.getStatus()))){
                foundNotification.setStatus(notificationDTO.getStatus());
            }
            atomicReference.set(Optional.of(notificationMapper
                    .NotificationToNotificationDto(notificationRepository.save(foundNotification))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();

    }
}

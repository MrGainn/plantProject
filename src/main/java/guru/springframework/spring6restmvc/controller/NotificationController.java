package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.User;
import guru.springframework.spring6restmvc.model.NotificationDTO;
import guru.springframework.spring6restmvc.services.CheckAuth;
import guru.springframework.spring6restmvc.services.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    NotificationService notificationService;

    private final CheckAuth checkAuth;

    @GetMapping
    public List<NotificationDTO> listNotifications(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Optional<User> user = checkAuth.authenticationForUser(authentication);
            if (user.isEmpty()){
                return new ArrayList<>();
            }
            User user1 = user.get();

            if (user1.getUserId().equals(UUID.fromString("a7355e4c-0000-0000-0000-ec00b8309ae9"))) {
                return new ArrayList<>();
            }
            return notificationService.listAllByUserId(user1);
        }
        return new ArrayList<>();
    }
}

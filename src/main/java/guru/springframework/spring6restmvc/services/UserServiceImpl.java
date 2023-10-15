package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private Map<UUID, UserDto> userMap;

    public UserServiceImpl(){
//        this.userMap = new HashMap<>();
//
//        UserDto user1 = UserDto.builder()
//                .id(UUID.randomUUID())
//                .username("Tico")
//                .email("tico@gmail.com")
//                .admin(Boolean.FALSE)
//                .createDate(LocalDateTime.now())
//                .hashedpassword("This is my password")
//                .build();
//        UserDto user2 = UserDto.builder()
//                .id(UUID.randomUUID())
//                .email("kazi@gmail.com")
//                .username("Kazi")
//                .admin(Boolean.FALSE)
//                .createDate(LocalDateTime.now())
//                .hashedpassword("dog")
//                .build();
//        UserDto user3 = UserDto.builder()
//                .id(UUID.randomUUID())
//                .email("Nikita@gmail.com")
//                .username("nikita")
//                .admin(Boolean.TRUE)
//                .createDate(LocalDateTime.now())
//                .hashedpassword("cat")
//                .build();
//
//        UserDto user4 = UserDto.builder()
//                .id(UUID.randomUUID())
//                .email("Pieter@gmail.com")
//                .username("Pieter lamlich")
//                .admin(Boolean.TRUE)
//                .createDate(LocalDateTime.now())
//                .hashedpassword("I smoke")
//                .build();
//
//        userMap.put(user1.getId(), user1);
//        userMap.put(user2.getId(), user2);
//        userMap.put(user3.getId(), user3);
//        userMap.put(user4.getId(), user4);

    }
    @Override
    public Optional<UserDto> getUserById(UUID uuid) {
        return Optional.ofNullable(userMap.get(uuid));
    }

    @Override
    public List<UserDto> listAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public UserDto saveNewUser(UserDto user) {
        user.setId(UUID.randomUUID());

        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<UserDto> updateUserById(UUID userId, UserDto user) {

        return null;
    }

    @Override
    public Optional<UserDto> patchUserById(UUID userId, UserDto user) {

        return null;
    }

    @Override
    public Boolean deleteUserById(UUID userId) {

        return null;
    }
}

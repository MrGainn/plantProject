package guru.springframework.spring6restmvc.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    private String username;
    private String email;

    private String hashedpassword;

    private Boolean admin;

    @CreationTimestamp
    private LocalDateTime createDate;
}

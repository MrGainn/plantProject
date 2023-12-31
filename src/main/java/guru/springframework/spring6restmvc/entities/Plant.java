package guru.springframework.spring6restmvc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Plant {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID plantId;

    private Integer age;

    private LocalDateTime lastWater;

    private Integer threshold;

    private String location;

    private Boolean mode;

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String plantName;

    @Builder.Default
    @OneToMany(mappedBy = "plant")
    private Set<Measurement> measurements = new HashSet<>();


    @Builder.Default
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "UserToPlant",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "plantId"))
    private Set<User> users = new HashSet<>();
}

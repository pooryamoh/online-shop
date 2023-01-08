package xyz.poorya.Payment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique=true)
    private String username;

    private String fName;

    private String lName;

    private String phoneNumber;

    private String email;


    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Wallet wallet;
}

package xyz.poorya.onlineshop.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import xyz.poorya.onlineshop.domain.AuditMetadata;

import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity extends AuditMetadata {

    @MongoId
    private String id;

    @Indexed(unique = true)
    private String username;

    private String fName;

    private String lName;

    private String password;

    private String phoneNumber;

    private String email;

    private Location location;

    @DBRef(lazy = true)
    private List<Role> role;


}

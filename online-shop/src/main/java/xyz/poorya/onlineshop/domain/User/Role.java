package xyz.poorya.onlineshop.domain.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Role {

    @MongoId
    private String id;

    private String name;

    public Role(String name) {
        this.name = name;
    }
}

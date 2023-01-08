package xyz.poorya.onlineshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import xyz.poorya.onlineshop.domain.Cart.Cart;
import xyz.poorya.onlineshop.domain.User.UserEntity;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @MongoId
    private String id;

    @DBRef
    private Cart cart;

    private boolean paid;

    @DBRef
    private UserEntity user;

    private int amount;

    private String description;
}

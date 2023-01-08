package xyz.poorya.onlineshop.domain.Cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import xyz.poorya.onlineshop.domain.User.UserEntity;

import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @MongoId
    private String id;

    private int total;

    @DBRef(lazy = true)
    private List<CartItem> cartItems;

    @DBRef(lazy = true)
    private UserEntity user;

    public Cart(List<CartItem> cartItems, UserEntity user) {
        this.cartItems = cartItems;
        this.user = user;
    }
}

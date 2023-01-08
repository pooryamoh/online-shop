package xyz.poorya.onlineshop.domain.Cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import xyz.poorya.onlineshop.domain.Product.Product;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @MongoId
    private String id;

    private int quantity;

    @DBRef(lazy = true)
    private Product product;

    public CartItem(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }
}

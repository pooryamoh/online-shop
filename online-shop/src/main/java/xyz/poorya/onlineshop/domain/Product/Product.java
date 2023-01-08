package xyz.poorya.onlineshop.domain.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import xyz.poorya.onlineshop.domain.AuditMetadata;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Product extends AuditMetadata {
    @MongoId
    private String id;

    private String name;

    private int quantity;

    private int price;

    @DBRef(lazy = true)
    private Category category;

}

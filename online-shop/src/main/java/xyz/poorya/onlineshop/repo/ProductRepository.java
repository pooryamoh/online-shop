package xyz.poorya.onlineshop.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import xyz.poorya.onlineshop.domain.Product.Product;

@Repository
@RestResource(exported = false)
public interface ProductRepository extends CrudRepository<Product, String> {
    boolean existsById(String id);
}

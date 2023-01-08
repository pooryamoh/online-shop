package xyz.poorya.onlineshop.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import xyz.poorya.onlineshop.domain.Cart.CartItem;

@Repository
@RestResource(exported = false)
public interface CartItemRepository extends CrudRepository<CartItem, String> {
}

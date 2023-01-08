package xyz.poorya.onlineshop.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import xyz.poorya.onlineshop.domain.Cart.Cart;
import xyz.poorya.onlineshop.domain.User.UserEntity;

import java.util.Optional;

@Repository
@RestResource(exported = false)
public interface CartRepository extends CrudRepository<Cart, String> {
    Optional<Cart> findCartByUser(UserEntity user);

    Boolean existsByUserUsername(String username);
}

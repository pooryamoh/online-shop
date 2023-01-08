package xyz.poorya.onlineshop.repo.rest;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Repository;
import xyz.poorya.onlineshop.domain.Cart.Cart;

import java.util.Optional;

@Repository
@RepositoryRestResource
@Primary

public interface CartRestRepository extends CrudRepository<Cart, String> {
    @Override
    @PostFilter("hasAuthority('SCOPE_ADMIN') or filterObject.user.username == authentication.name")
    <S extends Cart> S save(S entity);

    @Override
    <S extends Cart> Iterable<S> saveAll(Iterable<S> entities);

    @Override
    Optional<Cart> findById(String s);

    @Override
    boolean existsById(String s);

    @Override
    @PostFilter("hasAuthority('SCOPE_ADMIN') or filterObject.user.username == authentication.name")
    Iterable<Cart> findAll();

    @Override
    Iterable<Cart> findAllById(Iterable<String> strings);

    @Override
    long count();

    @Override
    void deleteById(String s);

    @Override
    void delete(Cart entity);

    @Override
    void deleteAllById(Iterable<? extends String> strings);

    @Override
    void deleteAll(Iterable<? extends Cart> entities);

    @Override
    void deleteAll();
}

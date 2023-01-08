package xyz.poorya.onlineshop.repo.rest;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Repository;
import xyz.poorya.onlineshop.domain.User.UserEntity;

import java.util.Optional;

@Repository
@RepositoryRestResource(path = "users")
@Primary

public interface UserRestRepository extends CrudRepository<UserEntity, String> {

}

package xyz.poorya.onlineshop.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import xyz.poorya.onlineshop.domain.User.Role;

import java.util.Optional;

@Repository
@RestResource(exported = false)
public interface RoleRepository extends CrudRepository<Role, String> {
    Optional<Role> findByName(String name);
}

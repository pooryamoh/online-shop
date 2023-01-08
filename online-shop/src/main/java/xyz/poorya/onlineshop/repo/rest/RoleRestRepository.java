package xyz.poorya.onlineshop.repo.rest;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import xyz.poorya.onlineshop.domain.User.Role;

import java.util.Optional;

@Repository
@RepositoryRestResource
@Primary
@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
public interface RoleRestRepository extends CrudRepository<Role, String> {
    Optional<Role> findByName(String name);
}

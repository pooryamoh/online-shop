package xyz.poorya.onlineshop.repo.rest;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import xyz.poorya.onlineshop.domain.Product.Category;

@Repository
@RepositoryRestResource
@Primary

public interface CategoryRestRepository extends CrudRepository<Category, String> {
}

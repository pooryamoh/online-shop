package xyz.poorya.onlineshop.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import xyz.poorya.onlineshop.domain.Transaction;

@Repository
@RestResource(exported = false)
public interface TransactionRepository extends CrudRepository<Transaction, String> {
}

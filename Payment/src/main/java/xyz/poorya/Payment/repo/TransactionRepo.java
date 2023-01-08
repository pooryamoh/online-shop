package xyz.poorya.Payment.repo;

import xyz.poorya.Payment.domain.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepo extends CrudRepository<Transaction,Long> {
}

package xyz.poorya.Payment.repo;

import xyz.poorya.Payment.domain.Wallet;
import org.springframework.data.repository.CrudRepository;

public interface WalletRepo extends CrudRepository<Wallet,Long> {
}

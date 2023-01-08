package xyz.poorya.Payment.services;

import xyz.poorya.Payment.domain.Transaction;
import xyz.poorya.Payment.domain.UserEntity;
import xyz.poorya.Payment.domain.Wallet;
import xyz.poorya.Payment.dto.TransactionDto;
import xyz.poorya.Payment.dto.TransactionResponseDto;
import xyz.poorya.Payment.rabbitmq.RabbitMQSender;
import xyz.poorya.Payment.repo.UserRepo;
import xyz.poorya.Payment.repo.WalletRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {
    private UserRepo userRepo;

    private WalletRepo walletRepo;

    private RabbitMQSender rabbitMQSender;

    public TransactionService(UserRepo userRepo, WalletRepo walletRepo, RabbitMQSender rabbitMQSender) {
        this.userRepo = userRepo;
        this.walletRepo = walletRepo;
        this.rabbitMQSender = rabbitMQSender;
    }

    public void addTransaction(TransactionDto transactionDto) {
        Optional<UserEntity> userByUsername = userRepo.findByUsername(transactionDto.getUsername());

        Wallet wallet = userByUsername.get().getWallet();

        if (wallet.getBalance() < transactionDto.getAmount()) {
            rabbitMQSender.send(new TransactionResponseDto(false, "Not enough balance",transactionDto.getId()));
        } else {
            wallet.setBalance(wallet.getBalance() - transactionDto.getAmount());
            Transaction transaction = Transaction.builder().paid(true).amount(transactionDto.getAmount()).detail("Paid").referenceId(transactionDto.getId()).build();
            wallet.getTransactions().add(transaction);
            walletRepo.save(wallet);
            rabbitMQSender.send(new TransactionResponseDto(true, "Paid successfully", transactionDto.getId()));

        }

    }
}

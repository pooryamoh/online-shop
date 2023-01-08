package xyz.poorya.onlineshop.service;

import org.springframework.stereotype.Service;
import xyz.poorya.onlineshop.domain.Cart.Cart;
import xyz.poorya.onlineshop.domain.Transaction;
import xyz.poorya.onlineshop.domain.User.UserEntity;
import xyz.poorya.onlineshop.dto.TransactionDto;
import xyz.poorya.onlineshop.exceptions.TransactionException;
import xyz.poorya.onlineshop.rabbitmq.RabbitMQSender;
import xyz.poorya.onlineshop.repo.CartRepository;
import xyz.poorya.onlineshop.repo.TransactionRepository;
import xyz.poorya.onlineshop.repo.UserRepository;
import xyz.poorya.onlineshop.repo.rest.UserRestRepository;

import java.util.Optional;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;

    private UserRestRepository userRestRepository;

    private CartRepository cartRepository;
    private RabbitMQSender rabbitMQSender;
    private final UserRepository userRepository;


    public TransactionService(TransactionRepository transactionRepository, UserRestRepository userRestRepository, CartRepository cartRepository, RabbitMQSender rabbitMQSender,
                              UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRestRepository = userRestRepository;
        this.cartRepository = cartRepository;
        this.rabbitMQSender = rabbitMQSender;
        this.userRepository = userRepository;
    }

    public Transaction payCart(String username) {

        Optional<UserEntity> user = userRepository.findByUsername(username);
        Optional<Cart> cartByUsername = cartRepository.findCartByUser(user.get());
        Transaction transaction = new Transaction();

        if (cartByUsername.isEmpty()) {
            throw new TransactionException("You cart is empty");

        } else {
            Cart cart = cartByUsername.get();
            transaction.setCart(cart);
            transaction.setUser(user.get());
            transaction.setPaid(false);
            transaction.setAmount(cart.getTotal());

            transaction = transactionRepository.save(transaction);

            rabbitMQSender.send(new TransactionDto(transaction.getId(), transaction.getUser().getUsername(), transaction.getAmount()));

        }

        return transaction;

    }
}

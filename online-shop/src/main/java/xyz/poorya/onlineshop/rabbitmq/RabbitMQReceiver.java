package xyz.poorya.onlineshop.rabbitmq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.poorya.onlineshop.domain.Transaction;
import xyz.poorya.onlineshop.dto.TransactionResponseDto;
import xyz.poorya.onlineshop.repo.TransactionRepository;

import java.util.Optional;

@Component
public class RabbitMQReceiver {

    private static Logger logger = LogManager.getLogger(RabbitMQReceiver.class.toString());
    private final TransactionRepository transactionRepository;

    public RabbitMQReceiver(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @RabbitHandler
    @RabbitListener(queues = "rabbitmq.queue.transaction-response", id = "TransactionListener")
    public void receiver(TransactionResponseDto transactionResponseDto) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(transactionResponseDto.getId());
        Transaction transaction = transactionOptional.get();
        if (transactionResponseDto.isPaid()) {
            transaction.setPaid(true);
            transaction.setDescription(transaction.getDescription());
            transactionRepository.save(transaction);
        } else {
            transaction.setPaid(false);
            transaction.setDescription(transaction.getDescription());
            transactionRepository.save(transaction);


        }
    }
}

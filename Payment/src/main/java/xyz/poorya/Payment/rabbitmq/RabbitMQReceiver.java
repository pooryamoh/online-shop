package xyz.poorya.Payment.rabbitmq;

import xyz.poorya.Payment.dto.TransactionDto;
import xyz.poorya.Payment.dto.UserQueueDto;
import xyz.poorya.Payment.services.TransactionService;
import xyz.poorya.Payment.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQReceiver {
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;
    private static Logger logger = LogManager.getLogger(RabbitMQReceiver.class.toString());

    @RabbitHandler
    @RabbitListener(queues = "rabbitmq.queue.user", id = "UserListener")
    public void receiver(UserQueueDto userQueueDto) {
        logger.info("Received" + userQueueDto);
        if (!userService.userExists(userQueueDto.getUsername())) {
            userService.createUser(userQueueDto);
        } else {
            userService.updateUser(userQueueDto);
        }

    }

    @RabbitHandler
    @RabbitListener(queues = "rabbitmq.queue.transaction", id = "TransactionListener")
    public void receiver(TransactionDto transactionDto) {
        logger.info("Received" + transactionDto);
        transactionService.addTransaction(transactionDto);
    }
}
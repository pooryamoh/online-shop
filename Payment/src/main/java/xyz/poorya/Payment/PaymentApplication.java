package xyz.poorya.Payment;

import xyz.poorya.Payment.dto.TransactionResponseDto;
import xyz.poorya.Payment.rabbitmq.RabbitMQSender;
import xyz.poorya.Payment.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentApplication implements CommandLineRunner {

	@Autowired
    UserService userService;

	@Autowired
	private RabbitMQSender rabbitMQSender;
	public static void main(String[] args) {
		SpringApplication.run(PaymentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		MenuOrder menuOrder = new MenuOrder();
//		menuOrder.setOrderId(123);
//		menuOrder.setCustomerName("ali");
//		menuOrder.setOrderIdentifier("123");
//		rabbitMQSender.send(menuOrder);
//		userService.createUser(new UserQueueDto("poorya"));
//		rabbitMQSender.send(new TransactionResponseDto(true, "Paid successfully"));
	}
}

package xyz.poorya.onlineshop.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {
    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.queue.user}")
    private String queueUser;

    @Value("${rabbitmq.routingkey.user}")
    private String routingKeyUser;

    @Value("${rabbitmq.queue.transaction}")
    private String queueTransaction;

    @Value("${rabbitmq.routingkey.transaction}")
    private String routingKeyTransaction;

    @Value("${rabbitmq.queue.transaction-response}")
    private String queueTransactionResponse;

    @Value("${rabbitmq.routingkey.transaction-response}")
    private String routingKeyTransactionResponse;

    @Value("${rabbitmq.username}")
    private String username;
    @Value("${rabbitmq.password}")
    private String password;
    @Value("${rabbitmq.host}")
    private String host;
    @Value("${rabbitmq.virtualhost}")
    private String virtualHost;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost(virtualHost);

        return cachingConnectionFactory;
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue queueUser(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(queueUser);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    public Queue queueTransaction(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(queueTransaction);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    public Queue queueTransactionResponse(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(queueTransactionResponse);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange, true, false);
    }

    @Bean
    Binding bindingUser(DirectExchange exchange, RabbitAdmin rabbitAdmin) {
        return BindingBuilder.bind(queueUser(rabbitAdmin)).to(exchange).with(routingKeyUser);
    }

    @Bean
    Binding bindingTransaction(DirectExchange exchange, RabbitAdmin rabbitAdmin) {
        return BindingBuilder.bind(queueTransaction(rabbitAdmin)).to(exchange).with(routingKeyTransaction);
    }

    @Bean
    Binding bindingTransactionResponse(DirectExchange exchange, RabbitAdmin rabbitAdmin) {
        return BindingBuilder.bind(queueTransactionResponse(rabbitAdmin)).to(exchange).with(routingKeyTransactionResponse);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}
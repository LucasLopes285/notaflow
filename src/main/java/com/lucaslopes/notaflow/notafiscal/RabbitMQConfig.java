package com.lucaslopes.notaflow.notafiscal;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "notaflow.exchange";
    public static final String FILA_PROCESSAMENTO = "notaflow.notas.processamento";
    public static final String ROUTING_KEY_PROCESSAMENTO = "notas.processar";

    public static final String FILA_DLQ = "notaflow.notas.processamento.dlq";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue filaProcessamento() {
        return QueueBuilder.durable(FILA_PROCESSAMENTO)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", FILA_DLQ)
                .build();
    }

    @Bean
    public Queue filaDlq() {
        return QueueBuilder.durable(FILA_DLQ).build();
    }

    @Bean
    public Binding binding(Queue filaProcessamento, DirectExchange exchange) {
        return BindingBuilder.bind(filaProcessamento)
                .to(exchange)
                .with(ROUTING_KEY_PROCESSAMENTO);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
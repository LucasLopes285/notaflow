package com.lucaslopes.notaflow.notafiscal;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotaFiscalPublisher {

    private final RabbitTemplate rabbitTemplate;

    public NotaFiscalPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarParaProcessamento(Long notaId) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY_PROCESSAMENTO,
                new NotaParaProcessar(notaId)
        );
    }
}
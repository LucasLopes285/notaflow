package com.lucaslopes.notaflow.notafiscal;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotaFiscalConsumer {

    private final NotaFiscalRepository repository;
    private final Extrator extrator;

    public NotaFiscalConsumer(NotaFiscalRepository repository, Extrator extrator) {
        this.repository = repository;
        this.extrator = extrator;
    }

    @RabbitListener(
            queues = RabbitMQConfig.FILA_PROCESSAMENTO,
            containerFactory = "rabbitListenerContainerFactory"
    )
    public void processar(NotaParaProcessar mensagem) {
        NotaFiscal nota = repository.findById(mensagem.notaId())
                .orElseThrow(() -> new IllegalStateException(
                        "Nota não encontrada: " + mensagem.notaId()));

        try {
            ResultadoExtracao resultado = extrator.extrair(nota.getCaminhoImagem());
            nota.atualizarComResultadoExtracao(resultado);
            nota.marcarComoConcluida();
            repository.save(nota);
        } catch (ErroPermanenteException e) {
            nota.marcarComoFalha();
            repository.save(nota);
            // não relança: erro permanente nunca teria sucesso numa nova tentativa
        } catch (Exception e) {
            // erro transitório (ou qualquer outro imprevisto): relança para o
            // interceptor decidir se tenta de novo (com backoff) ou manda para a DLQ
            throw new ErroTransitorioException("Falha ao processar nota " + nota.getId(), e);
        }
    }
}
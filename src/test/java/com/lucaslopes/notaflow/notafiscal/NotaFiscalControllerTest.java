package com.lucaslopes.notaflow.notafiscal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotaFiscalControllerTest {

    @Mock
    private NotaFiscalRepository repository;

    @Mock
    private NotaFiscalPublisher publisher;

    @Mock
    private ArmazenamentoService armazenamentoService;

    private NotaFiscalController controller;

    @BeforeEach
    void setUp() {
        controller = new NotaFiscalController(repository, publisher, armazenamentoService);
    }

    @Test
    void deveCriarNotaERetornar202() {
        MultipartFile arquivo = new MockMultipartFile(
                "arquivo", "nota.jpg", "image/jpeg", "conteudo-fake".getBytes()
        );

        when(armazenamentoService.salvar(any())).thenReturn("/uploads/nota-fake.jpg");
        when(repository.save(any(NotaFiscal.class))).thenAnswer(invocacao -> invocacao.getArgument(0));

        ResponseEntity<NotaFiscalResponse> resposta =
                controller.criar(arquivo, UriComponentsBuilder.newInstance());

        assertThat(resposta.getStatusCode().value()).isEqualTo(202);
        assertThat(resposta.getBody().status()).isEqualTo(StatusNota.PENDENTE);
        verify(publisher, times(1)).publicarParaProcessamento(any());
    }

    @Test
    void deveRetornar404QuandoNotaNaoExiste() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<NotaFiscalResponse> resposta = controller.buscarPorId(999L);

        assertThat(resposta.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    void deveRetornar200ComNotaQuandoExiste() {
        NotaFiscal nota = new NotaFiscal(
                "12345", "12345678000199", LocalDate.now(), new BigDecimal("150.50")
        );
        when(repository.findById(1L)).thenReturn(Optional.of(nota));

        ResponseEntity<NotaFiscalResponse> resposta = controller.buscarPorId(1L);

        assertThat(resposta.getStatusCode().value()).isEqualTo(200);
        assertThat(resposta.getBody().numero()).isEqualTo("12345");
    }
}
package com.lucaslopes.notaflow.notafiscal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
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

    private NotaFiscalController controller;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        controller = new NotaFiscalController(repository);
    }

    @Test
    void deveCriarNotaERetornar201() {
        NotaFiscalRequest request = new NotaFiscalRequest(
                "12345", "12345678000199", LocalDate.now(), new BigDecimal("150.50")
        );

        NotaFiscal notaSalva = new NotaFiscal(
                request.numero(), request.cnpjEmissor(), request.dataEmissao(), request.valorTotal()
        );
        when(repository.save(any(NotaFiscal.class))).thenReturn(notaSalva);

        ResponseEntity<NotaFiscalResponse> resposta =
                controller.criar(request, UriComponentsBuilder.newInstance());

        assertThat(resposta.getStatusCode().value()).isEqualTo(201);
        assertThat(resposta.getBody().numero()).isEqualTo("12345");
        verify(repository, times(1)).save(any(NotaFiscal.class));
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
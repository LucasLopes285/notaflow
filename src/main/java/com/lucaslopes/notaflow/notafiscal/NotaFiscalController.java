package com.lucaslopes.notaflow.notafiscal;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/notas")
public class NotaFiscalController {

    private final NotaFiscalRepository repository;

    public NotaFiscalController(NotaFiscalRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<NotaFiscalResponse> criar(
            @Valid @RequestBody NotaFiscalRequest request,
            UriComponentsBuilder uriBuilder) {

        NotaFiscal nota = new NotaFiscal(
                request.numero(), request.cnpjEmissor(),
                request.dataEmissao(), request.valorTotal()
        );

        NotaFiscal salva = repository.save(nota);

        URI location = uriBuilder.path("/notas/{id}").buildAndExpand(salva.getId()).toUri();
        return ResponseEntity.created(location).body(NotaFiscalResponse.fromEntity(salva));
    }

    @GetMapping
    public List<NotaFiscalResponse> listar() {
        return repository.findAll().stream()
                .map(NotaFiscalResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotaFiscalResponse> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(nota -> ResponseEntity.ok(NotaFiscalResponse.fromEntity(nota)))
                .orElse(ResponseEntity.notFound().build());
    }


}

package com.lucaslopes.notaflow.notafiscal;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Pageable;

import java.net.URI;

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
    public PaginaResponse<NotaFiscalResponse> listar(
            @PageableDefault(size = 20, sort = "dataEmissao", direction = Sort.Direction.DESC)
            Pageable pageable) {

        Page<NotaFiscal> pagina = repository.findAll(pageable);

        return PaginaResponse.fromPage(pagina.map(NotaFiscalResponse::fromEntity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotaFiscalResponse> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(nota -> ResponseEntity.ok(NotaFiscalResponse.fromEntity(nota)))
                .orElse(ResponseEntity.notFound().build());
    }


}

package com.lucaslopes.notaflow.notafiscal;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;

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
            @RequestParam(required = false) String cnpjEmissor,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) BigDecimal valorMinimo,
            @RequestParam(required = false) BigDecimal valorMaximo,
            @PageableDefault(size = 20, sort = "dataEmissao", direction = Sort.Direction.DESC)
            Pageable pageable) {

        Specification<NotaFiscal> filtro = Specification.unrestricted();

        if (cnpjEmissor != null) {
            filtro = filtro.and(NotaFiscalSpecifications.comCnpjEmissor(cnpjEmissor));
        }
        if (dataInicio != null) {
            filtro = filtro.and(NotaFiscalSpecifications.comDataEmissaoApartirDe(dataInicio));
        }
        if (dataFim != null) {
            filtro = filtro.and(NotaFiscalSpecifications.comDataEmissaoAte(dataFim));
        }
        if (valorMinimo != null) {
            filtro = filtro.and(NotaFiscalSpecifications.comValorMinimo(valorMinimo));
        }
        if (valorMaximo != null) {
            filtro = filtro.and(NotaFiscalSpecifications.comValorMaximo(valorMaximo));
        }

        Page<NotaFiscal> pagina = repository.findAll(filtro, pageable);

        return PaginaResponse.fromPage(pagina.map(NotaFiscalResponse::fromEntity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotaFiscalResponse> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(nota -> ResponseEntity.ok(NotaFiscalResponse.fromEntity(nota)))
                .orElse(ResponseEntity.notFound().build());
    }


}

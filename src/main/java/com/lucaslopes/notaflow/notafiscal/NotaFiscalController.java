package com.lucaslopes.notaflow.notafiscal;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    private final NotaFiscalPublisher publisher;
    private final ArmazenamentoService armazenamentoService;

    public NotaFiscalController(
            NotaFiscalRepository repository,
            NotaFiscalPublisher publisher,
            ArmazenamentoService armazenamentoService) {
        this.repository = repository;
        this.publisher = publisher;
        this.armazenamentoService = armazenamentoService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<NotaFiscalResponse> criar(
            @RequestParam("arquivo") MultipartFile arquivo,
            UriComponentsBuilder uriBuilder) {

        if (arquivo.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        String caminho = armazenamentoService.salvar(arquivo);

        NotaFiscal nota = new NotaFiscal();
        nota.definirCaminhoImagem(caminho);

        NotaFiscal salva = repository.save(nota);

        publisher.publicarParaProcessamento(salva.getId());

        URI location = uriBuilder.path("/notas/{id}").buildAndExpand(salva.getId()).toUri();
        return ResponseEntity.accepted().location(location).body(NotaFiscalResponse.fromEntity(salva));


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

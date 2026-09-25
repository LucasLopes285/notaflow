package com.lucaslopes.notaflow.notafiscal;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "notas_fiscais")
public class NotaFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String numero;

    @Column(name = "cnpj_emissor", nullable = false, length = 14)
    private String cnpjEmissor;

    @Column(name = "data_emissao", nullable = false)
    private LocalDate dataEmissao;

    @Column(name = "valor_total", nullable = false, precision = 12,scale = 2)
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusNota status = StatusNota.PENDENTE;

    @Column(name = "caminho_imagem")
    private String caminhoImagem;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    protected NotaFiscal() {}

    public NotaFiscal(String numero, String cnpjEmissor, LocalDate dataEmissao, BigDecimal valorTotal) {
        this.numero = numero;
        this.cnpjEmissor = cnpjEmissor;
        this.dataEmissao = dataEmissao;
        this.valorTotal = valorTotal;
    }

    public Long getId() { return id; }
    public String getNumero() { return numero; }
    public String getCnpjEmissor() { return cnpjEmissor; }
    public LocalDate getDataEmissao() { return dataEmissao; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public StatusNota getStatus() { return status; }
    public String getCaminhoImagem() { return caminhoImagem; }
    public LocalDateTime getCriadoEm() { return criadoEm; }

    public void marcarComoConcluida() { this.status = StatusNota.CONCLUIDA; }
    public void marcarComoFalha() { this.status = StatusNota.FALHA; }
    public void definirCaminhoImagem(String caminho) { this.caminhoImagem = caminho; }

    public void atualizarComResultadoExtracao(ResultadoExtracao resultado) {
        this.numero = resultado.numero();
        this.cnpjEmissor = resultado.cnpjEmissor();
        this.dataEmissao = resultado.dataEmissao();
        this.valorTotal = resultado.valorTotal();
    }
}

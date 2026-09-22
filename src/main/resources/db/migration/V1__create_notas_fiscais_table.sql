CREATE TABLE notas_fiscais(
    id  BIGSERIAL  PRIMARY KEY,
    numero         VARCHAR(50) NOT NULL,
    cnpj_emissor   VARCHAR(14) NOT NULL,
    data_emissao   DATE NOT NULL,
    valor_total    NUMERIC(12, 2) NOT NULL,
    status         VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    caminho_imagem VARCHAR(255),
    criado_em      TIMESTAMP NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX idx_notas_fiscais_numero_cnpj
    ON notas_fiscais (numero, cnpj_emissor);
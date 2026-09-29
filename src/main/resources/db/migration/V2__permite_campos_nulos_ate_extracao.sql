ALTER TABLE notas_fiscais ALTER COLUMN numero DROP NOT NULL;
ALTER TABLE notas_fiscais ALTER COLUMN cnpj_emissor DROP NOT NULL;
ALTER TABLE notas_fiscais ALTER COLUMN data_emissao DROP NOT NULL;
ALTER TABLE notas_fiscais ALTER COLUMN valor_total DROP NOT NULL;
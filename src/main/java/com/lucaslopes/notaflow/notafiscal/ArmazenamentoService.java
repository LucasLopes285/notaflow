package com.lucaslopes.notaflow.notafiscal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ArmazenamentoService {

    private final Path diretorioBase;

    public ArmazenamentoService(@Value("${notaflow.storage.diretorio}") String diretorio) {
        this.diretorioBase = Path.of(diretorio);
        try{
            Files.createDirectories(this.diretorioBase);
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível criar o diretório de uploads", e);
        }
    }

    public  String salvar(MultipartFile arquivo) {
        String extensao = extrairExtensao(arquivo.getOriginalFilename());
        String nomeUnico = UUID.randomUUID() + extensao;
        Path destino = diretorioBase.resolve(nomeUnico);

        try {
            arquivo.transferTo(destino);
        } catch (IOException e) {
            throw new IllegalStateException("Falha ao salvar o arquivo", e);
        }

        return destino.toString();
    }

    private String extrairExtensao(String nomeOriginal) {
        if (nomeOriginal == null || !nomeOriginal.contains("'")){
            return "";
        }
        return nomeOriginal.substring(nomeOriginal.lastIndexOf('.'));
    }
}

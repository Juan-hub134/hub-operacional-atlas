package br.com.atlastec.huboperacional.service;

import br.com.atlastec.huboperacional.dto.DocumentosDto;
import br.com.atlastec.huboperacional.dto.DocumentosDtoReposta;
import br.com.atlastec.huboperacional.exception.DocumentoNaoEncontradoException;
import br.com.atlastec.huboperacional.model.Documentos;
import br.com.atlastec.huboperacional.repository.DocumentosRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentoService {
    private final DocumentosRepository documentosRepository;
    public DocumentoService(DocumentosRepository documentosRepository) {
        this.documentosRepository = documentosRepository;
    }
    public void uploadArquivo(DocumentosDto informacoes, MultipartFile file) throws IOException {
            Documentos documentos = new Documentos();
            documentos.setTitulo(informacoes.titulo());
            documentos.setTipoDocumento(informacoes.tipo());
            byte[] documento = file.getBytes();
            LocalDateTime dataUpload = LocalDateTime.now();
            documentos.setData(dataUpload);
            documentos.setDocumento(documento);
            documentosRepository.save(documentos);
    }
    public List<DocumentosDtoReposta> listarDocumentos(){
        List<Documentos> listaDocumentos = documentosRepository.findAll();
        List<DocumentosDtoReposta> listaDeDocumentos = listaDocumentos.stream()
                .map(d -> new DocumentosDtoReposta(d.getId(),d.getTitulo(),d.getTipoDocumento()))
                .toList();
        return listaDeDocumentos;
    }
    public byte[] downloadArquivo(int id){
        Optional<Documentos> documentos = documentosRepository.findById(id);
        if(documentos.isPresent()){
            return documentos.get().getDocumento();
        }else {
            throw new DocumentoNaoEncontradoException("Documento que você passou com esse "+ id + " não existe");
        }
    }
    public void deletarDocumento(int id){
        Optional<Documentos> documento = documentosRepository.findById(id);
        if(documento.isPresent()){
            documentosRepository.deleteById(id);
        }else{
            throw new DocumentoNaoEncontradoException("Documento que você passou com esse "+ id + " não existe");
        }
    }
}

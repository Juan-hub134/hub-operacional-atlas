package br.com.atlastec.huboperacional.controller;

import br.com.atlastec.huboperacional.dto.DocumentosDto;
import br.com.atlastec.huboperacional.dto.DocumentosDtoReposta;
import br.com.atlastec.huboperacional.exception.DocumentoNaoEncontradoException;
import br.com.atlastec.huboperacional.service.DocumentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/atlashub")
public class DocumentoController {
    private final DocumentoService documentoService;
    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }
    @PostMapping("/api/admin/upload")
    public ResponseEntity<String> upload(@ModelAttribute DocumentosDto documentosDto, @RequestParam("file") MultipartFile file) {
        try {
            documentoService.uploadArquivo(documentosDto, file);
            return ResponseEntity.ok().body("Arquivo enviado!");
        }catch (IOException e) {
            return ResponseEntity.badRequest().body("Erro ao enviar documento!");
        }
    }
    @GetMapping("/api/admin/arquivo/lista")
    public ResponseEntity<List<DocumentosDtoReposta>> listarDocumentos(){
        return ResponseEntity.ok(documentoService.listarDocumentos());
    }
    @GetMapping("/api/{id}/admin/arquivo/download")
    public ResponseEntity<?> downloadArquivo(@PathVariable Integer id){
        try {
            return ResponseEntity.ok(documentoService.downloadArquivo(id));
        }catch (DocumentoNaoEncontradoException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
    @GetMapping("/api/{id}/admin/arquivo/remover")
    public ResponseEntity<String> removerArquivo(@PathVariable Integer id){
        try {
            documentoService.deletarDocumento(id);
            return ResponseEntity.ok("Documento deletado!");
        }catch (DocumentoNaoEncontradoException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body( exception.getMessage());
        }
    }
}

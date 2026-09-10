package br.com.atlastec.huboperacional.controller;

import br.com.atlastec.huboperacional.dto.LoginDto;
import br.com.atlastec.huboperacional.dto.TipoDto;
import br.com.atlastec.huboperacional.dto.UsuarioDto;
import br.com.atlastec.huboperacional.service.RegistroService;
import br.com.atlastec.huboperacional.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atlashub")
public class RegistroController {
    private final RegistroService registroService;
    private final UsuarioService usuarioService;
    public RegistroController(RegistroService registroService, UsuarioService usuarioService) {
        this.registroService = registroService;
        this.usuarioService = usuarioService;
    }
    @PostMapping("/registro")
    public String registrarToken(@RequestBody TipoDto registro){
        return registroService.registrarToken(registro);
    }
    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarUsuario(@RequestBody UsuarioDto usuario){
        try {
            String tipoUsuario =  registroService.validarToken(usuario.token());
            usuarioService.criarUsuario(usuario, tipoUsuario);
            return ResponseEntity.ok("Cadastro feito");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/checarLogin")
    public String checarLogin(@RequestBody LoginDto login){
        return usuarioService.verificarUsuario(login);
    }
}

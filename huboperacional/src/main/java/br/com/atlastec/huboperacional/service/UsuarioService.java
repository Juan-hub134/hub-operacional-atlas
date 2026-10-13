package br.com.atlastec.huboperacional.service;

import br.com.atlastec.huboperacional.dto.LoginDto;
import br.com.atlastec.huboperacional.dto.UsuarioDto;
import br.com.atlastec.huboperacional.model.Registro;
import br.com.atlastec.huboperacional.model.Usuario;
import br.com.atlastec.huboperacional.repository.RegistroRepository;
import br.com.atlastec.huboperacional.repository.UsuarioRepository;
import com.auth0.jwt.JWT;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.*;
import java.util.Optional;

@Service
public class UsuarioService {
    private final PasswordEncoder encoder;
    private final UsuarioRepository usuarioRepository;
    private final RegistroRepository registroRepository;
    private final TokenService tokenService;
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder encoder, RegistroRepository registroRepository, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
        this.registroRepository = registroRepository;
        this.tokenService = tokenService;
    }
    public void criarUsuario(UsuarioDto dados, String tipoUsuario){
        Usuario usuario = new Usuario();
        usuario.setNome(dados.nome());
        usuario.setEmail(dados.email());
        String senha = encoder.encode(dados.senha());
        usuario.setSenha(senha);
        usuario.setTipo(tipoUsuario);
        Registro registro = registroRepository.findByToken(dados.token()).get();//vai apontar pra entendida IdRegistro e as anotacoes fazem o resto
        usuario.setIdRegistro(registro);
        usuarioRepository.save(usuario);
    }
    public String verificarUsuario(LoginDto dados){
        Optional<Usuario> usuario = usuarioRepository.findByEmail(dados.email());
        if(usuario.isPresent()){
            if(encoder.matches(dados.senha(),usuario.get().getSenha())){
               return tokenService.gerarToken(usuario.get());
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Credenciais inválidas");
    }
}

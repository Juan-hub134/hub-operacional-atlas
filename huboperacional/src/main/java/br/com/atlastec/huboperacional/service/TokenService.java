package br.com.atlastec.huboperacional.service;

import br.com.atlastec.huboperacional.model.Usuario;
import com.auth0.jwt.JWT;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    public String gerarToken(Usuario usuario){
        JWT.create()
                .withSubject(String.valueOf(usuario.getIdUsuario()));//peguei o id gerado pelo postegree e coloquei no campo sub
               return "ronaldo";
    }
}

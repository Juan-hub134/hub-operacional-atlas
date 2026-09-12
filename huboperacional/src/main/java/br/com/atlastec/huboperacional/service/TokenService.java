package br.com.atlastec.huboperacional.service;

import br.com.atlastec.huboperacional.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Service
public class TokenService {
    private final RsakeyService rsaKeyService;
    public TokenService (RsakeyService rsakeyService) {
        this.rsaKeyService = rsakeyService;
    }
    public String gerarToken(Usuario usuario){
        RSAPrivateKey privateKey = rsaKeyService.carregarPrivateKey();
        RSAPublicKey publicKey = rsaKeyService.carregarPublicKey();
        Algorithm algorithm = Algorithm.RSA256(publicKey,privateKey);
        return JWT.create()
                .withSubject(String.valueOf(usuario.getIdUsuario()))//peguei o id gerado pelo postegree e coloquei no campo sub
                .sign(algorithm);
    }
}

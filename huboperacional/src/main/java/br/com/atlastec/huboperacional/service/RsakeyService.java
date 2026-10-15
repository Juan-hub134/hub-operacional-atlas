package br.com.atlastec.huboperacional.service;

import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.stereotype.Service;


import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class RsakeyService {
    //abandonei! Só funcionária se fosse em PKCS#1
    //biblioteca interessante que tem métodos prontos para conversão de aquivos pem gerados com PKCS#1:
    //try com argumento(try-with-resources) substitui o parser.close()
//        try(PEMParser parser = new PEMParser(//pemParser vai conseguir ler esse arquivo pem, mesmo sendo um  PKCS#1
//                new FileReader("/huboperacional/src/main/resources/keys/private_key.pem"))){//abre o arquivo pem para leitura
//            Object objeto = parser.readObject();//método da classe PEMParser, ele lê o arquivo pem e devolve um objeto
//            System.out.println("O tipo real do objeto lido é: " + objeto.getClass().getName());
//            //nome autoexplicativo, vai converte esse pem, serve para pegar o objeto que Bouncy Castle leu e trasnforma em objeto de chave
//            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
//            //faz o cast, pega esse objeto a classe Object e trasnforma em uma PEMkeyPair, já que sabemos que o que foi lido por readObject é uma chave
//            PEMKeyPair keypair = (PEMKeyPair) objeto;
//            //cast de novo, mas agora é para RSAPrivateKey
//            return (RSAPrivateKey) converter
//                    .getKeyPair(keypair)
//                    .getPrivate();
//        }catch (IOException e){
//            e.printStackTrace();
//            throw new IllegalStateException("Erro ao cerregar a chave privada ", e);
//        }
         //Para o público
//      try (PEMParser parser = new PEMParser(
//            new FileReader("/huboperacional/huboperacional/src/main/resources/keys/private_key.pem"))){
//        Object objeto = parser.readObject();
//        System.out.println("O tipo real do objeto lido é: " + objeto.getClass().getName());
//        SubjectPublicKeyInfo info = (SubjectPublicKeyInfo) objeto;
//        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
//        return (RSAPublicKey) converter
//                .getPublicKey(info);
//    }catch (IOException e){
//        throw new IllegalStateException("erro ao carregar a chave pública ", e);
//    }
    public RSAPrivateKey carregarPrivateKey() {
                try {
                    Path path = Paths.get(System.getenv("RSA_PRIVATE_KEY_PATH"));//acessar o arquivo private key da pasta keys que voce criou no shell
                    String key = Files.readString(path);// seria ler o caminho, ler o path
                    //retirar o que está escrindo em priavate_key.pem, deixar só o arquivo bruto
                    key = key.replaceAll("-----BEGIN.*-----", "")
                            .replaceAll("-----END.*-----", "")
                            .replaceAll("\\s", "");
                                             //(Base64)Chave de ferramenta, (getDecoder) chave de fenda, (decode) aperta parafuso!
                    byte[] bytesDecodificados = Base64.getDecoder().decode(key);
                    //PCKS8 é usado em chave privadas, passamos as intâncias com os bytes para variável spec(especificao)
                    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytesDecodificados);
                    //usamos KeyFactory para específica o algoritmo, nese caso RSA 256, chave privada e pública
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    //fazemos o cast, e específicamos que é uma chave privada
                    return (RSAPrivateKey) keyFactory.generatePrivate(spec);

                }catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e){
                    throw new IllegalStateException("erro ao carregarPrivateKey", e);
                }
    }
    public RSAPublicKey carregarPublicKey() {
        try {
            Path path = Paths.get(System.getenv("RSA_PUBLIC_KEY_PATH"));//
            String key = Files.readString(path);
            key = key.replaceAll("-----BEGIN.*-----", "")
                    .replaceAll("-----END.*-----", "")
                    .replaceAll("\\s", "");
            byte[] bytesDecodificados = Base64.getDecoder().decode(key);
            // X509EncodedKeySpec padrão para chave pública
            X509EncodedKeySpec spec = new X509EncodedKeySpec(bytesDecodificados);
            KeyFactory keyFactory =  KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(spec);
        }catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e){
            throw new IllegalStateException("erro ao carregarPublicKey", e);
        }
    }
}

package br.com.atlastec.huboperacional.service;

import br.com.atlastec.huboperacional.dto.TipoDto;
import br.com.atlastec.huboperacional.model.Registro;
import br.com.atlastec.huboperacional.repository.RegistroRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Predicate;

@Service
public class RegistroService {
    private final RegistroRepository registroRepository;
    public RegistroService(RegistroRepository registroRepository) {
        this.registroRepository = registroRepository;
    }
    public String registrarToken(TipoDto tipoDto) {
        UUID uuid = UUID.randomUUID();
        Registro registro = new Registro();
        registro.setToken(uuid.toString());
        registro.setDataExpiracao(LocalDateTime.now().plusDays(7));
        registro.setTipoRegistro(tipoDto.tipoRegistro());
        registroRepository.save(registro);
        return uuid.toString();
    }
    public String validarToken(String token) {
        //se não se registrar durante 7 dias é tchau
        if (registroRepository.findByToken(token).isPresent() && LocalDateTime.now().isBefore(registroRepository.findByToken(token).get().getDataExpiracao())){
            return registroRepository.findByToken(token).get().getTipoRegistro();
    }
        throw new RuntimeException("Token expirado");
  }
}

package br.com.atlastec.huboperacional.service;

import br.com.atlastec.huboperacional.dto.TreinamentoDto;
import br.com.atlastec.huboperacional.model.Treinamento;
import br.com.atlastec.huboperacional.model.Usuario;
import br.com.atlastec.huboperacional.repository.TreinamentoRepository;
import br.com.atlastec.huboperacional.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TreinamentoService {
    private final TreinamentoRepository treinamentoRepository;
    private final UsuarioRepository usuarioRepository;
    public TreinamentoService(TreinamentoRepository treinamentoRepository, UsuarioRepository usuarioRepository) {
        this.treinamentoRepository = treinamentoRepository;
        this.usuarioRepository = usuarioRepository;
    }
    public void  acompanharTreinamento(TreinamentoDto treinamentoDto, String sub){
        int id = Integer.parseInt(sub);
        Usuario usuario = usuarioRepository.findById(id).get();
        Optional<Treinamento> treinamento= treinamentoRepository.findByIdUsuarioAndAreaAndDescricao(usuario, treinamentoDto.area(), treinamentoDto.descricao());
        if (treinamento.isPresent()) {
            if(treinamento.get().isChecklist() == true){
                treinamento.get().setChecklist(false);
                treinamentoRepository.save(treinamento.get());
            }else {
                treinamento.get().setChecklist(true);
                treinamentoRepository.save(treinamento.get());
            }
        }else{
            Treinamento treinamentoNovo = new Treinamento();
            // Nota de debug: O objeto "Treinamento" possui um getter para a entidade "Usuario"
            // Caso precise debugar, utilize: treinamento.getIdUsuario().getIdUsuario(), primeiro getter retorna entidade "Usuario",
            // e o segundo getter retorna o id desse usuário
            treinamentoNovo.setIdUsuario(usuario);
            treinamentoNovo.setArea(treinamentoDto.area());
            treinamentoNovo.setDescricao(treinamentoDto.descricao());
            treinamentoNovo.setChecklist(treinamentoDto.checklist());
            treinamentoRepository.save(treinamentoNovo);
        }
    }
}

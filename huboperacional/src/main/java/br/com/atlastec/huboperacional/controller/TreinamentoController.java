package br.com.atlastec.huboperacional.controller;

import br.com.atlastec.huboperacional.dto.TreinamentoDto;
import br.com.atlastec.huboperacional.service.TreinamentoService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atlashub")
public class TreinamentoController {
    private final TreinamentoService treinamentoService;
    public TreinamentoController(TreinamentoService treinamentoService) {
        this.treinamentoService = treinamentoService;
    }
    @PostMapping("/api/treinamento")
    public void gerirTreinamento(@RequestBody TreinamentoDto treinamentoDto, @AuthenticationPrincipal Jwt jwt){
        String sub = jwt.getSubject();
        treinamentoService.acompanharTreinamento(treinamentoDto, sub);
    }
    @GetMapping("/api/treinamento")
    public List<TreinamentoDto> listarTreinamentos(@AuthenticationPrincipal Jwt jwt) {
        return treinamentoService.listarTreinamentos(jwt.getSubject());
    }
}

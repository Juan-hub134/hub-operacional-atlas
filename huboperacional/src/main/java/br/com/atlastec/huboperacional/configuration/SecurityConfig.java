package br.com.atlastec.huboperacional.configuration;

import br.com.atlastec.huboperacional.service.RsakeyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPublicKey;
import java.util.Date;

@Configuration
public class SecurityConfig {
    private  RsakeyService rsakeyService;
    public SecurityConfig(RsakeyService rsakeyService) {
        this.rsakeyService = rsakeyService;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/atlashub/api/admin/registro").permitAll()
                        .requestMatchers("/atlashub/api/admin/upload").hasAuthority("SCOPE_admin")
                        .requestMatchers("/atlashub/api/admin/arquivo/lista").hasAnyAuthority("SCOPE_admin", "SCOPE_membro")
                        .requestMatchers("/atlashub/api/{id}/admin/arquivo/download").hasAnyAuthority("SCOPE_admin", "SCOPE_membro")
                        .requestMatchers("/atlashub/api/{id}/admin/arquivo/remover").hasAuthority("SCOPE_admin")
                        .requestMatchers("/atlashub/api/admin/registrar/movimentacao/financeira").hasAuthority("SCOPE_admin")
                        .requestMatchers("/atlashub/api/admin/listar/movimentacao/financeira").hasAnyAuthority("SCOPE_admin",  "SCOPE_membro")
                        .requestMatchers("/atlashub/api/admin/{id}/atualizar/movimentacao/financeira").hasAuthority("SCOPE_admin")
                        .requestMatchers("/atlashub/api/admin/{id}/excluir/movimentacao/financeira").hasAuthority("SCOPE_admin")
                        .requestMatchers("/atlashub/api/admin/{id}/buscar/movimentacao/financeira").hasAnyAuthority("SCOPE_admin", "SCOPE_membro")
                        .requestMatchers("/atlashub/api/admin/soma/entradas/financeiras").hasAuthority("SCOPE_admin")
                        .requestMatchers("/atlashub/api/admin/soma/saidas/financeiras").hasAuthority("SCOPE_admin")
                        .requestMatchers("/atlashub/api/admin/saldo/periodo").hasAuthority("SCOPE_admin")
                        .requestMatchers("/atlashub/api/admin/soma/saldo/pendente").hasAuthority("SCOPE_admin")
                        .requestMatchers("/atlashub/api/cadastro").permitAll()
                        .requestMatchers("/atlashub/api/login").permitAll()
                        .requestMatchers("/atlashub/api/treinamento").hasAnyAuthority("SCOPE_admin", "SCOPE_membro", "SCOPE_trainee")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> {}) //configurar o backand para receber o token
                );
        return http.build();
    }
    @Bean
    public JwtDecoder jwtDecoder(){
        RSAPublicKey publicKey = rsakeyService.carregarPublicKey();
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

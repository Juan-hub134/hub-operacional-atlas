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
                        .requestMatchers("/atlashub/api/admin/registro").hasAuthority("SCOPE_admin")
                        .requestMatchers("/atlashub/api/admin/upload").hasAuthority("SCOPE_admin")
                        .requestMatchers("/atlashub/api/admin/arquivo/lista").hasAuthority("SCOPE_admin")
                        .requestMatchers("/atlashub/api/{id}/admin/arquivo/download").hasAuthority("SCOPE_admin")
                        .requestMatchers("/atlashub/api/{id}/admin/arquivo/remover").hasAuthority("SCOPE_admin")
                        .requestMatchers("/atlashub/api/cadastro").permitAll()
                        .requestMatchers("/atlashub/api/login").permitAll()
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

package com.farma.estoque.controller;

import com.farma.estoque.dto.AutenticacaoDTO;
import com.farma.estoque.dto.TokenJwtDTO;
import com.farma.estoque.model.Usuario;
import com.farma.estoque.security.RateLimitService;
import com.farma.estoque.security.TokenService;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RateLimitService rateLimitService;

    @PostMapping("/login")
    public ResponseEntity<?> efetuarLogin(
            HttpServletRequest request,
            @RequestBody @Valid AutenticacaoDTO dados) {

        String ip = request.getRemoteAddr();
        Bucket bucket = rateLimitService.resolveBucket(ip);

        if (!bucket.tryConsume(1)) {
            return ResponseEntity
                    .status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Muitas tentativas de login. Aguarde 1 minuto e tente novamente.");
        }

        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return  ResponseEntity.ok(new TokenJwtDTO(tokenJWT));
    }
}

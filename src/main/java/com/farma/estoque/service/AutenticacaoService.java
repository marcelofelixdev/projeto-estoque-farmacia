package com.farma.estoque.service;

import com.farma.estoque.dto.CadastroUsuarioRequestDTO;
import com.farma.estoque.model.Usuario;
import com.farma.estoque.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = repository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }
        return user;
    }

    public Usuario cadastrar(CadastroUsuarioRequestDTO dto) {
        // Verifica se o login já está em uso
        if (repository.findByLogin(dto.login()) != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe um usuário com o login: " + dto.login()
            );
        }

        // Aplica o BCrypt na senha antes de salvar
        String senhaCriptografada = passwordEncoder.encode(dto.senha());

        Usuario novoUsuario = new Usuario(null, dto.login(), senhaCriptografada, dto.role());

        return repository.save(novoUsuario);
    }
}
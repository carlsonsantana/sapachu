package org.sapac.controllers.usuario;

import java.util.ArrayList;
import java.util.List;
import org.sapac.entities.Usuario;
import org.sapac.models.UsuarioDAO;
import org.sapac.models.hibernate.UsuarioDAOHibernate;
import org.sapac.utils.HashGenerator;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SpringAdapter implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authObject) throws AuthenticationException {
        String nome = authObject.getName();
        String senha = authObject.getCredentials().toString();
        UsuarioDAO dao = new UsuarioDAOHibernate();
        Usuario usuario = dao.carregarUsuario(nome);
        List<SimpleGrantedAuthority> papeis = new ArrayList<SimpleGrantedAuthority>();
		String cript = HashGenerator.gerar(senha);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado no banco de dados!");
        } else if (!usuario.getSenha().equals(cript)) {
            throw new BadCredentialsException("Senha incorreta");
        } else if (!usuario.isAtivo()) {
            throw new DisabledException("Usuário " + nome + " está desativado");
        } else {
            if (usuario.isMedico()) {
                papeis.add(new SimpleGrantedAuthority("ROLE_MEDICO"));
                if (usuario.isCoordenador()) {
                    papeis.add(new SimpleGrantedAuthority("ROLE_COORD"));
                }
            } else if (usuario.isEnfermeiro()) {
                papeis.add(new SimpleGrantedAuthority("ROLE_ENFERMEIRO"));
            } else {
                throw new AuthenticationServiceException("Informações incorretas no banco de dados. Favor informar administrador do sistema");
            }
            Authentication response = new UsernamePasswordAuthenticationToken(usuario.getNomeUsuario(), usuario.getSenha(), papeis);
            return response;
        }
    }

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(UsernamePasswordAuthenticationToken.class);
    }
}
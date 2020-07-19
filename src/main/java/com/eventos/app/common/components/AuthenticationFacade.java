package com.eventos.app.common.components;

import com.eventos.app.common.interfaces.IAuthenticationFacade;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Classe que responsável por retornar dados de sessão de usuários conectados ao sistema
 *
 */
@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    /**
     * Permite a descoberta dos dados de sessão de usuários autenticados.
     *
     * @return um objeto {@link Authentication} contendo o login do usuário relacionado ao contexto da requisição.
     */
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

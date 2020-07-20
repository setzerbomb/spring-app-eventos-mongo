package com.eventos.app.common.components;

import com.eventos.app.model.domain.User;
import com.eventos.app.model.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.stereotype.Service;

@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    private UsersService usersService;
    private MessageByLocaleService messageByLocaleService;

    @Autowired
    public SpringDataUserDetailsService(UsersService service, MessageByLocaleService messageByLocaleService) {
        this.usersService = service;
        this.messageByLocaleService = messageByLocaleService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UserDeniedAuthorizationException, BadCredentialsException {
        User user = this.usersService.findByEmail(email);
        if (user != null) {
            return new ConnectedUser(user.getEmail(), user.getSenha(), true,
                    AuthorityUtils.createAuthorityList("USER"));
        }
        throw new BadCredentialsException(messageByLocaleService.getMessage("user.credentials.invalid"));
    }

}

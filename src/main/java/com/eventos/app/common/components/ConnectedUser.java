package com.eventos.app.common.components;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class ConnectedUser extends User {

    public ConnectedUser(String username, String password, boolean enabled,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, true, true, true, authorities);
    }

}

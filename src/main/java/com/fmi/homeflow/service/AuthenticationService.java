package com.fmi.homeflow.service;

import com.fmi.homeflow.model.Role;
import com.fmi.homeflow.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userService.findByName(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username " + username + "not found");
        }
        User user = userOptional.get();
        return org.springframework.security.core.userdetails.User.withUsername(username).password(user.getPassword())
                .credentialsExpired(false).disabled(false).authorities(getAuthorities(user.getRole())).build();

    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return  authorities;
    }
}

package com.fmi.homeflow.service.user;

import com.fmi.homeflow.model.user.Role;
import com.fmi.homeflow.model.user.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity existingUser = usersService.getUserByUsername(username);
        return User.withUsername(existingUser.getUsername())
            .password(existingUser.getPassword())
            .credentialsExpired(false)
            .disabled(false)
            .authorities(getAuthorities(existingUser.getRole()))
            .build();
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }
}

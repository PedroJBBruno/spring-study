package com.pedrojbbruno.microservice.authService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final String userName = "authUser";
        final String secret = encoder.encode("zyFH&V2QGxV3");


        if(userName.equals(username)) {
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                    .commaSeparatedStringToAuthorityList("ROLE_ADMIN");
            return new User(username, secret, grantedAuthorities);
        }

        throw new UsernameNotFoundException("Username: " + username + " not found");
    }
}

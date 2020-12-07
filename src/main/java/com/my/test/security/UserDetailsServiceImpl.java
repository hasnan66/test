package com.my.test.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final Map<String, UserDetailsImpl> users = new HashMap<>();

    public UserDetailsServiceImpl() {
        UserDetailsImpl user = new UserDetailsImpl(
                "abcd1234",
                "testadmin",
                "adminpassword",
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("ADMIN"))
        );
        UserDetailsImpl user1 = new UserDetailsImpl(
                "abcd12345",
                "testuser",
                "userpassword",
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("USER"))
        );
        users.put(user.getUsername(), user);
        users.put(user1.getUsername(), user1);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsImpl user = users.get(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username not found.");
        }

        return user;
    }

}

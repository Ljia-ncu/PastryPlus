package org.mrl.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

public class DefaultUserDetails implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 默认实现，由各module配置
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_user");
        return new User(username, "", Collections.singletonList(authority));
    }
}

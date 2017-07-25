package com.mposhatov.springUtil;

import com.mposhatov.dao.RegisteredClientRepository;
import com.mposhatov.entity.DbRegisteredClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private RegisteredClientRepository registeredClientRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        final DbRegisteredClient client = registeredClientRepository.findByName(name);
        if (client != null) {
            return new User(client.getName(), client.getPassword(), true, true,
                    true, true, client.getRoles().stream().map(o ->
                    new SimpleGrantedAuthority(o.name())).collect(Collectors.toList()));
        } else {
            throw new UsernameNotFoundException(name);
        }
    }
}

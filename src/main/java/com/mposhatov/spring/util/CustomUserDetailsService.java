package com.mposhatov.spring.util;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.entity.DbClient;
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
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        final DbClient client = clientRepository.findByLogin(login);

        if (client == null) {
            throw new UsernameNotFoundException(login);
        }

        return new User(client.getLogin(), client.getPassword(), true, true,
                true, true, client.getRoles().stream().map(o ->
                new SimpleGrantedAuthority(o.name())).collect(Collectors.toList()));
    }
}

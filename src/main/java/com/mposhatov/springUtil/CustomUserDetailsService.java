package com.mposhatov.springUtil;

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
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        final DbClient client = clientRepository.findByName(name);
        if (client != null) {
            return new User(client.getName(), client.getPassword(), true, true,
                    true, true, client.getRoles().stream().map(o ->
                    new SimpleGrantedAuthority(o.name())).collect(Collectors.toList()));
        } else {
            throw new UsernameNotFoundException(name);
        }
    }
}

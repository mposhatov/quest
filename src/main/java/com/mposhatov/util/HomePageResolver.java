package com.mposhatov.util;

import com.mposhatov.entity.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HomePageResolver {
    public static String redirectToHomePage() {
        String homePageUrl = Role.ROLE_GUEST.getHomePage();

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null) {
            final List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            homePageUrl = Stream.of(Role.ROLE_ADMIN, Role.ROLE_GAMER)
                    .filter(role -> roles.contains(role.name()))
                    .findFirst().orElse(Role.ROLE_GUEST).getHomePage();
        }
        return homePageUrl;
    }
}

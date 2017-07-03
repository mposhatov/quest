package com.mposhatov.springUtil;

import com.mposhatov.entity.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Service
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        final Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (authorities.contains(Role.ROLE_ADMIN.name())) {
            request.getRequestDispatcher(Role.ROLE_ADMIN.getHomePage()).forward(request, response);
        } else if (authorities.contains(Role.ROLE_GAMER.name())) {
            request.getRequestDispatcher(Role.ROLE_GAMER.getHomePage()).forward(request, response);
        } else {
            response.sendRedirect(Role.ROLE_GUEST.getHomePage());
        }
    }
}
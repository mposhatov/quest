package com.mposhatov.springUtil;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dto.Client;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.Role;
import com.mposhatov.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        final User user = (User) authentication.getPrincipal();
        final DbClient dbClient = clientRepository.findByName(user.getUsername());
        request.getSession().setAttribute(Client.class.getName(), EntityConverter.toClient(dbClient));
        final List<Role> roles = dbClient.getRoles();
        if(roles.contains(Role.ROLE_ADMIN)) {
            response.sendRedirect(Role.ROLE_ADMIN.getHomePage());
        } else if (roles.contains(Role.ROLE_GAMER)) {
            response.sendRedirect(Role.ROLE_GAMER.getHomePage());
        } else if (roles.contains(Role.ROLE_AUTHOR)) {
            response.sendRedirect(Role.ROLE_AUTHOR.getHomePage());
        } else {
            response.sendRedirect(Role.ROLE_GUEST.getHomePage());
        }
    }
}
package com.mposhatov.springUtil;

import com.mposhatov.dto.ClientSession;
import com.mposhatov.dao.RegisteredClientRepository;
import com.mposhatov.entity.DbRegisteredClient;
import com.mposhatov.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

//    @Autowired
//    private SessionManager sessionManager;

    @Autowired
    private RegisteredClientRepository clientRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        final User user = (User) authentication.getPrincipal();

        final DbRegisteredClient client = clientRepository.findByName(user.getUsername());
        final List<Role> roles = client.getRoles();

//        final DbActiveSession dbActiveSession = sessionManager.createSession(
//                user.getUsername(), request.getRemoteAddr(), request.getHeader("User-Agent"));

//        final DbRegisteredClient dbRegisteredClient = dbActiveSession.getClient();
//        final List<Role> roles = dbRegisteredClient.getRoles();

//        request.getSession().removeAttribute(ClientSession.class.getName());

        request.getSession().setAttribute(ClientSession.class.getName(), new ClientSession(client.getId(), client.getRoles()));
//        request.getSession().setAttribute(ClientSession.class.getName(), EntityConverter.toClientSession(dbActiveSession));

        if (client.getRoles().contains(Role.ROLE_ADMIN)) {
            response.sendRedirect(Role.ROLE_ADMIN.getHomePage());
        } else if (roles.contains(Role.ROLE_GAMER)) {
            response.sendRedirect(Role.ROLE_GAMER.getHomePage());
        } else if (roles.contains(Role.ROLE_AUTHOR)) {
            response.sendRedirect(Role.ROLE_AUTHOR.getHomePage());
        } else {
            response.sendRedirect(Role.ROLE_ANONYMOUS.getHomePage());
        }
    }
}

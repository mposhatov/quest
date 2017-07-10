package com.mposhatov.springUtil;

import com.mposhatov.dao.ActiveSessionRepository;
import com.mposhatov.dto.Client;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.DbActiveSession;
import com.mposhatov.entity.Role;
import com.mposhatov.service.SessionService;
import com.mposhatov.util.EntityConverter;
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

    @Autowired
    private SessionService sessionService;

    @Autowired
    private ActiveSessionRepository activeSessionRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        final User user = (User) authentication.getPrincipal();

        final DbActiveSession session = sessionService.createSession(
                user.getUsername(), request.getRemoteAddr(), request.getHeader("User-Agent"));

        final DbClient client = session.getClient();
        final List<Role> roles = client.getRoles();

        request.getSession().setAttribute(Client.class.getName(), EntityConverter.toClient(client));
        request.getSession().setAttribute(ClientSession.class.getName(), EntityConverter.toClientSession(session));

        if (roles.contains(Role.ROLE_ADMIN)) {
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

package com.mposhatov.spring.util;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.DbHero;
import com.mposhatov.util.HomePageResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        final User user = (User) authentication.getPrincipal();

        final DbClient client = clientRepository.findByLogin(user.getUsername());

        final DbHero dbHero = client.getHero();

        request.getSession().setMaxInactiveInterval((int) TimeUnit.DAYS.toMillis(7));//todo properties;

        request.getSession().setAttribute(ClientSession.class.getName(), new ClientSession(client.getId(),
                dbHero.getId(), client.getRoles()));

        getRedirectStrategy().sendRedirect(request, response, HomePageResolver.redirectToHomePage());
    }
}

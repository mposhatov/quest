package com.mposhatov.springUtil;

import com.mposhatov.dao.ActiveGameRepository;
import com.mposhatov.dao.AnonymousClientRepository;
import com.mposhatov.dao.RegisteredClientRepository;
import com.mposhatov.entity.DbActiveGame;
import com.mposhatov.entity.DbAnonymousClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

@Component
public class SessionManagement implements HttpSessionListener, ApplicationContextAware {

    private final Logger logger = LoggerFactory.getLogger(SessionManagement.class);

    @Autowired
    private AnonymousClientRepository anonymousClientRepository;

    @Autowired
    private RegisteredClientRepository registeredClientRepository;

    @Autowired
    private ActiveGameRepository activeGameRepository;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        final DbAnonymousClient client = anonymousClientRepository.findByJsessionId(event.getSession().getId());

        if(client != null) {
            final List<DbActiveGame> activeGames = activeGameRepository.findByClient(client.getId(), true);

            if(activeGames != null) {
                activeGameRepository.delete(activeGames);
            }

            anonymousClientRepository.delete(client);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            ((WebApplicationContext) applicationContext).getServletContext().addListener(this);
        } catch (RuntimeException e) {
            logger.error("Unable to add session listener.", e);
//            throw e;//todo add
        }
    }
}

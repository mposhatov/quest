package com.mposhatov.springUtil;

import com.mposhatov.controller.Client;
import com.mposhatov.dao.AnonymousClientRepository;
import com.mposhatov.entity.DbAnonymousClient;
import com.mposhatov.entity.Role;
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
import java.util.Collections;

@Component
public class SessionManagement implements HttpSessionListener, ApplicationContextAware {

    private final Logger logger = LoggerFactory.getLogger(SessionManagement.class);

    @Autowired
    private AnonymousClientRepository anonymousClientRepository;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        final DbAnonymousClient client = anonymousClientRepository.save(new DbAnonymousClient(event.getSession().getId()));
        event.getSession().setAttribute(Client.class.getName(),
                new Client(client.getId(), Collections.singletonList(Role.ROLE_ANONYMOUS)));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        final DbAnonymousClient client = anonymousClientRepository.findByJsessionId(event.getSession().getId());
        if(client != null) {
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

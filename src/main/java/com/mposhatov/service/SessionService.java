package com.mposhatov.service;

import com.mposhatov.dao.ActiveSessionRepository;
import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dao.ClosedSessionRepository;
import com.mposhatov.entity.ClientStatus;
import com.mposhatov.entity.DbActiveSession;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.DbClosedSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SessionService {

    private final Logger logger = LoggerFactory.getLogger(SessionService.class);

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ActiveSessionRepository activeSessionRepository;

    @Autowired
    private ClosedSessionRepository closedSessionRepository;

    public DbActiveSession createSession(String clientName, String ip, String userAgent) {
        final DbClient client = clientRepository.findByName(clientName);
        DbActiveSession activeSession = activeSessionRepository.findByClient(client);
        if (activeSession == null) {
            activeSession = activeSessionRepository.save(
                    new DbActiveSession(client, ClientStatus.ONLINE, ip, userAgent));
        }

        return activeSession;
    }

    public DbClosedSession deleteSession(String clientName) {
        final DbClient client = clientRepository.findByName(clientName);
        final DbActiveSession activeSession = activeSessionRepository.findByClient(client);

        final DbClosedSession closedSession = new DbClosedSession(
                activeSession.getClient(), activeSession.getCreatedAt(), activeSession.getIp(), activeSession.getUserAgent());

        activeSessionRepository.delete(activeSession);
        return closedSessionRepository.save(closedSession);
    }

    @EventListener
    public void onSessionCreated(HttpSessionCreatedEvent event) {
        event.getSession().setMaxInactiveInterval(1);
    }

    @EventListener
    public void sessionDestroyed(HttpSessionDestroyedEvent event) {
        final DbClient client = clientRepository.findByJsessionId(event.getSession().getId());
        clientRepository.delete(client);
    }


}

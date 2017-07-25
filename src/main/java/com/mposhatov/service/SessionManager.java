package com.mposhatov.service;

import com.mposhatov.dao.ActiveSessionRepository;
import com.mposhatov.dao.AnonymousClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SessionManager {

    private final Logger logger = LoggerFactory.getLogger(SessionManager.class);

    @Autowired
    private ActiveSessionRepository activeSessionRepository;

    @Autowired
    private AnonymousClientRepository anonymousClientRepository;

//    private static Map<String, HttpSession> httpSessionByJSessionIds = new ConcurrentHashMap<>();

//    public DbActiveSession createSession(String clientName, String ip, String userAgent) {
//        final DbRegisteredClient client = registeredClientRepository.findByName(clientName);
//        DbActiveSession activeSession = activeSessionRepository.findByClient(client);
//        if (activeSession == null) {
//            activeSession = activeSessionRepository.save(
//                    new DbActiveSession(client, ClientStatus.ONLINE, ip, userAgent));
//        }
//
//        return activeSession;
//    }
//
//    public DbClosedSession deleteSession(String clientName) {
//        final DbRegisteredClient client = registeredClientRepository.findByName(clientName);
//        final DbActiveSession activeSession = activeSessionRepository.findByClient(client);
//
//        final DbClosedSession closedSession = new DbClosedSession(
//                activeSession.getClient(), activeSession.getCreatedAt(), activeSession.getIp(), activeSession.getUserAgent());
//
//        activeSessionRepository.delete(activeSession);
//        return closedSessionRepository.save(closedSession);
//    }

//    @EventListener
//    public void onSessionCreated(HttpSessionCreatedEvent event) {
////        event.getSession().setMaxInactiveInterval(3);
//        final HttpSession session = event.getSession();
//        httpSessionByJSessionIds.put(session.getId(),session);
//    }
//
//    @EventListener
//    public void sessionDestroyed(HttpSessionDestroyedEvent event) {
//        final DbAnonymousClient client = anonymousClientRepository.findByJsessionId(event.getSession().getId());
//        if(client != null) {
//            anonymousClientRepository.delete(client);
//        }
//    }
}

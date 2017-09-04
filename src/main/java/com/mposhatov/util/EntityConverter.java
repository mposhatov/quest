package com.mposhatov.util;

import com.mposhatov.dto.Background;
import com.mposhatov.dto.Client;
import com.mposhatov.dto.Subject;
import com.mposhatov.entity.DbBackground;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.DbSubject;
import org.springframework.stereotype.Service;

@Service
public class EntityConverter {

    public static Client toClient(DbClient dbRegClient) {
        return new Client(dbRegClient.getId(), dbRegClient.getName(),
                dbRegClient.getPhoto() != null ? toBackground(dbRegClient.getPhoto()) : null,
                dbRegClient.getHero().getLevel(), dbRegClient.getHero().getExperience());
    }

    public static Background toBackground(DbBackground dbBackground) {
        return new Background(dbBackground.getId(), dbBackground.getContentType());
    }

    public static Subject toSubject(DbSubject dbSubject) {
        return new Subject(dbSubject.getId(), dbSubject.getName());
    }


}

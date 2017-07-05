package com.mposhatov.util;

import com.mposhatov.dto.Client;
import com.mposhatov.entity.DbClient;
import org.springframework.stereotype.Service;

@Service
public class EntityConverter {

    public static Client toClient(DbClient dbClient) {
        return new Client(dbClient.getId(), dbClient.getName());
    }
}

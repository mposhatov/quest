package com.mposhatov.dao;

import com.mposhatov.entity.DbAnonymousActiveGame;
import com.mposhatov.entity.DbAnonymousClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnonymousActiveGameRepository extends JpaRepository<DbAnonymousActiveGame, Long>{
    DbAnonymousActiveGame findByClient(DbAnonymousClient client);
}

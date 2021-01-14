package com.botframework.sample.echobot.repo;

import com.botframework.sample.echobot.domain.ChannelAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelAccountRepo extends JpaRepository<ChannelAccount, UUID> {

    @Query("select distinct u from ChannelAccount u where u.name = :name")
    Optional<List<ChannelAccount>> findByName(String name);
}
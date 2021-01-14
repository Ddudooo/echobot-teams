package com.botframework.sample.echobot.repo;

import com.botframework.sample.echobot.domain.ChannelBotAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChannelBotAccountRepo extends JpaRepository<ChannelBotAccount, UUID> {

}

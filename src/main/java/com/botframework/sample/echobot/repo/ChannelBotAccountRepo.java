package com.botframework.sample.echobot.repo;

import com.botframework.sample.echobot.domain.ChannelBotAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ChannelBotAccountRepo extends CrudRepository<ChannelBotAccount, UUID> {

}

package com.botframework.sample.echobot.repo;

import com.botframework.sample.echobot.domain.ChannelAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ChannelAccountRepo extends CrudRepository<ChannelAccount, UUID> {

}
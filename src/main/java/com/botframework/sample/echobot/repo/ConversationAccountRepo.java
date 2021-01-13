package com.botframework.sample.echobot.repo;

import com.botframework.sample.echobot.domain.ConversationAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ConversationAccountRepo extends CrudRepository<ConversationAccount, UUID> {

}

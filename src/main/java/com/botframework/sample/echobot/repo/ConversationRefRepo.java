package com.botframework.sample.echobot.repo;

import com.botframework.sample.echobot.domain.ConversationRef;
import org.springframework.data.repository.CrudRepository;

public interface ConversationRefRepo extends CrudRepository<ConversationRef, Long> {

}

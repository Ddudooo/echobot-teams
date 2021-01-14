package com.botframework.sample.echobot.repo;

import com.botframework.sample.echobot.domain.ConversationAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConversationAccountRepo extends JpaRepository<ConversationAccount, UUID> {

}

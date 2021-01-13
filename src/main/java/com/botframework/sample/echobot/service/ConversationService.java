package com.botframework.sample.echobot.service;

import com.botframework.sample.echobot.domain.ChannelAccount;
import com.botframework.sample.echobot.domain.ChannelBotAccount;
import com.botframework.sample.echobot.domain.ConversationAccount;
import com.botframework.sample.echobot.domain.ConversationRef;
import com.botframework.sample.echobot.repo.ChannelAccountRepo;
import com.botframework.sample.echobot.repo.ChannelBotAccountRepo;
import com.botframework.sample.echobot.repo.ConversationAccountRepo;
import com.botframework.sample.echobot.repo.ConversationRefRepo;
import com.microsoft.bot.schema.ConversationReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ConversationService {

    private final ChannelAccountRepo userRepo;
    private final ChannelBotAccountRepo botRepo;
    private final ConversationAccountRepo conversationRepo;
    private final ConversationRefRepo refRepo;

    public void saveConversation(ConversationReference reference) {
        ConversationRef refEntity = new ConversationRef(reference);
        saveBotAccount(refEntity.get_bot());
        saveUserAccount(refEntity.get_user());
        saveConversationAccount(refEntity.get_conversation());
        saveRef(refEntity);
    }

    private ConversationRef saveRef(ConversationRef ref) {
        return refRepo.save(ref);
    }

    private ConversationAccount saveConversationAccount(ConversationAccount account) {
        return conversationRepo.save(account);
    }

    private ChannelBotAccount saveBotAccount(ChannelBotAccount bot) {
        return botRepo.save(bot);
    }

    private ChannelAccount saveUserAccount(ChannelAccount user) {
        return userRepo.save(user);
    }

    @Transactional(readOnly = true)
    public List<ConversationRef> findAllRef() {
        List<ConversationRef> refs = new ArrayList<>();
        refRepo.findAll().forEach(refs::add);
        return refs;
    }
}

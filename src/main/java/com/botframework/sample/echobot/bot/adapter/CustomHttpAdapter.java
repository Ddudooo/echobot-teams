package com.botframework.sample.echobot.bot.adapter;

import com.botframework.sample.echobot.bot.adapter.provider.CustomCredentialProvider;
import com.botframework.sample.echobot.domain.ConversationRef;
import com.botframework.sample.echobot.service.ConversationService;
import com.microsoft.bot.builder.Bot;
import com.microsoft.bot.builder.BotFrameworkAdapter;
import com.microsoft.bot.builder.InvokeResponse;
import com.microsoft.bot.connector.authentication.ChannelProvider;
import com.microsoft.bot.connector.authentication.CredentialProvider;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.ChannelAccount;
import com.microsoft.bot.schema.ConversationAccount;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 라이브러리상에서 지원하는 Http 어뎁터의 프로파일 읽기 문제로 따로 생성.
 *
 * @see com.microsoft.bot.integration.BotFrameworkHttpAdapter
 */
@Slf4j
public class CustomHttpAdapter extends BotFrameworkAdapter {

    @Getter(value = AccessLevel.PRIVATE)
    private String appId;
    @Getter(value = AccessLevel.PRIVATE)
    private String password;
    private ConversationService conversationService;

    public CustomHttpAdapter(String appId, String password,
        ConversationService conversationService) {
        super(new CustomCredentialProvider(appId, password));
        this.appId = appId;
        this.password = password;
        this.conversationService = conversationService;
    }

    public CustomHttpAdapter(String appId, String password) {
        super(new CustomCredentialProvider(appId, password));
        this.appId = appId;
        this.password = password;
    }

    public CustomHttpAdapter(
        CredentialProvider withCredentialProvider,
        ChannelProvider withChannelProvider
    ) {
        super(withCredentialProvider, withChannelProvider, null, null);
    }

    public CompletableFuture<InvokeResponse> processIncomingActivity(String authHeader,
        Activity activity, Bot bot) {
        return processActivity(authHeader, activity, bot::onTurn);
    }

    public String sendMsg(String msg) {
        List<ConversationRef> refList = conversationService.findAllRef();
        for (ConversationRef ref : refList) {
            log.info("ref {} ", ref);
            log.info("ref {}", ref.getUuid());
            ChannelAccount userAccount = ref.get_user();
            log.info("user Account [{}]{}", userAccount.getAadObjectId(), userAccount.getName());
            ref.setUser(ChannelAccount.clone(userAccount));
            ChannelAccount botAccount = ref.get_bot();
            log.info("bot account [{}]{}", botAccount.getAadObjectId(), botAccount.getName());
            ref.setBot(ChannelAccount.clone(botAccount));
            ConversationAccount conversationAccount = ref.get_conversation();
            log.info("conversation Account [{}]{}", conversationAccount.getAadObjectId(),
                conversationAccount.getName());
            ref.setConversation(ConversationAccount.clone(conversationAccount));
            this.continueConversation(appId,
                ref, turnContext -> turnContext.sendActivity("send " + msg)
                    .thenApply(resourceResponse -> null));
        }
        return "msg " + msg;
    }
}

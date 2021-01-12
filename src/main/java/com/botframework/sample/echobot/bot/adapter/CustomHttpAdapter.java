package com.botframework.sample.echobot.bot.adapter;

import com.botframework.sample.echobot.bot.adapter.provider.CustomCredentialProvider;
import com.botframework.sample.echobot.domain.ChannelAccount;
import com.botframework.sample.echobot.domain.ConversationRef;
import com.botframework.sample.echobot.repo.ConversationRefRepo;
import com.microsoft.bot.builder.Bot;
import com.microsoft.bot.builder.BotFrameworkAdapter;
import com.microsoft.bot.builder.InvokeResponse;
import com.microsoft.bot.connector.authentication.ChannelProvider;
import com.microsoft.bot.connector.authentication.CredentialProvider;
import com.microsoft.bot.schema.Activity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
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
    private ConversationRefRepo refRepo;

    public CustomHttpAdapter(String appId, String password, ConversationRefRepo refRepo) {
        super(new CustomCredentialProvider(appId, password));
        this.appId = appId;
        this.password = password;
        this.refRepo = refRepo;
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
        List<ConversationRef> refList = new ArrayList<>();
        refRepo.findAll().forEach(refList::add);
        for (ConversationRef ref : refList) {
            log.info("ref {}", ref.get_id());
            ChannelAccount account = ref.get_user();
            log.info("user acount [{}]{}", account.getAadObjectId(), account.getName());
            ref.setUser(account);
            this.continueConversation(appId,
                ref, turnContext -> turnContext.sendActivity("send " + msg)
                    .thenApply(resourceResponse -> null));
        }
        return "msg " + msg;
    }
}

package com.botframework.sample.echobot.bot.adapter;

import com.microsoft.bot.builder.Bot;
import com.microsoft.bot.builder.BotFrameworkAdapter;
import com.microsoft.bot.builder.InvokeResponse;
import com.microsoft.bot.connector.authentication.ChannelProvider;
import com.microsoft.bot.connector.authentication.CredentialProvider;
import com.microsoft.bot.schema.Activity;

import java.util.concurrent.CompletableFuture;

/**
 * 라이브러리상에서 지원하는 Http 어뎁터의 프로파일 읽기 문제로 따로 생성.
 *
 * @see com.microsoft.bot.integration.BotFrameworkHttpAdapter
 */
public class CustomHttpAdapter extends BotFrameworkAdapter {

    public CustomHttpAdapter(
        CredentialProvider withCredentialProvider) {
        super(withCredentialProvider);
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
}

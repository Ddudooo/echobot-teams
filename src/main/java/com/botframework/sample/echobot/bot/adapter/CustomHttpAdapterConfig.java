package com.botframework.sample.echobot.bot.adapter;

import com.botframework.sample.echobot.service.ConversationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 라이브러리에서 제공하는 기본 어뎁터 프로파일 읽기 문제로 재생성.
 *
 * @see com.microsoft.bot.integration.BotFrameworkHttpAdapter
 */
@Configuration
public class CustomHttpAdapterConfig {

    @Value("${bot.ms.credentials.appId}")
    private String appId;
    @Value("${bot.ms.credentials.password}")
    private String password;

    @Bean
    public CustomHttpAdapter getCustomHttpAdapter(ConversationService conversationService) {
        return new CustomHttpAdapter(appId, password, conversationService);
    }
}

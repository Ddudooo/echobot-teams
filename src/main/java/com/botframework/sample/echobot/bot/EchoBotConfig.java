package com.botframework.sample.echobot.bot;

import com.microsoft.bot.builder.Bot;
import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.TurnContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * 단순 입력값 반환 기능.
 *
 * @see Bot
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EchoBotConfig implements Bot {

    @Override
    public CompletableFuture<Void> onTurn(TurnContext turnContext) {
        return turnContext.sendActivity(
            MessageFactory.text(turnContext.getActivity().getText())
        ).thenApply(resourceResponse -> null);
    }
}

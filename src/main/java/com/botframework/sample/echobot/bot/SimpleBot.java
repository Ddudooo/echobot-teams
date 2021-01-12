package com.botframework.sample.echobot.bot;

import com.botframework.sample.echobot.domain.ConversationRef;
import com.botframework.sample.echobot.repo.ConversationRefRepo;
import com.microsoft.bot.builder.ActivityHandler;
import com.microsoft.bot.builder.BotFrameworkAdapter;
import com.microsoft.bot.builder.InvokeResponse;
import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.ActivityTypes;
import com.microsoft.bot.schema.ChannelAccount;
import com.microsoft.bot.schema.ConversationAccount;
import com.microsoft.bot.schema.ConversationReference;
import com.microsoft.bot.schema.MessageReaction;
import com.microsoft.bot.schema.ResourceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 단순 입력값 반환 기능.
 *
 * @see ActivityHandler
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleBot extends ActivityHandler {

    private final ConversationRefRepo refRepo;

    @Override
    protected CompletableFuture<Void> onMessageActivity(TurnContext turnContext) {
        String text = turnContext.getActivity().getText();
        log.info("[{}] send [{}]", turnContext.getActivity().getFrom().getAadObjectId(),
            text);
        ConversationAccount conversationAccount = turnContext.getActivity().getConversation();
        log.info("conversation id [{}]", conversationAccount.getId());
        log.info("conversation type [{}]", conversationAccount.getConversationType());
        ConversationReference ref = turnContext.getActivity().getConversationReference();
        log.info("conversation ref id - [{}]", ref.getActivityId());
        log.info("conversation ref channel id - [{}]", ref.getChannelId());
        ChannelAccount account = ref.getUser();
        log.info("Channel account [{}]", account.getAadObjectId());
        log.info("Channel account [{}]", account.getId());
        ConversationRef refEntity = new ConversationRef(ref);
        refRepo.save(refEntity);
        turnContext.sendActivity(
            MessageFactory.text(turnContext.getActivity().getText())
        ).thenApply(resourceResponse -> null);
        return super.onMessageActivity(turnContext);
    }

    @Override
    protected CompletableFuture<Void> onConversationUpdateActivity(TurnContext turnContext) {
        log.info("Conversation Update {}", turnContext.getActivity());
        return super.onConversationUpdateActivity(turnContext);
    }

    @Override
    protected CompletableFuture<Void> onMembersAdded(List<ChannelAccount> membersAdded,
        TurnContext turnContext) {
        log.info("Members Added...{}", membersAdded);
        return super.onMembersAdded(membersAdded, turnContext);
    }

    @Override
    protected CompletableFuture<Void> onMembersRemoved(List<ChannelAccount> membersRemoved,
        TurnContext turnContext) {
        log.info("Members Removed...{}", membersRemoved);
        return super.onMembersRemoved(membersRemoved, turnContext);
    }

    @Override
    protected CompletableFuture<Void> onMessageReactionActivity(TurnContext turnContext) {
        return super.onMessageReactionActivity(turnContext);
    }

    @Override
    protected CompletableFuture<Void> onReactionsAdded(List<MessageReaction> messageReactions,
        TurnContext turnContext) {
        return super.onReactionsAdded(messageReactions, turnContext);
    }

    @Override
    protected CompletableFuture<Void> onReactionsRemoved(List<MessageReaction> messageReactions,
        TurnContext turnContext) {
        return super.onReactionsRemoved(messageReactions, turnContext);
    }

    @Override
    protected CompletableFuture<Void> onEventActivity(TurnContext turnContext) {
        return super.onEventActivity(turnContext);
    }

    @Override
    protected CompletableFuture<InvokeResponse> onInvokeActivity(TurnContext turnContext) {
        return super.onInvokeActivity(turnContext);
    }

    @Override
    protected CompletableFuture<Void> onSignInInvoke(TurnContext turnContext) {
        return super.onSignInInvoke(turnContext);
    }

    @Override
    protected CompletableFuture<Void> onTokenResponseEvent(TurnContext turnContext) {
        return super.onTokenResponseEvent(turnContext);
    }

    @Override
    protected CompletableFuture<Void> onEvent(TurnContext turnContext) {
        log.info("EVENT {}", turnContext.getActivity().getName());
        return super.onEvent(turnContext);
    }

    @Override
    protected CompletableFuture<Void> onInstallationUpdate(TurnContext turnContext) {
        return super.onInstallationUpdate(turnContext);
    }

    @Override
    protected CompletableFuture<Void> onUnrecognizedActivityType(TurnContext turnContext) {
        return super.onUnrecognizedActivityType(turnContext);
    }

    @Override
    public CompletableFuture<Void> onTurn(TurnContext turnContext) {
        ChannelAccount from = turnContext.getActivity().getFrom();
        log.info("From Account id {}", from.getAadObjectId());
        log.info("receive text {}", turnContext.getActivity().getText());
        String type = turnContext.getActivity().getType();
        log.info("TYPE {}", type);
        Assert.notNull(turnContext, "TurnContext cannot be null.");
        Assert.notNull(turnContext.getActivity(), "turnContext must have a non-null Activity.");
        Assert.notNull(turnContext.getActivity().getType(),
            "turnContext.getActivity must have a non-null Type.");

        switch (turnContext.getActivity().getType()) {
            case ActivityTypes.MESSAGE:
                return onMessageActivity(turnContext);

            case ActivityTypes.CONVERSATION_UPDATE:
                return onConversationUpdateActivity(turnContext);

            case ActivityTypes.MESSAGE_REACTION:
                return onMessageReactionActivity(turnContext);

            case ActivityTypes.EVENT:
                return onEventActivity(turnContext);

            case ActivityTypes.INSTALLATION_UPDATE:
                return onInstallationUpdate(turnContext);

            case ActivityTypes.INVOKE:
                return onInvokeActivity(turnContext).thenCompose(invokeResponse -> {
                    // If OnInvokeActivityAsync has already sent an InvokeResponse, do not send
                    // another one.
                    if (
                        invokeResponse != null && turnContext.getTurnState()
                            .get(BotFrameworkAdapter.INVOKE_RESPONSE_KEY) == null
                    ) {

                        Activity activity = new Activity(ActivityTypes.INVOKE_RESPONSE);
                        activity.setValue(invokeResponse);

                        return turnContext.sendActivity(activity);
                    }

                    CompletableFuture<ResourceResponse> noAction = new CompletableFuture<>();
                    noAction.complete(null);
                    return noAction;
                }).thenApply(response -> null);

            default:
                return onUnrecognizedActivityType(turnContext);
        }
    }
}

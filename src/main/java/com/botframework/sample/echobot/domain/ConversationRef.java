package com.botframework.sample.echobot.domain;


import com.microsoft.bot.schema.ChannelAccount;
import com.microsoft.bot.schema.ConversationReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConversationRef extends ConversationReference {

    @Id
    @GeneratedValue
    @Column(name = "CONVERSATION_REF_ID")
    private Long _id;
    @Column(name = "ACTIVITY_ID")
    private String activityId;
    @Column(name = "CHANNEL_ID")
    private String channelId;
    @Column(name = "LOCALE")
    private String locale;
    @Column(name = "SERVICE_URL")
    private String serviceUrl;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "CHANNEL_BOT_ACCOUNT_ID")
    private com.botframework.sample.echobot.domain.ChannelBotAccount _bot;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "CHANNEL_ACCOUNT_ID")
    private com.botframework.sample.echobot.domain.ChannelAccount _user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "CONVERSATION_ACCOUNT_ID")
    private ConversationAccount _conversation;

    public ConversationRef(ConversationReference ref) {
        this.setActivityId(ref.getActivityId());
        this.setBot(ChannelAccount.clone(ref.getBot()));
        this._bot = new com.botframework.sample.echobot.domain.ChannelBotAccount(this.getBot());
        this.setUser(ChannelAccount.clone(ref.getUser()));
        this._user = new com.botframework.sample.echobot.domain.ChannelAccount(this.getUser());
        this.setConversation(ConversationAccount.clone(ref.getConversation()));
        this._conversation = new ConversationAccount(this.getConversation());
        this.setServiceUrl(ref.getServiceUrl());
        this.setLocale(ref.getLocale());
        this.setChannelId(ref.getChannelId());
    }

    @Override
    public void setActivityId(String withActivityId) {
        this.activityId = withActivityId;
        super.setActivityId(withActivityId);
    }

    @Override
    public void setChannelId(String withChannelId) {
        this.channelId = withChannelId;
        super.setChannelId(withChannelId);
    }

    @Override
    public void setLocale(String withLocale) {
        this.locale = withLocale;
        super.setLocale(withLocale);
    }

    @Override
    public void setServiceUrl(String withServiceUrl) {
        this.serviceUrl = withServiceUrl;
        super.setServiceUrl(withServiceUrl);
    }
}

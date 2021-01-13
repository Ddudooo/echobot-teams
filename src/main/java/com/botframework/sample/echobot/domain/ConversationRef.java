package com.botframework.sample.echobot.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microsoft.bot.schema.ChannelAccount;
import com.microsoft.bot.schema.ConversationReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;
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
public class ConversationRef extends ConversationReference implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 8873176198260270232L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "REF_ID")
    private UUID uuid;
    @Column(name = "ACTIVITY_ID")
    private String activityId;
    @Column(name = "CHANNEL_ID")
    private String channelId;
    @Column(name = "LOCALE")
    private String locale;
    @Column(name = "SERVICE_URL")
    private String serviceUrl;

    @Setter(value = AccessLevel.PROTECTED)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, orphanRemoval = false)
    @JoinColumn(name = "CHANNEL_BOT_ACCOUNT_ID")
    private com.botframework.sample.echobot.domain.ChannelBotAccount _bot;

    @Setter(value = AccessLevel.PROTECTED)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, orphanRemoval = false)
    @JoinColumn(name = "CHANNEL_ACCOUNT_ID")
    private com.botframework.sample.echobot.domain.ChannelAccount _user;

    @Setter(value = AccessLevel.PROTECTED)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, orphanRemoval = false)
    @JoinColumn(name = "CONVERSATION_ACCOUNT_ID")
    private ConversationAccount _conversation;

    public ConversationRef(ConversationReference ref) {
        this.setActivityId(ref.getActivityId());
        this.setBot(ChannelAccount.clone(ref.getBot()));
        this.set_bot(new ChannelBotAccount(this.getBot(), this));
        this.setUser(ChannelAccount.clone(ref.getUser()));
        this.set_user(
            new com.botframework.sample.echobot.domain.ChannelAccount(this.getUser(), this));
        this.setConversation(ConversationAccount.clone(ref.getConversation()));
        this.set_conversation(new ConversationAccount(this.getConversation(), this));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConversationRef that = (ConversationRef) o;

        return new EqualsBuilder()
            .append(getActivityId(), that.getActivityId())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(getActivityId())
            .toHashCode();
    }

    @Override
    public String toString() {
        return "ConversationRef{" +
            "uuid=" + uuid +
            ", activityId='" + activityId + '\'' +
            ", channelId='" + channelId + '\'' +
            ", locale='" + locale + '\'' +
            ", serviceUrl='" + serviceUrl + '\'' +
            '}';
    }
}

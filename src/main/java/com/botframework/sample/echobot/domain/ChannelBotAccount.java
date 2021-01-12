package com.botframework.sample.echobot.domain;


import com.microsoft.bot.schema.RoleTypes;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChannelBotAccount extends com.microsoft.bot.schema.ChannelAccount {

    @Id
    @GeneratedValue
    @Column(name = "CHANNEL_BOT_ACCOUNT_ID")
    private Long _id;

    @OneToOne(mappedBy = "_bot")
    private ConversationRef ref;

    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AAD_OBJECT_ID")
    private String aadObjectId;

    @Column(name = "ROLE")
    @Enumerated(value = EnumType.STRING)
    private RoleTypes role;


    public ChannelBotAccount(com.microsoft.bot.schema.ChannelAccount account) {
        this.setId(account.getId());
        this.setRole(account.getRole());
        this.setName(account.getName());
        this.setAadObjectId(account.getAadObjectId());

        for (String key : account.getProperties().keySet()) {
            this.setProperties(key, account.getProperties().get(key));
        }
    }

    @Override
    public void setId(String withId) {
        this.id = withId;
        super.setId(withId);
    }

    @Override
    public void setName(String withName) {
        this.name = withName;
        super.setName(withName);
    }

    @Override
    public void setRole(RoleTypes withRole) {
        this.role = withRole;
        super.setRole(withRole);
    }

    @Override
    public void setAadObjectId(String withAadObjectId) {
        this.aadObjectId = withAadObjectId;
        super.setAadObjectId(withAadObjectId);
    }
}

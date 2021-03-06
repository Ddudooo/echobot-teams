package com.botframework.sample.echobot.domain;


import com.microsoft.bot.schema.RoleTypes;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChannelAccount extends com.microsoft.bot.schema.ChannelAccount {

    @Setter(value = AccessLevel.PROTECTED)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "_user")
    private ConversationRef ref;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "CHANNEL_ACCOUNT_ID")
    private UUID uuid;

    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AAD_OBJECT_ID")
    private String aadObjectId;

    @Column(name = "ROLE")
    @Enumerated(value = EnumType.STRING)
    private RoleTypes role;


    public ChannelAccount(com.microsoft.bot.schema.ChannelAccount account) {
        this.setId(account.getId());
        this.setRole(account.getRole());
        this.setName(account.getName());
        this.setAadObjectId(account.getAadObjectId());

        for (String key : account.getProperties().keySet()) {
            this.setProperties(key, account.getProperties().get(key));
        }
    }

    public ChannelAccount(com.microsoft.bot.schema.ChannelAccount account, ConversationRef ref) {
        this.setId(account.getId());
        this.setRole(account.getRole());
        this.setName(account.getName());
        this.setAadObjectId(account.getAadObjectId());
        this.setRef(ref);

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChannelAccount that = (ChannelAccount) o;

        return new EqualsBuilder()
            .append(getId(), that.getId())
            .append(getAadObjectId(), that.getAadObjectId())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(getId())
            .append(getAadObjectId())
            .toHashCode();
    }
}

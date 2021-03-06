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
public class ConversationAccount extends com.microsoft.bot.schema.ConversationAccount {

    @Setter(value = AccessLevel.PROTECTED)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "_conversation")
    private ConversationRef ref;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "CONVERSATION_ACCOUNT_ID")
    private UUID uuid;

    @Column(name = "IS_GROUP")
    private boolean isGroup = false;

    @Column(name = "CONVERSATION_TYPE")
    private String conversationType;

    @Column(name = "TENANT_ID")
    private String tenantId;

    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AAD_OBJECT_ID")
    private String aadObjectId;

    @Column(name = "ROLE")
    @Enumerated(value = EnumType.STRING)
    private RoleTypes role;

    public ConversationAccount(com.microsoft.bot.schema.ConversationAccount account) {
        this.setName(account.getName());
        this.setIsGroup(account.isGroup());
        this.setConversationType(account.getConversationType());
        this.setAadObjectId(account.getAadObjectId());
        this.setRole(account.getRole());
        this.setId(account.getId());
        this.setTenantId(account.getTenantId());

        for (String key : account.getProperties().keySet()) {
            this.setProperties(key, account.getProperties().get(key));
        }
    }

    public ConversationAccount(com.microsoft.bot.schema.ConversationAccount account,
        ConversationRef ref) {
        this.setName(account.getName());
        this.setIsGroup(account.isGroup());
        this.setConversationType(account.getConversationType());
        this.setAadObjectId(account.getAadObjectId());
        this.setRole(account.getRole());
        this.setId(account.getId());
        this.setTenantId(account.getTenantId());
        this.setRef(ref);

        for (String key : account.getProperties().keySet()) {
            this.setProperties(key, account.getProperties().get(key));
        }
    }

    @Override
    public void setIsGroup(boolean withIsGroup) {
        this.isGroup = withIsGroup;
        super.setIsGroup(withIsGroup);
    }

    @Override
    public void setConversationType(String withConversationType) {
        this.conversationType = withConversationType;
        super.setConversationType(withConversationType);
    }

    @Override
    public void setTenantId(String withTenantId) {
        this.tenantId = withTenantId;
        super.setTenantId(withTenantId);
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
    public void setAadObjectId(String withAadObjectId) {
        this.aadObjectId = withAadObjectId;
        super.setAadObjectId(withAadObjectId);
    }

    @Override
    public void setRole(RoleTypes withRole) {
        this.role = withRole;
        super.setRole(withRole);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConversationAccount account = (ConversationAccount) o;

        return new EqualsBuilder()
            .append(getId(), account.getId())
            .append(getRef(), account.getRef())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(getId())
            .append(getRef())
            .toHashCode();
    }
}

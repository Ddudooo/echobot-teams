package com.botframework.sample.echobot.repo;

import com.botframework.sample.echobot.domain.ConversationRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ConversationRefRepo extends JpaRepository<ConversationRef, UUID> {

    @Query("select r from ConversationRef r where r.activityId = :activityId")
    Optional<ConversationRef> findByActivityId(String activityId);

    @Async("customExecutor")
    @Query("select r from ConversationRef r")
    CompletableFuture<Optional<ConversationRef>> findLastByUserAccount();
}

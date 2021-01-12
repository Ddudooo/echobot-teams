package com.botframework.sample.echobot.rest;

import com.botframework.sample.echobot.bot.adapter.CustomHttpAdapter;
import com.microsoft.bot.builder.Bot;
import com.microsoft.bot.schema.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import javax.naming.AuthenticationException;

/**
 * 기본 컨트롤러 사용안하고 명시적으로 생성하여 사용.
 *
 * @see com.microsoft.bot.integration.spring.BotController
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class BotEndPoint {

    private final CustomHttpAdapter adapter;

    private final Bot bot;

    @PostMapping("/test/sample")
    public CompletableFuture<ResponseEntity<Object>> incoming(
        @RequestBody Activity activity,
        @RequestHeader(value = "Authorization", defaultValue = "") String authHeader
    ) {
        log.info("Activity {}", activity);
        log.info("Authorization {}", authHeader);
        return adapter.processIncomingActivity(authHeader, activity, bot)
            .handle((result, exception) -> {
                if (exception == null) {
                    if (result != null) {
                        return ResponseEntity.status(result.getStatus()).body(result.getBody());
                    } else {
                        return ResponseEntity.accepted().build();
                    }
                }
                log.error("Exception handling... {}", exception);
                if (exception instanceof CompletionException) {
                    if (exception.getCause() instanceof AuthenticationException) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            });
    }

    @GetMapping("/send/msg")
    public ResponseEntity<Object> sendMsg() {
        String response = adapter.sendMsg("test");
        return ResponseEntity.accepted().body(response);
    }
}

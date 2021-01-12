package com.botframework.sample.echobot.bot.adapter.provider;

import com.microsoft.bot.connector.authentication.CredentialProvider;
import com.microsoft.bot.connector.authentication.SimpleCredentialProvider;

/**
 * appid, password만 입력받는 Provider.
 *
 * @see SimpleCredentialProvider
 */
public class CustomCredentialProvider extends SimpleCredentialProvider implements
    CredentialProvider {

    public CustomCredentialProvider(String appId, String password) {
        setAppId(appId);
        setPassword(password);
    }
}

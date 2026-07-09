package com.example.urlshortener.application.port.out;

import com.example.urlshortener.domain.model.AccessToken;
import com.example.urlshortener.domain.model.User;

public interface TokenIssuer {
    AccessToken issueFor(User user);
}

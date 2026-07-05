package com.example.urlshotener.domain.model;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public record OriginalUrl(String url) {
    public OriginalUrl {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("URL can't be blank");
        }

        try {
            new URI(url).toURL();
        }  catch (URISyntaxException | MalformedURLException exception) {
            throw new IllegalArgumentException("Invalid URL: " + url);
        }
    }
}

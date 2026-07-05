package com.example.urlshortener.application.port.out;

import com.example.urlshortener.domain.model.DailyClickCounts;
import com.example.urlshortener.domain.model.ShortCode;
import com.example.urlshortener.domain.model.UrlAccess;

public interface UrlAccessRepository {
    void add(UrlAccess urlAccess);

    DailyClickCounts countPerDay(ShortCode shortCode);
}

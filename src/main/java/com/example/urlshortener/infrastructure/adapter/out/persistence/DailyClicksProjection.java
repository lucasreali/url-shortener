package com.example.urlshortener.infrastructure.adapter.out.persistence;

import java.time.LocalDate;

public interface DailyClicksProjection {
    LocalDate getDay();

    long getClicks();
}

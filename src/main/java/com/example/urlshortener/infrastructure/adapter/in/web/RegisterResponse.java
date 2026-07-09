package com.example.urlshortener.infrastructure.adapter.in.web;

import java.util.UUID;

public record RegisterResponse(UUID id, String email) {
}

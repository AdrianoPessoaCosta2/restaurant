package br.com.restaurants.infrastructure.controller.request;

public record LoginRequest(
        String login,
        String password
) {
}

package br.com.restaurants.login.adpter.request;

public record LoginRequest(
        String login,
        String password
) {
}

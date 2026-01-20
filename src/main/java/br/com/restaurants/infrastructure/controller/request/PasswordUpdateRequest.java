package br.com.restaurants.infrastructure.controller.request;

public record PasswordUpdateRequest(
        String currentPassword,
        String newPassword
) {
}

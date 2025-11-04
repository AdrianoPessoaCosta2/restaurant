package br.com.restaurants.user.adapter.controller.resquest;

public record PasswordUpdateRequest(
        String currentPassword,
        String newPassword
) {
}

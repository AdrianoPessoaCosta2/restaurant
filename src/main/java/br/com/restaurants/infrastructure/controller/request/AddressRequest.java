package br.com.restaurants.infrastructure.controller.request;

public record AddressRequest(
        Long id,
        String street,
        String numberAddress,
        String city,
        String state,
        String zipCode
) {
}

package br.com.restaurants.user.adapter.controller.resquest;

public record AddressRequest(
        Long id,
        String street,
        String numberAddress,
        String city,
        String state,
        String zipCode
) {
}

package br.com.restaurants.core.gateway;

import br.com.restaurants.core.entities.Address;

public interface AddressGateway {
    Address save(Address address);
    Address update(Address address);
}

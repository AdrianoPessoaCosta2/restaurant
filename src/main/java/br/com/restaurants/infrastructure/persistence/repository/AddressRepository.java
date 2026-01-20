package br.com.restaurants.infrastructure.persistence.repository;

import br.com.restaurants.core.entities.Address;

public interface AddressRepository {
    Address save(Address address);

    Address update(Address address);
}

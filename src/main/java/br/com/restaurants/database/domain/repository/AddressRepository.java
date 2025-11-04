package br.com.restaurants.database.domain.repository;

import br.com.restaurants.database.domain.entity.AddressEntity;

public interface AddressRepository {
    AddressEntity save(AddressEntity addressEntity);

    AddressEntity update(AddressEntity addressEntity);
}

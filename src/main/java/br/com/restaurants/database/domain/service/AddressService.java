package br.com.restaurants.database.domain.service;

import br.com.restaurants.database.domain.entity.AddressEntity;
import br.com.restaurants.database.domain.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public AddressEntity save(AddressEntity addressEntity) {
        return addressRepository.save(addressEntity);
    }

    public AddressEntity update(AddressEntity addressEntity) {
        return addressRepository.update(addressEntity);
    }
}

package br.com.restaurants.infrastructure.gateway;

import br.com.restaurants.core.entities.Address;
import br.com.restaurants.core.gateway.AddressGateway;
import br.com.restaurants.infrastructure.persistence.repository.AddressRepository;
import org.springframework.stereotype.Component;

@Component
public class AddressGatewayImpl implements AddressGateway {
    private AddressRepository addressRepository;

    public AddressGatewayImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address save(Address address) {
        return addressRepository.save(address);
    }
    public Address update(Address address) {
        return addressRepository.update(address);
    }
}


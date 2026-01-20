package br.com.restaurants.infrastructure.gateway;

import br.com.restaurants.core.entities.Address;
import br.com.restaurants.infrastructure.persistence.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressGatewayImplTest {

    @InjectMocks
    private AddressGatewayImpl gateway;

    @Mock
    private AddressRepository addressRepository;

    @Test
    void shouldSaveAddress() {
        Address address = new Address();
        Address savedAddress = new Address();

        when(addressRepository.save(address)).thenReturn(savedAddress);

        Address result = gateway.save(address);

        assertEquals(savedAddress, result);
        verify(addressRepository).save(address);
    }

    @Test
    void shouldUpdateAddress() {
        Address address = new Address();
        Address updatedAddress = new Address();

        when(addressRepository.update(address)).thenReturn(updatedAddress);

        Address result = gateway.update(address);

        assertEquals(updatedAddress, result);
        verify(addressRepository).update(address);
    }
}
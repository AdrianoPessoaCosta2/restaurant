package br.com.restaurants.core.usecases;

import br.com.restaurants.core.entities.Address;
import br.com.restaurants.core.entities.User;
import br.com.restaurants.core.gateway.AddressGateway;
import br.com.restaurants.core.gateway.UserGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private AddressGateway addressGateway;

    @InjectMocks
    private CreateUserUseCase useCase;

    @Test
    void shouldCreateUserSuccessfully() {
        Address address = new Address();
        Address savedAddress = new Address();
        savedAddress.setId(1L);

        User user = new User();
        user.setPassword("123456");
        user.setAddress(address);

        User savedUser = new User();
        savedUser.setId(10L);
        savedUser.setPassword("123456");
        savedUser.setAddress(savedAddress);

        when(addressGateway.save(address)).thenReturn(savedAddress);
        when(userGateway.save(user)).thenReturn(savedUser);

        User result = useCase.execute(user);

        assertNotNull(result);
        assertEquals(savedAddress, result.getAddress());
        assertEquals(savedUser, result);

        verify(addressGateway).save(address);
        verify(userGateway).save(user);
    }
}
package br.com.restaurants.core.usecases;

import br.com.restaurants.core.gateway.UserGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private DeleteUserUseCase useCase;

    @Test
    void shouldDeleteUserSuccessfully() {
        UUID id = UUID.randomUUID();

        useCase.execute(id);

        verify(userGateway).deleteById(id);
    }
}
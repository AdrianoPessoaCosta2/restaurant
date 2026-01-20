package br.com.restaurants.infrastructure.configuration;

import br.com.restaurants.core.gateway.AddressGateway;
import br.com.restaurants.core.gateway.UserGateway;
import br.com.restaurants.core.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCaseConfig {

    @Bean
    public FindAllUsersUseCase findAllUsersUseCase(UserGateway userGateway) {
        return new FindAllUsersUseCase(userGateway);
    }

    @Bean
    public FindUserByIdUseCase findUserByIdUseCase(UserGateway userGateway) {
        return new FindUserByIdUseCase(userGateway);
    }

    @Bean
    public FindUserByNameUseCase findUserByNameUseCase(UserGateway userGateway) {
        return new FindUserByNameUseCase(userGateway);
    }

    @Bean
    public CreateUserUseCase createUserUseCase(
            UserGateway userGateway,
            AddressGateway addressGateway
    ) {
        return new CreateUserUseCase(userGateway, addressGateway);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UserGateway userGateway) {
        return new UpdateUserUseCase(userGateway);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(UserGateway userGateway) {
        return new DeleteUserUseCase(userGateway);
    }

    @Bean
    public UpdateUserPasswordUseCase updateUserPasswordUseCase(
            UserGateway userGateway
    ) {
        return new UpdateUserPasswordUseCase(userGateway);
    }
}

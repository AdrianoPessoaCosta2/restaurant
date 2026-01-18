package br.com.restaurants.core.usecases;

import br.com.restaurants.core.entities.User;
import br.com.restaurants.core.gateway.UserGateway;

public class FindAllUser {

    private UserGateway userGateway;

    private FindAllUser(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public static FindAllUser create(UserGateway userGateway) {
        return new FindAllUser(userGateway);
    }

    public User run(){

    }
}

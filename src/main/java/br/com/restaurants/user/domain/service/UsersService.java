package br.com.restaurants.user.domain.service;

import br.com.restaurants.core.model.User;
import br.com.restaurants.database.adapter.UserPersistence;
import br.com.restaurants.exception.ResourceNotFoundException;
import br.com.restaurants.user.adapter.controller.resquest.UserUpdateRequest;
import br.com.restaurants.user.domain.mapper.UserControllerMapper;
import br.com.restaurants.user.adapter.controller.response.UserResponse;
import br.com.restaurants.user.adapter.controller.resquest.UserCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsersService {

    private UserControllerMapper userControllerMapper;
    private UserPersistence userPersistence;

    public UsersService(UserControllerMapper userControllerMapper, UserPersistence userPersistence) {
        this.userControllerMapper = userControllerMapper;
        this.userPersistence = userPersistence;
    }

    public List<UserResponse> findAll() {
        List<User> users = userPersistence.findAll();
        if(users.isEmpty()){
            throw new ResourceNotFoundException("Users not found");
        }
        return userControllerMapper.toResponse(users);
    }

    public UserResponse findByName(String name) {
        User user = userPersistence.findByName(name);
        return userControllerMapper.toResponse(user);
    }


    public UserResponse findById(UUID id) {
        User user = userPersistence.findById(id);
        return userControllerMapper.toResponse(user);
    }

    public UserResponse save(UserCreateRequest request) {
        User user = userControllerMapper.toUser(request);
        User userSaved = userPersistence.save(user);
        return userControllerMapper.toResponse(userSaved);
    }

    public UserResponse update(UUID id, UserUpdateRequest request) {
        User user = userControllerMapper.toUser(request, id);
        User userSaved = userPersistence.update(user);
        return userControllerMapper.toResponse(userSaved);
    }

    public void deleteById(UUID id) {
        userPersistence.deleteById(id);
    }

}

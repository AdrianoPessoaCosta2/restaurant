package br.com.restaurants.infrastructure.controller;

import br.com.restaurants.core.entities.User;
import br.com.restaurants.core.usecases.*;
import br.com.restaurants.infrastructure.controller.request.UserCreateRequest;
import br.com.restaurants.infrastructure.controller.request.UserUpdateRequest;
import br.com.restaurants.infrastructure.controller.response.UserResponse;
import br.com.restaurants.infrastructure.mapper.UserControllerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private CreateUserUseCase createUserUseCase;

    @Mock
    private FindAllUsersUseCase findAllUsersUseCase;

    @Mock
    private FindUserByIdUseCase findUserByIdUseCase;

    @Mock
    private FindUserByNameUseCase findUserByNameUseCase;

    @Mock
    private UpdateUserUseCase updateUserUseCase;

    @Mock
    private DeleteUserUseCase deleteUserUseCase;

    @Mock
    private UserControllerMapper userControllerMapper;

    @Mock
    private UserResponse userResponse;

    @Mock
    private UserCreateRequest createRequest;

    @Mock
    private UserUpdateRequest updateRequest;

    @Test
    void shouldReturnAllUsers() {
        User user = new User();
        List<User> users = List.of(user);
        List<UserResponse> responses = List.of(userResponse);

        when(findAllUsersUseCase.execute()).thenReturn(users);
        when(userControllerMapper.toResponse(users)).thenReturn(responses);

        List<UserResponse> result = controller.findAll();

        assertEquals(responses, result);
        verify(findAllUsersUseCase).execute();
    }

    @Test
    void shouldFindUserByName() {
        String name = "John";
        User user = new User();

        when(findUserByNameUseCase.execute(name)).thenReturn(user);
        when(userControllerMapper.toResponse(user)).thenReturn(userResponse);

        UserResponse result = controller.findByName(name);

        assertEquals(userResponse, result);
        verify(findUserByNameUseCase).execute(name);
    }

    @Test
    void shouldFindUserById() {
        UUID id = UUID.randomUUID();
        User user = new User();

        when(findUserByIdUseCase.execute(id)).thenReturn(user);
        when(userControllerMapper.toResponse(user)).thenReturn(userResponse);

        UserResponse result = controller.findById(id);

        assertEquals(userResponse, result);
        verify(findUserByIdUseCase).execute(id);
    }

    @Test
    void shouldSaveUser() {
        User userDomain = new User();
        User savedUser = new User();

        when(userControllerMapper.toUser(createRequest)).thenReturn(userDomain);
        when(createUserUseCase.execute(userDomain)).thenReturn(savedUser);
        when(userControllerMapper.toResponse(savedUser)).thenReturn(userResponse);

        UserResponse result = controller.save(createRequest);

        assertEquals(userResponse, result);
        verify(createUserUseCase).execute(userDomain);
    }

    @Test
    void shouldUpdateUser() {
        UUID id = UUID.randomUUID();
        User userDomain = new User();
        User updatedUser = new User();

        when(userControllerMapper.toUser(updateRequest, id)).thenReturn(userDomain);
        when(updateUserUseCase.execute(userDomain)).thenReturn(updatedUser);
        when(userControllerMapper.toResponse(updatedUser)).thenReturn(userResponse);

        UserResponse result = controller.update(id, updateRequest);

        assertEquals(userResponse, result);
        verify(updateUserUseCase).execute(userDomain);
    }

    @Test
    void shouldDeleteUserById() {
        UUID id = UUID.randomUUID();

        controller.deleteById(id);

        verify(deleteUserUseCase).execute(id);
    }
}
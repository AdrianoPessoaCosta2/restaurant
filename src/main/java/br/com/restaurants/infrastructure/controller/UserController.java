package br.com.restaurants.infrastructure.controller;

import br.com.restaurants.core.usecases.*;
import br.com.restaurants.infrastructure.controller.request.UserCreateRequest;
import br.com.restaurants.infrastructure.controller.request.UserUpdateRequest;
import br.com.restaurants.infrastructure.controller.response.UserResponse;
import br.com.restaurants.infrastructure.mapper.UserControllerMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Operações de usuários - v1")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final FindAllUsersUseCase findAllUsersUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final FindUserByNameUseCase findUserByNameUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    private final UserControllerMapper userControllerMapper;

    public UserController(
            CreateUserUseCase createUserUseCase,
            FindAllUsersUseCase findAllUsersUseCase,
            FindUserByIdUseCase findUserByIdUseCase,
            FindUserByNameUseCase findUserByNameUseCase,
            UpdateUserUseCase updateUserUseCase,
            DeleteUserUseCase deleteUserUseCase,
            UserControllerMapper userControllerMapper
    ) {
        this.createUserUseCase = createUserUseCase;
        this.findAllUsersUseCase = findAllUsersUseCase;
        this.findUserByIdUseCase = findUserByIdUseCase;
        this.findUserByNameUseCase = findUserByNameUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.userControllerMapper = userControllerMapper;
    }

    @GetMapping
    @Operation(summary = "Lista todos os usuários")
    public List<UserResponse> findAll() {
        return userControllerMapper.toResponse(findAllUsersUseCase.execute());
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Busca usuário pelo nome")
    public UserResponse findByName(@PathVariable String name) {
        return userControllerMapper.toResponse(findUserByNameUseCase.execute(name));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca usuário pelo ID")
    public UserResponse findById(@PathVariable UUID id) {
        return userControllerMapper.toResponse(findUserByIdUseCase.execute(id));
    }

    @PostMapping
    @Operation(summary = "Cria um novo usuário")
    public UserResponse save(@RequestBody UserCreateRequest request) {
        var userDomain = userControllerMapper.toUser(request);
        var savedUser = createUserUseCase.execute(userDomain);

        return userControllerMapper.toResponse(savedUser);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuário existente")
    public UserResponse update(@PathVariable UUID id, @RequestBody UserUpdateRequest request) {
        var userDomain = userControllerMapper.toUser(request, id);
        return userControllerMapper.toResponse(updateUserUseCase.execute(userDomain));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um usuário pelo ID")
    public void deleteById(@PathVariable UUID id) {
        deleteUserUseCase.execute(id);
    }
}
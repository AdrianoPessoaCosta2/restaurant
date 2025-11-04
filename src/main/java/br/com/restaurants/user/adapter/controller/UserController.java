package br.com.restaurants.user.adapter.controller;

import br.com.restaurants.user.adapter.controller.response.UserResponse;
import br.com.restaurants.user.adapter.controller.resquest.UserCreateRequest;
import br.com.restaurants.user.adapter.controller.resquest.UserUpdateRequest;
import br.com.restaurants.user.domain.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Operações de usuários - v1")
public class UserController {

    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os usuários")
    public List<UserResponse> findAll() {
        return usersService.findAll();
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Busca usuário pelo nome")
    public UserResponse findByName(@PathVariable String name) {
        return usersService.findByName(name);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca usuário pelo ID")
    public UserResponse findById(@PathVariable UUID id) {
        return usersService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Cria um novo usuário")
    public UserResponse save(@RequestBody UserCreateRequest request) {
        return usersService.save(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuário existente")
    public UserResponse update(@PathVariable UUID id, @RequestBody UserUpdateRequest request) {
        return usersService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um usuário pelo ID")
    public void deleteById(@PathVariable UUID id) {
        usersService.deleteById(id);
    }
}

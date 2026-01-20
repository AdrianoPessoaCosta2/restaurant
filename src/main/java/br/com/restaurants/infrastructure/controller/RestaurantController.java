package br.com.restaurants.infrastructure.controller;

import br.com.restaurants.core.entities.Restaurant;
import br.com.restaurants.core.usecases.*;
import br.com.restaurants.infrastructure.controller.request.RestaurantCreateRequest;
import br.com.restaurants.infrastructure.controller.response.RestaurantResponse;
import br.com.restaurants.infrastructure.mapper.RestaurantControllerMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/restaurants")
@Tag(name = "Restaurants", description = "Gestão de Restaurantes")
public class RestaurantController {

    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final FindAllRestaurantsUseCase findAllRestaurantsUseCase;
    private final FindRestaurantByIdUseCase findRestaurantByIdUseCase;
    private final UpdateRestaurantUseCase updateRestaurantUseCase;
    private final DeleteRestaurantUseCase deleteRestaurantUseCase;
    private final RestaurantControllerMapper restaurantMapper;

    public RestaurantController(
            CreateRestaurantUseCase createRestaurantUseCase,
            FindAllRestaurantsUseCase findAllRestaurantsUseCase,
            FindRestaurantByIdUseCase findRestaurantByIdUseCase,
            UpdateRestaurantUseCase updateRestaurantUseCase,
            DeleteRestaurantUseCase deleteRestaurantUseCase,
            RestaurantControllerMapper restaurantMapper
    ) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.findAllRestaurantsUseCase = findAllRestaurantsUseCase;
        this.findRestaurantByIdUseCase = findRestaurantByIdUseCase;
        this.updateRestaurantUseCase = updateRestaurantUseCase;
        this.deleteRestaurantUseCase = deleteRestaurantUseCase;
        this.restaurantMapper = restaurantMapper;
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo restaurante")
    public ResponseEntity<RestaurantResponse> save(@RequestBody RestaurantCreateRequest request) {
        Restaurant domain = restaurantMapper.toDomain(request);
        Restaurant saved = createRestaurantUseCase.execute(domain, request.ownerId());
        return ResponseEntity.ok(restaurantMapper.toResponse(saved));
    }

    @GetMapping
    @Operation(summary = "Lista todos os restaurantes")
    public ResponseEntity<List<RestaurantResponse>> findAll() {
        List<RestaurantResponse> response = findAllRestaurantsUseCase.execute()
                .stream()
                .map(restaurantMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca restaurante pelo ID")
    public ResponseEntity<RestaurantResponse> findById(@PathVariable UUID id) {
        Restaurant restaurant = findRestaurantByIdUseCase.execute(id);
        return ResponseEntity.ok(restaurantMapper.toResponse(restaurant));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um restaurante existente")
    public ResponseEntity<RestaurantResponse> update(@PathVariable UUID id, @RequestBody RestaurantCreateRequest request) {
        Restaurant domain = restaurantMapper.toDomain(request);
        domain.setPublicId(id);
        Restaurant updated = updateRestaurantUseCase.execute(domain);
        return ResponseEntity.ok(restaurantMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um restaurante pelo ID")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        deleteRestaurantUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
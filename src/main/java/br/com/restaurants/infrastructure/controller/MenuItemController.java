package br.com.restaurants.infrastructure.controller;

import br.com.restaurants.core.entities.MenuItem;
import br.com.restaurants.core.usecases.*;
import br.com.restaurants.infrastructure.controller.request.MenuItemRequest;
import br.com.restaurants.infrastructure.controller.response.MenuItemResponse;
import br.com.restaurants.infrastructure.mapper.MenuItemControllerMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/menu-items")
@Tag(name = "Menu Items", description = "Gestão de Itens do Cardápio")
public class MenuItemController {

    private final CreateMenuItemUseCase createMenuItemUseCase;
    private final FindMenuItemsByRestaurantUseCase findMenuItemsByRestaurantUseCase;
    private final MenuItemControllerMapper mapper;

    public MenuItemController(
            CreateMenuItemUseCase createMenuItemUseCase,
            FindMenuItemsByRestaurantUseCase findMenuItemsByRestaurantUseCase,
            MenuItemControllerMapper mapper) {
        this.createMenuItemUseCase = createMenuItemUseCase;
        this.findMenuItemsByRestaurantUseCase = findMenuItemsByRestaurantUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Adiciona um item ao cardápio")
    public ResponseEntity<MenuItemResponse> save(@RequestBody MenuItemRequest request) {
        MenuItem domain = mapper.toDomain(request);
        MenuItem saved = createMenuItemUseCase.execute(domain, request.restaurantId());
        return ResponseEntity.ok(mapper.toResponse(saved));
    }

    @GetMapping("/restaurant/{restaurantId}")
    @Operation(summary = "Lista itens de um restaurante específico")
    public ResponseEntity<List<MenuItemResponse>> findByRestaurant(@PathVariable UUID restaurantId) {
        List<MenuItem> items = findMenuItemsByRestaurantUseCase.execute(restaurantId);
        return ResponseEntity.ok(items.stream().map(mapper::toResponse).toList());
    }
}
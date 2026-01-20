package br.com.restaurants.infrastructure.controller;

import br.com.restaurants.core.entities.MenuItem;
import br.com.restaurants.core.usecases.CreateMenuItemUseCase;
import br.com.restaurants.core.usecases.FindMenuItemsByRestaurantUseCase;
import br.com.restaurants.infrastructure.controller.request.MenuItemRequest;
import br.com.restaurants.infrastructure.controller.response.MenuItemResponse;
import br.com.restaurants.infrastructure.mapper.MenuItemControllerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuItemControllerTest {

    @Mock
    private CreateMenuItemUseCase createMenuItemUseCase;
    @Mock
    private FindMenuItemsByRestaurantUseCase findMenuItemsByRestaurantUseCase;
    @Mock
    private MenuItemControllerMapper mapper;
    @InjectMocks
    private MenuItemController controller;

    @Test
    void shouldSaveMenuItem() {
        UUID resId = UUID.randomUUID();
        MenuItemRequest request = new MenuItemRequest("Pizza", "Cheese", BigDecimal.TEN, true, "path", resId);
        MenuItem domain = new MenuItem();
        MenuItem saved = new MenuItem();
        MenuItemResponse response = new MenuItemResponse(UUID.randomUUID(), "Pizza", "Cheese", BigDecimal.TEN, true, "path", resId);

        when(mapper.toDomain(request)).thenReturn(domain);
        when(createMenuItemUseCase.execute(domain, resId)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        ResponseEntity<MenuItemResponse> result = controller.save(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void shouldFindItemsByRestaurant() {
        UUID resId = UUID.randomUUID();
        MenuItem item = new MenuItem();
        MenuItemResponse response = new MenuItemResponse(UUID.randomUUID(), "Pizza", "Cheese", BigDecimal.TEN, true, "path", resId);

        when(findMenuItemsByRestaurantUseCase.execute(resId)).thenReturn(List.of(item));
        when(mapper.toResponse(item)).thenReturn(response);

        ResponseEntity<List<MenuItemResponse>> result = controller.findByRestaurant(resId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
    }
}
package br.com.restaurants.infrastructure.controller;

import br.com.restaurants.core.entities.Restaurant;
import br.com.restaurants.core.usecases.CreateRestaurantUseCase;
import br.com.restaurants.infrastructure.controller.request.RestaurantCreateRequest;
import br.com.restaurants.infrastructure.controller.response.RestaurantResponse;
import br.com.restaurants.infrastructure.mapper.RestaurantControllerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

    @Mock
    private CreateRestaurantUseCase createRestaurantUseCase;
    @Mock
    private RestaurantControllerMapper restaurantMapper;
    @InjectMocks
    private RestaurantController controller;

    @Test
    void shouldSaveRestaurant() {
        UUID ownerId = UUID.randomUUID();
        RestaurantCreateRequest request = new RestaurantCreateRequest(
                "Test Rest", "Italian", LocalTime.NOON, LocalTime.MIDNIGHT, null, ownerId
        );
        Restaurant domain = new Restaurant();
        Restaurant saved = new Restaurant();
        RestaurantResponse response = new RestaurantResponse(
                UUID.randomUUID(), "Test Rest", "Italian", LocalTime.NOON, LocalTime.MIDNIGHT, null, ownerId, "Owner"
        );

        when(restaurantMapper.toDomain(request)).thenReturn(domain);
        when(createRestaurantUseCase.execute(domain, ownerId)).thenReturn(saved);
        when(restaurantMapper.toResponse(saved)).thenReturn(response);

        ResponseEntity<RestaurantResponse> result = controller.save(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }
}
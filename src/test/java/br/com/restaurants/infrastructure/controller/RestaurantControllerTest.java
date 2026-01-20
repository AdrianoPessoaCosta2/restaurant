package br.com.restaurants.infrastructure.controller;

import br.com.restaurants.core.entities.Restaurant;
import br.com.restaurants.core.usecases.*;
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
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

    @Mock
    private CreateRestaurantUseCase createRestaurantUseCase;

    @Mock
    private RestaurantControllerMapper restaurantMapper;

    @InjectMocks
    private RestaurantController controller;

    @Mock
    private FindAllRestaurantsUseCase findAllRestaurantsUseCase;

    @Mock
    private FindRestaurantByIdUseCase findRestaurantByIdUseCase;

    @Mock
    private UpdateRestaurantUseCase updateRestaurantUseCase;

    @Mock
    private DeleteRestaurantUseCase deleteRestaurantUseCase;

    @Mock
    private RestaurantCreateRequest request;

    @Mock
    private RestaurantResponse response;

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

    @Test
    void shouldFindAllRestaurants() {
        Restaurant restaurant = new Restaurant();
        List<Restaurant> list = List.of(restaurant);

        when(findAllRestaurantsUseCase.execute()).thenReturn(list);
        when(restaurantMapper.toResponse(restaurant)).thenReturn(response);

        ResponseEntity<List<RestaurantResponse>> result = controller.findAll();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
        assertEquals(response, result.getBody().get(0));
    }

    @Test
    void shouldFindRestaurantById() {
        UUID id = UUID.randomUUID();
        Restaurant restaurant = new Restaurant();

        when(findRestaurantByIdUseCase.execute(id)).thenReturn(restaurant);
        when(restaurantMapper.toResponse(restaurant)).thenReturn(response);

        ResponseEntity<RestaurantResponse> result = controller.findById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(findRestaurantByIdUseCase).execute(id);
    }

    @Test
    void shouldUpdateRestaurant() {
        UUID id = UUID.randomUUID();
        Restaurant domain = new Restaurant();
        Restaurant updated = new Restaurant();

        when(restaurantMapper.toDomain(request)).thenReturn(domain);
        when(updateRestaurantUseCase.execute(domain)).thenReturn(updated);
        when(restaurantMapper.toResponse(updated)).thenReturn(response);

        ResponseEntity<RestaurantResponse> result = controller.update(id, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        assertEquals(id, domain.getPublicId());
        verify(updateRestaurantUseCase).execute(domain);
    }

    @Test
    void shouldDeleteRestaurant() {
        UUID id = UUID.randomUUID();

        ResponseEntity<Void> result = controller.deleteById(id);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(deleteRestaurantUseCase).execute(id);
    }
}
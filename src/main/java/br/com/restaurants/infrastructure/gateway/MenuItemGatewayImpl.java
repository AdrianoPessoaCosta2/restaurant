package br.com.restaurants.infrastructure.gateway;

import br.com.restaurants.core.entities.MenuItem;
import br.com.restaurants.core.gateway.MenuItemGateway;
import br.com.restaurants.infrastructure.exception.ResourceNotFoundException;
import br.com.restaurants.infrastructure.persistence.entity.MenuItemEntity;
import br.com.restaurants.infrastructure.persistence.mapper.MenuItemMapper;
import br.com.restaurants.infrastructure.persistence.repository.MenuItemRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class MenuItemGatewayImpl implements MenuItemGateway {

    private final MenuItemRepository repository;
    private final MenuItemMapper mapper;

    public MenuItemGatewayImpl(MenuItemRepository repository, MenuItemMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public MenuItem save(MenuItem menuItem) {
        MenuItemEntity entity = mapper.toEntity(menuItem);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public MenuItem update(MenuItem menuItem) {
        MenuItemEntity oldEntity = repository.findById(menuItem.getPublicId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu Item not found"));

        MenuItemEntity entity = mapper.toUpdateEntity(menuItem);
        entity.setId(oldEntity.getId());

        return mapper.toDomain(repository.update(entity));
    }

    @Override
    public List<MenuItem> findAllByRestaurantId(UUID restaurantId) {
        return repository.findAllByRestaurantId(restaurantId)
                .stream().map(mapper::toDomain).toList();
    }

    @Override
    public MenuItem findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException("Menu Item not found"));
    }

    @Override
    public void deleteById(UUID id) {
        if (!repository.deleteById(id)) {
            throw new ResourceNotFoundException("Menu Item not found");
        }
    }
}
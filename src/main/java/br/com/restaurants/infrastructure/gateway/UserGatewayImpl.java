package br.com.restaurants.infrastructure.gateway;

import br.com.restaurants.core.entities.Address;
import br.com.restaurants.core.entities.User;
import br.com.restaurants.core.gateway.AddressGateway;
import br.com.restaurants.core.gateway.UserGateway;
import br.com.restaurants.infrastructure.exception.BusinessException;
import br.com.restaurants.infrastructure.exception.ResourceNotFoundException;
import br.com.restaurants.infrastructure.persistence.entity.UserEntity;
import br.com.restaurants.infrastructure.persistence.mapper.UserMapper;
import br.com.restaurants.infrastructure.persistence.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserGatewayImpl implements UserGateway {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AddressGateway addressGateway;

    public UserGatewayImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            AddressGateway addressGateway
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.addressGateway = addressGateway;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUser)
                .toList();
    }

    @Override
    public User findByName(String name) {
        return userMapper.toUser(
                userRepository.findByName(name)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"))
        );
    }

    @Override
    public User findById(UUID id) {
        return userMapper.toUser(
                userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"))
        );
    }

    @Override
    public User save(User user) {
        UserEntity entity = userMapper.toUserEntity(user);

        Address address = addressGateway.save(entity.getAddress());
        entity.setAddress(address);
        entity.setAddressId(address.getId());

        return userMapper.toUser(userRepository.save(entity));
    }

    @Override
    public User update(User user) {
        UserEntity oldEntity = userRepository.findById(user.getPublicId())
                .orElseThrow(() -> new BusinessException("User not found"));

        UserEntity entity = userMapper.toUserUpdateEntity(user);
        entity.getAddress().setId(oldEntity.getId());

        UserEntity saved = userRepository.update(entity);
        Address addressSaved = addressGateway.update(entity.getAddress());
        saved.setAddress(addressSaved);

        return userMapper.toUser(saved);
    }

    @Override
    public void deleteById(UUID id) {
        if (!userRepository.deleteById(id)) {
            throw new ResourceNotFoundException("User not found");
        }
    }

    @Override
    public void updatePassword(UUID id, String password) {
        if (!userRepository.updatePassword(id, password)) {
            throw new ResourceNotFoundException("User not found");
        }
    }

    @Override
    public boolean validateLogin(String user, String password) {
          return userRepository.findByUserPassword(user, password);
    }
}
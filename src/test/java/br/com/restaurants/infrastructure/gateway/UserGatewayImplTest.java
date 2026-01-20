package br.com.restaurants.infrastructure.gateway;

import br.com.restaurants.core.entities.Address;
import br.com.restaurants.core.entities.User;
import br.com.restaurants.core.gateway.AddressGateway;
import br.com.restaurants.infrastructure.exception.BusinessException;
import br.com.restaurants.infrastructure.exception.ResourceNotFoundException;
import br.com.restaurants.infrastructure.persistence.entity.UserEntity;
import br.com.restaurants.infrastructure.persistence.mapper.UserMapper;
import br.com.restaurants.infrastructure.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserGatewayImplTest {

    @InjectMocks
    private UserGatewayImpl gateway;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AddressGateway addressGateway;

    @Test
    void shouldFindAllUsers() {
        UserEntity entity = new UserEntity();
        User user = new User();

        when(userRepository.findAll()).thenReturn(List.of(entity));
        when(userMapper.toUser(entity)).thenReturn(user);

        List<User> result = gateway.findAll();

        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
    }

    @Test
    void shouldFindUserByName() {
        String name = "John";
        UserEntity entity = new UserEntity();
        User user = new User();

        when(userRepository.findByName(name)).thenReturn(Optional.of(entity));
        when(userMapper.toUser(entity)).thenReturn(user);

        User result = gateway.findByName(name);

        assertEquals(user, result);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundByName() {
        String name = "John";
        when(userRepository.findByName(name)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> gateway.findByName(name));
    }

    @Test
    void shouldFindUserById() {
        UUID id = UUID.randomUUID();
        UserEntity entity = new UserEntity();
        User user = new User();

        when(userRepository.findById(id)).thenReturn(Optional.of(entity));
        when(userMapper.toUser(entity)).thenReturn(user);

        User result = gateway.findById(id);

        assertEquals(user, result);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundById() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> gateway.findById(id));
    }

    @Test
    void shouldSaveUser() {
        User user = new User();
        UserEntity entity = new UserEntity();
        Address address = new Address();
        address.setId(1L);
        entity.setAddress(address);

        UserEntity savedEntity = new UserEntity();
        User expectedUser = new User();

        when(userMapper.toUserEntity(user)).thenReturn(entity);
        when(addressGateway.save(address)).thenReturn(address);
        when(userRepository.save(entity)).thenReturn(savedEntity);
        when(userMapper.toUser(savedEntity)).thenReturn(expectedUser);

        User result = gateway.save(user);

        assertEquals(expectedUser, result);
        assertEquals(1L, entity.getAddressId());
        verify(addressGateway).save(address);
        verify(userRepository).save(entity);
    }

    @Test
    void shouldUpdateUser() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setPublicId(id);

        UserEntity oldEntity = new UserEntity();
        oldEntity.setId(10L);

        UserEntity newEntity = new UserEntity();
        Address address = new Address();
        newEntity.setAddress(address);

        Address updatedAddress = new Address();
        UserEntity savedEntity = new UserEntity();
        savedEntity.setAddress(updatedAddress);
        User expectedUser = new User();

        when(userRepository.findById(id)).thenReturn(Optional.of(oldEntity));
        when(userMapper.toUserUpdateEntity(user)).thenReturn(newEntity);
        when(userRepository.update(newEntity)).thenReturn(savedEntity);
        when(addressGateway.update(address)).thenReturn(updatedAddress);
        when(userMapper.toUser(savedEntity)).thenReturn(expectedUser);

        User result = gateway.update(user);

        assertEquals(expectedUser, result);
        verify(userRepository).update(newEntity);
        verify(addressGateway).update(address);
        assertEquals(10L, newEntity.getAddress().getId());
    }

    @Test
    void shouldThrowBusinessExceptionWhenUpdatingNonExistentUser() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setPublicId(id);

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> gateway.update(user));
        verify(userRepository, never()).update(any());
    }

    @Test
    void shouldDeleteUserById() {
        UUID id = UUID.randomUUID();
        when(userRepository.deleteById(id)).thenReturn(true);

        gateway.deleteById(id);

        verify(userRepository).deleteById(id);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        UUID id = UUID.randomUUID();
        when(userRepository.deleteById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> gateway.deleteById(id));
    }

    @Test
    void shouldUpdatePassword() {
        UUID id = UUID.randomUUID();
        String pass = "newPass";
        when(userRepository.updatePassword(id, pass)).thenReturn(true);

        gateway.updatePassword(id, pass);

        verify(userRepository).updatePassword(id, pass);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingPasswordForNonExistentUser() {
        UUID id = UUID.randomUUID();
        String pass = "newPass";
        when(userRepository.updatePassword(id, pass)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> gateway.updatePassword(id, pass));
    }

    @Test
    void shouldValidateLogin() {
        String user = "user";
        String pass = "pass";
        when(userRepository.findByUserPassword(user, pass)).thenReturn(true);

        boolean result = gateway.validateLogin(user, pass);

        assertTrue(result);
    }
}
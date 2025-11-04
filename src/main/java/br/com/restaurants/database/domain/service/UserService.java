package br.com.restaurants.database.domain.service;

import br.com.restaurants.core.model.User;
import br.com.restaurants.database.domain.entity.AddressEntity;
import br.com.restaurants.database.domain.entity.UserEntity;
import br.com.restaurants.database.domain.mapper.UserMapper;
import br.com.restaurants.database.domain.repository.UserRepository;
import br.com.restaurants.exception.BusinessException;
import br.com.restaurants.exception.ResourceNotFoundException;
import br.com.restaurants.login.configuration.BCryptUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final AddressService addressService;

    public UserService(UserMapper userMapper, UserRepository userRepository, AddressService addressService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.addressService = addressService;
    }

    public List<User> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toUser)
                .toList();
    }

    public User findByName(String name) {
        UserEntity userEntity = userRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toUser(userEntity);
    }

    public Optional<String> findPasswordByLogin(String login) {
        return userRepository.findPasswordByLogin(login);
    }

    public User findById(UUID id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toUser(userEntity);
    }

    public User save(User user) {
        UserEntity userEntity = userMapper.toUserEntity(user);
        AddressEntity addressEntity = addressService.save(userEntity.getAddressEntity());
        userEntity.setAddressEntity(addressEntity);
        userEntity.setAddressId(addressEntity.getId());
        userEntity.setPassword(BCryptUtil.encode(userEntity.getPassword()));
        UserEntity userSaved = userRepository.save(userEntity);
        return userMapper.toUser(userSaved);
    }

    public User update(User user) {
        UserEntity userEntityOld = userRepository.findById(user.getPublicId())
                .orElseThrow(() -> new BusinessException("User not found"));
        UserEntity userEntity = userMapper.toUserUpdateEntity(user);
        userEntity.getAddressEntity().setId(userEntityOld.getId());
        UserEntity userSaved = userRepository.update(userEntity);
        AddressEntity addressSaved = addressService.update(userEntity.getAddressEntity());
        userSaved.setAddressEntity(addressSaved);
        return userMapper.toUser(userSaved);
    }

    public void deleteById(UUID id) {
        if (!userRepository.deleteById(id)) {
            throw new ResourceNotFoundException("User not found");
        }
    }

    public void updatePassword(UUID id, String password) {
        String newPassword = BCryptUtil.encode(password);
        if (!userRepository.updatePassword(id, newPassword)) {
            throw new ResourceNotFoundException("User not found");
        }
    }
}

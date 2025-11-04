package br.com.restaurants.database.adapter;

import br.com.restaurants.core.model.User;
import br.com.restaurants.database.domain.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserPersistence {

    private UserService userService;

    public UserPersistence(UserService userService) {
        this.userService = userService;
    }

    public User save(User user){
        return userService.save(user);
    }

    public User update(User user){
        return userService.update(user);
    }

    public List<User> findAll() {
        return userService.findAll();
    }

    public User findByName(String name) {
        return userService.findByName(name);
    }

    public User findById(UUID id) {
        return userService.findById(id);
    }

    public Optional<String> findPasswordByLogin(String login) {
        return userService.findPasswordByLogin(login);
    }

    public void deleteById(UUID id) {
        userService.deleteById(id);
    }
}

package br.com.restaurants.login.adpter;

import br.com.restaurants.login.adpter.response.TokenResponse;
import br.com.restaurants.login.adpter.request.LoginRequest;
import br.com.restaurants.login.domain.service.LoginService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("login")
public class LoginController {
    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public TokenResponse login(@RequestBody LoginRequest request) {
        return loginService.authenticate(request);
    }

    @PatchMapping("/{id}")
    public void updatePassword(@PathVariable String id, @RequestBody String password) {
        loginService.updatePassword(id, password);
    }
}

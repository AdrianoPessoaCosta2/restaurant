package br.com.restaurants.login.adpter;

import br.com.restaurants.login.adpter.response.TokenResponse;
import br.com.restaurants.login.adpter.request.LoginRequest;
import br.com.restaurants.login.domain.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

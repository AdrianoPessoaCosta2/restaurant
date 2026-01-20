package br.com.restaurants.infrastructure.controller;

import br.com.restaurants.core.gateway.UserGateway;
import br.com.restaurants.infrastructure.controller.request.LoginRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
public class LoginController {

    private final UserGateway userGateway;

    public LoginController(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @PostMapping
    public ResponseEntity login(@RequestBody LoginRequest request, HttpSession session) {
        boolean valido = userGateway.validateLogin(request.login(), request.password());

        if (valido) {
            session.setAttribute("usuarioLogado", request.login());
            return ResponseEntity.ok("Logado com sucesso. Cookie de sessão criado.");
        }

        return ResponseEntity.status(401).body("Login ou senha incorretos");
    }
}

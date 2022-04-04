package ru.itmo.wp.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/1")
public class UserController {
    private final JwtService jwtService;
    private final UserService userService;
    private static final String SECRET = "1sdf6gd1fh56sd1fhg";
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET);
    private static final JWTVerifier verifier = JWT.require(algorithm).build();

    public UserController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping("users/auth")
    public User findUserByJwt(@RequestParam String jwt) {
        return jwtService.find(jwt);
    }

    @GetMapping("users")
    public List<User> findUsers() {
        return userService.findAll();
    }

    /*@PostMapping("users")
    public User register(String login, String name, String password) {
        User user = new User();
        user.setAdmin(false);
        user.setLogin(login);
        user.setName(name);
        //jwtService.create(user);
        return user;
    }*/

    @PostMapping("users")
    public User register(@RequestParam String jwt) {
    DecodedJWT decodedJwt = verifier.verify(jwt);
        User user = new User();
        user.setAdmin(false);
        user.setLogin(decodedJwt.getClaim("login").asString());
        user.setName(decodedJwt.getClaim("name").asString());
        jwtService.create(user);
        return user;
    }
}

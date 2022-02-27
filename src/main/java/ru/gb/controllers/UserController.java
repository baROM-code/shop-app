package ru.gb.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.api.security.api.AuthenticationUserGateway;
import ru.gb.api.security.api.UserGateway;
import ru.gb.api.security.dto.AuthenticationUserDto;
import ru.gb.api.security.dto.UserDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Objects;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    private final UserGateway userGateway;
    private final AuthenticationUserGateway authenticationUserGateway;

    @GetMapping("/reg")
    public String showRegForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "reg-form";
    }

    @PostMapping("/reg")
    public String saveUser(UserDto userDto) {
        userGateway.handlePost(userDto);
        return "redirect:/shop/product/all";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new AuthenticationUserDto());
        return "login-form";
    }

    @PostMapping("/login")
    public String login(AuthenticationUserDto authenticationUserDto, HttpServletResponse response) {
        ResponseEntity<HashMap<Object, Object>> login = (ResponseEntity<HashMap<Object, Object>>) authenticationUserGateway.login(authenticationUserDto);
        String token = (String) Objects.requireNonNull(login.getBody()).get("token");
        token = "Bearer_" + token;
        Cookie cookie = new Cookie("Authorization", token);
        cookie.setMaxAge(Integer.MAX_VALUE);
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return "redirect:/shop/product/all";
    }


}

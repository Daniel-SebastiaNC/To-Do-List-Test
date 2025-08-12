package dev.danielsebastian.To_Do_List.controller;

import dev.danielsebastian.To_Do_List.dto.user.TokenDto;
import dev.danielsebastian.To_Do_List.dto.user.UserLoginRequest;
import dev.danielsebastian.To_Do_List.dto.user.UserRegisterRequest;
import dev.danielsebastian.To_Do_List.dto.user.UserResponse;
import dev.danielsebastian.To_Do_List.model.User;
import dev.danielsebastian.To_Do_List.security.TokenService;
import dev.danielsebastian.To_Do_List.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody UserLoginRequest userLoginRequest){
        try {

            UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(userLoginRequest.email(), userLoginRequest.password());
            Authentication authentication = authenticationManager.authenticate(userAndPass);

            User user = (User) authentication.getPrincipal();

            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new TokenDto(token));

        } catch (BadCredentialsException exception){
            throw new RuntimeException("Usuário ou Senha invalidos");
        }

    }
}

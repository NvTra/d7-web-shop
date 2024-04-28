package com.tranv.d7shop.controller;

import com.tranv.d7shop.components.LocalizationUtils;
import com.tranv.d7shop.dtos.UserDTO;
import com.tranv.d7shop.dtos.UserLoginDTO;
import com.tranv.d7shop.models.User;
import com.tranv.d7shop.reponse.LoginRepose;
import com.tranv.d7shop.reponse.RegisterRepose;
import com.tranv.d7shop.services.IUserService;
import com.tranv.d7shop.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final LocalizationUtils localizationUtils;

    @PostMapping("/register")
    public ResponseEntity<RegisterRepose> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult bindingResult) {
        RegisterRepose registerRepose = new RegisterRepose();
        try {
            if (bindingResult.hasErrors()) {
                List<String> errorMessage = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                registerRepose.setMessage(errorMessage.toString());
                return ResponseEntity.badRequest().body(registerRepose);
            }
            if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
                registerRepose.setMessage(localizationUtils.getLocalizedMessage(
                        MessageKeys.PASSWORD_NOT_MATCH));
                return ResponseEntity.badRequest().body(registerRepose);
            }
            User user = userService.createUser(userDTO);
            registerRepose.setMessage(localizationUtils.getLocalizedMessage(
                    MessageKeys.REGISTER_SUCCESSFULLY));
            registerRepose.setUser(user);
            return ResponseEntity.ok(registerRepose);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(registerRepose);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRepose> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        try {
            String token = userService.loginUser(
                    userLoginDTO.getPhoneNumber(),
                    userLoginDTO.getPassword(),
                    userLoginDTO.getRoleId()
            );

            return ResponseEntity.ok(
                    LoginRepose.builder()
                            .message(localizationUtils.getLocalizedMessage(
                                    MessageKeys.LOGINS_SUCCESSFULLY
                            ))
                            .token(token)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    LoginRepose.builder()
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGINS_FAILED, e.getMessage()))
                            .build()
            );
        }
    }
}

package com.tranv.d7shop.controller;

import com.tranv.d7shop.components.LocalizationUtils;
import com.tranv.d7shop.dtos.UserDTO;
import com.tranv.d7shop.dtos.UserLoginDTO;
import com.tranv.d7shop.reponse.LoginRepose;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final LocalizationUtils localizationUtils;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult bindingResult) {

        try {
            if (bindingResult.hasErrors()) {
                List<String> errorMessage = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errorMessage);
            }
            if (!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body(localizationUtils.getLocalizedMessage(
                        MessageKeys.PASSWORD_NOT_MATCH
                ));
            }
            userService.createUser(userDTO);
            return ResponseEntity.ok("Successfully registered");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRepose> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        try {
            String token = userService.loginUser(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());

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

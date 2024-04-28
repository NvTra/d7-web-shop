package com.tranv.d7shop.controller;

import com.tranv.d7shop.models.Role;
import com.tranv.d7shop.services.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
public class RoleController {
    private final IRoleService roleService;

    @GetMapping("")
    public ResponseEntity<?> getAllRoles() {
        List<Role>roleList=roleService.getAllRoles();
        return ResponseEntity.ok(roleList);
    }

}

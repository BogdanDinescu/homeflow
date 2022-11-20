package com.fmi.homeflow.controller;

import com.fmi.homeflow.model.User;
import com.fmi.homeflow.model.dto.UserDto;
import com.fmi.homeflow.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

import static com.fmi.homeflow.utility.UserConstants.GET_USER_ROUTE;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
class UserController {

    private final UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addUser(@RequestBody UserDto user) {
        User createdUser = userService.addUser(user);
        return ResponseEntity.created(URI.create(GET_USER_ROUTE + createdUser.getId())).build();
    }

    /*@GetMapping("/{uuid}")
    public ResponseEntity<UserDto> getUser(@PathVariable UUID uuid) {
        return ResponseEntity.ok(userService.getUserDtoById(uuid));
    }*/
}

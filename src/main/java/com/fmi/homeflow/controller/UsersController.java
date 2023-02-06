package com.fmi.homeflow.controller;

import com.fmi.homeflow.model.dto.user.CreateUserRequest;
import com.fmi.homeflow.model.dto.user.UserDetailsDto;
import com.fmi.homeflow.service.user.UsersFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
class UsersController {

    private final UsersFacade usersFacade;

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailsDto> getUser(@PathVariable String username) {
        return ResponseEntity.ok(usersFacade.getUserByUsername(username));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.created(usersFacade.addUser(createUserRequest)).build();
    }

    @PreAuthorize("principal.username == #username")
    @PutMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailsDto> updateUser(@PathVariable String username,
                                                     @RequestBody UserDetailsDto userDetailsDto) {
        return ResponseEntity.ok(usersFacade.updateUser(username, userDetailsDto));
    }

    @PreAuthorize("principal.username == #username")
    @DeleteMapping(value = "/delete/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        usersFacade.deleteUser(username);
        return ResponseEntity.noContent().build();
    }
}

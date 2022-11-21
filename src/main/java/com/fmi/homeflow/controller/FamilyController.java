package com.fmi.homeflow.controller;

import com.fmi.homeflow.model.Family;
import com.fmi.homeflow.service.FamilyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

import static com.fmi.homeflow.utility.UserConstants.GET_USER_ROUTE;

@RestController
@RequestMapping("api/family")
@AllArgsConstructor
public class FamilyController {

    private final FamilyService familyService;

    /*@GetMapping("/{id}")
    public ResponseEntity<Family> getFamilyById(@PathVariable UUID id) {
        return ResponseEntity.ok(familyService.getFamilyById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createFamily(@RequestBody Family family) {
        Family serviceFamily = familyService.createFamily(family.getName(), family.getMembersList());
        return ResponseEntity.created(URI.create(GET_USER_ROUTE + serviceFamily.getId())).build();
    }

    @PutMapping("/add/{familyId}/{userId}")
    public ResponseEntity<Void> addToFamily(@PathVariable UUID familyId, @PathVariable UUID userId) {
        familyService.addMemberToFamily(userId, familyId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/delete/{familyId}/{userId}")
    public ResponseEntity<Void> removeFromFamily(@PathVariable UUID familyId, @PathVariable UUID userId) {
        familyService.removeMemberFromFamily(userId, familyId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFamily(@PathVariable UUID id) {
        familyService.deleteFamily(id);
        return ResponseEntity.noContent().build();
    }*/
}

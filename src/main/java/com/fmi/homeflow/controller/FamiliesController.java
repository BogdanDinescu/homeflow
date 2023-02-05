package com.fmi.homeflow.controller;

import com.fmi.homeflow.model.dto.family.FamilyDto;
import com.fmi.homeflow.model.family.FamilyEntity;
import com.fmi.homeflow.service.family.FamiliesFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/families")
@AllArgsConstructor
public class FamiliesController {

    private final FamiliesFacade familiesFacade;

    @PreAuthorize("@usersService.memberIsInFamily(principal.username, #id)")
    @GetMapping("/{id}")
    public ResponseEntity<FamilyEntity> getFamilyById(@PathVariable UUID id) {
        return ResponseEntity.ok(familiesFacade.getFamily(id));
    }

    @PostMapping
    public ResponseEntity<Void> addFamily(@RequestBody FamilyDto createFamilyRequest) {
        return ResponseEntity.created(familiesFacade.addFamily(createFamilyRequest)).build();
    }

    @PreAuthorize("@usersService.memberIsInFamily(principal.username, #familyId)")
    @PutMapping("/add/{familyId}/{username}")
    public ResponseEntity<Void> addToFamily(@PathVariable UUID familyId, @PathVariable String username) {
        familiesFacade.addMemberToFamily(username, familyId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("@usersService.memberIsInFamily(principal.username, #familyId) and @usersService.memberIsInFamily(#username, #familyId)")
    @DeleteMapping("/delete/{familyId}/{username}")
    public ResponseEntity<Void> removeFromFamily(@PathVariable UUID familyId, @PathVariable String username) {
        familiesFacade.removeMemberFromFamily(username, familyId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("@usersService.memberIsInFamily(principal.username, #id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFamily(@PathVariable UUID id) {
        familiesFacade.deleteFamily(id);
        return ResponseEntity.noContent().build();
    }
}

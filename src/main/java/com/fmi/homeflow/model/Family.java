package com.fmi.homeflow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Family {

    private UUID id;
    private String name;
    private Set<UUID> membersList;

    public void addMember(UUID user) {
        membersList.add(user);
    }

    public boolean removeMember(UUID user) {
        return membersList.remove(user);
    }

    public boolean hasMember(UUID user) {
        return membersList.contains(user);
    }
}

package com.fmi.homeflow.model.family;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fmi.homeflow.model.user.UserEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "families")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FamilyEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name_of_family")
    private String name;

    @OneToMany(mappedBy = "userFamily")
    @JsonIgnore
    private Set<UserEntity> membersList;

}

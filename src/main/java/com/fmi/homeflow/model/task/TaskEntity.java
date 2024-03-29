package com.fmi.homeflow.model.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fmi.homeflow.model.family.FamilyEntity;
import com.fmi.homeflow.model.user.UserEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "state", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private State state;

    @ManyToOne()
    @JoinColumn(name = "family_id", referencedColumnName = "id")
    @JsonIgnore
    private FamilyEntity familyEntity;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private UserEntity assignee;

}

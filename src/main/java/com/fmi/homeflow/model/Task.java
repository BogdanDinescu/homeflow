package com.fmi.homeflow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Task {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID taskId;

    @Column(name = "name")
    private String name;

    @Column(name = "state")
    @Enumerated(value = EnumType.STRING)
    private State state;

    @ManyToOne()
    @JoinColumn(name = "family_id", referencedColumnName = "id")
    @JsonIgnore
    private Family family;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "username")
    @JsonIgnore
    private User assignee;

    @Transient
    private UUID familyId;
    @Transient
    private String assigneeName;

}

package com.fmi.homeflow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tasks", schema = "homeflow_schema")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    UUID taskId;

    @Column(name = "name")
    String name;

    @Column(name = "state")
    @Enumerated(value = EnumType.STRING)
    State state;

    @Column(name = "familyId")
    UUID familyId;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    User assignee;

}

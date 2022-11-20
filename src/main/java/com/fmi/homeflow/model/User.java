package com.fmi.homeflow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "homeflow_schema")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    UUID id;

    @Column(name = "username")
    String name;

    @Column(name = "password")
    String password;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    Role role;

    @ManyToOne()
    @JoinColumn(name = "family_id", referencedColumnName = "id")
    @JsonIgnore
    Family userFamily;

    @OneToMany(mappedBy = "assignee")
    @JsonIgnore
    Set<Task> tasks;

}

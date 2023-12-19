package com.example.stc.task3.STC.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "permissions")
@Data
@NoArgsConstructor
public class Permissions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userEmail;
    private String permissionLevel;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private PermissionGroups group;

    public Permissions(String userEmail, String permissionLevel) {
        this.userEmail = userEmail;
        this.permissionLevel = permissionLevel;

    }
}

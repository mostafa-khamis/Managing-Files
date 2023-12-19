package com.example.stc.task3.STC.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "permission_groups")
@Data
@NoArgsConstructor
public class PermissionGroups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String groupName;

    public PermissionGroups(String groupName) {
        this.groupName = groupName;
    }
}

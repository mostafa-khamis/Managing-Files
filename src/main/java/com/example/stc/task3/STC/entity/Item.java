package com.example.stc.task3.STC.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type;
    private String name;
    private String path;
    @ManyToOne
    @JoinColumn(name = "permission_group_id")
    private PermissionGroups permissionGroups;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Files> files;

    public Item(String type, String name, String path) {
        this.type = type;
        this.name = name;
        this.path = path;
    }
}
package com.example.stc.task3.STC.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "binary_data", columnDefinition = "bigint")
    @Lob
    private byte[] binaryData;
    private String type;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public Files(byte[] binaryData, String type) {
        this.type = type;
        this.binaryData = binaryData;
    }
}

package com.example.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@ToString
@RequiredArgsConstructor
@Table(name = "DEVICE")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "DEVICE_NAME",columnDefinition = "CHAR(255)")
    private String name;

    public Device(String name){
        this.name = name;
    }
}

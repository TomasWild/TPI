package com.utn.argentina_programa.incident_reporting_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String businessName;
    private String cuit;
    @ElementCollection
    private List<String> hiredServices = new ArrayList<>();

    public Client(String businessName,
                  String cuit,
                  List<String> hiredServices) {
        this.businessName = businessName;
        this.cuit = cuit;
        this.hiredServices = hiredServices;
    }
}

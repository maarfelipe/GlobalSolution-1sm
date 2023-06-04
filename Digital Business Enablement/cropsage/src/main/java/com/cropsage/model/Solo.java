package com.cropsage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "T_GS_SOLO")
public class Solo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_solo", nullable = false)
    private long id;

    @Column(name = "qt_nitrogenio", nullable = false)
    private double nitrogenio;

    @Column(name = "qt_potassio", nullable = false)
    private double potassio;

    @Column(name = "qt_fosforo", nullable = false)
    private double fosforo;

    @Column(name = "qt_temperatura", nullable = false)
    private double temperatura;

    @Column(name = "qt_umidade", nullable = false)
    private double umidade;

    @Column(name = "ds_ph", nullable = false)
    private double ph;

    @Column(name = "ds_chuva", nullable = false)
    private double chuva;

    @ManyToOne()

}

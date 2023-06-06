package com.cropsage.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "Informar a % de Nitrogênio é necessário para saber o Produto.")
    @Min(0)
    @Max(100)
    private double nitrogenio;

    @Column(name = "qt_potassio", nullable = false)
    @NotNull(message = "Informar a % de Potássio é necessário para saber o Produto.")
    @Min(0)
    @Max(100)
    private double potassio;

    @Column(name = "qt_fosforo", nullable = false)
    @NotNull(message = "Informar a % de Fósforo é necessário para saber o Produto.")
    @Min(0)
    @Max(100)
    private double fosforo;

    @Column(name = "qt_temperatura", nullable = false)
    @NotNull(message = "Informar a Temperatura é necessário para saber o Produto.")
    @Max(100)
    private double temperatura;

    @Column(name = "qt_umidade", nullable = false)
    @NotNull(message = "Informar a % de Umidade é necessário para saber o Produto.")
    @Min(0)
    @Max(100)
    private double umidade;

    @Column(name = "ds_ph", nullable = false)
    @NotNull(message = "Informar o PH é necessário para saber o Produto.")
    @Min(0)
    @Max(14)
    private double ph;

    @Column(name = "ds_chuva", nullable = false)
    @NotNull(message = "Informar o mm da Chuva é necessário para saber o Produto.")
    @Min(0)
    private double chuva;

    @ManyToOne
    @JoinColumn(name = "cd_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cd_produto")
    private Produto produto;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cd_localizacao")
    private Localizacao localizacao;

}

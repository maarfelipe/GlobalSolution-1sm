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
@Entity(name = "T_GS_LOCALIZACAO")
public class Localizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_localizacao", nullable = false)
    private long id;

    @Column(name = "ds_longitude", nullable = false)
    private String longitude;

    @Column(name = "ds_latitude", nullable = false)
    private String latitude;

    @Column(name = "nm_localizacao", nullable = false)
    private String nome;

    @Column(name = "ds_cep", nullable = false)
    private String cep;


}

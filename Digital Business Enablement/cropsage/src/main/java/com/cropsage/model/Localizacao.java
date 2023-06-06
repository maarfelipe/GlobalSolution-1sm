package com.cropsage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Longitude é obrigatório.")
    private String longitude;

    @Column(name = "ds_latitude", nullable = false)
    @NotEmpty(message = "Latitude é obrigatório.")
    private String latitude;

    @Column(name = "nm_localizacao", nullable = false)
    @NotEmpty(message = "Nome é obrigatório.")
    private String nome;

    @Column(name = "ds_cep")
    private String cep;

    @ManyToOne
    @JoinColumn(name = "cd_usuario")
    private Usuario usuario;

    @OneToOne(mappedBy = "localizacao")
    @JsonIgnore
    private Solo solo;

}

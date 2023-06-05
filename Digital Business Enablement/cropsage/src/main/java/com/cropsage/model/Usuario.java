package com.cropsage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "T_GS_USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_usuario", nullable = false)
    private long id;

    @Column(name = "nm_usuario", nullable = false)
    @NotNull
    private String nome;

    @Column(name = "ds_email", nullable = false)
    @NotNull
    private String email;

    @Column(name = "ds_senha", nullable = false)
    @NotNull
    private String senha;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Solo> soloList;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Localizacao> localizacaoList;

    public EntityModel<Usuario> toEntityModel() {
        return EntityModel.of(
                this
        );
    }

}

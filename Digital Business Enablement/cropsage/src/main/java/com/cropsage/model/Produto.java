package com.cropsage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "T_GS_PRODUTO")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_produto", nullable = false)
    private long id;

    @Column(name = "nm_produto", nullable = false)
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "produto")
    private List<Solo> soloList;

}

package com.cropsage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "T_GS_USUARIO")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_usuario", nullable = false)
    private long id;

    @Column(name = "nm_usuario", nullable = false)
    @NotEmpty(message = "Nome é obrigatório.")
    private String nome;

    @Column(name = "ds_email", nullable = false, unique = true)
    @NotEmpty(message = "Email é obrigatório.")
    @Email(message = "O Email precisa ser válido")
    private String email;

    @Column(name = "ds_senha", nullable = false)
    @NotEmpty(message = "Senha é obrigatório.")
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USUARIO"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

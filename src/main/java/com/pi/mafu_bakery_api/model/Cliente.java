package com.pi.mafu_bakery_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CPF
    @Column(nullable = false, unique = true)
    private String cpf;
    @Column(nullable = false)
    private String nomeCompleto;
    @Column(nullable = false)
    private Date dataDeNascimento;
    @Column(nullable = false)
    private String genero;
    @OneToOne
    @JoinColumn(name = "credencial_id")
    @JsonIgnore
    private Credencial credencial;

}

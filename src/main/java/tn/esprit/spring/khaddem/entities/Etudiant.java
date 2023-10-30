package tn.esprit.spring.khaddem.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Etudiant  implements Serializable {

    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEtudiant;
    private String prenomE;
    private String nomE;
    @Enumerated(EnumType.STRING)
    private  Option op;

    @ManyToOne
    @JsonIgnore
    private Departement departement;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Equipe> equipes;

    @OneToMany(mappedBy = "etudiant" , cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JsonIgnore
    private List<Contrat> contrats;



}

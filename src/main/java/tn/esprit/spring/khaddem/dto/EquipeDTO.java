package tn.esprit.spring.khaddem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.spring.khaddem.entities.DetailEquipe;
import tn.esprit.spring.khaddem.entities.Niveau;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idEquipe;
    private String nomEquipe;
    private Niveau niveau;
    private List<EtudiantDTO> etudiants;
    private DetailEquipe detailEquipe;



    @Override
    public String toString() {
        return "EquipeDTO{" +
                "idEquipe=" + idEquipe +
                ", nomEquipe='" + nomEquipe + '\'' +
                ", niveau=" + niveau +
                ", etudiants=" + etudiants +
                ", detailEquipe=" + detailEquipe +
                '}';
    }
}
package tn.esprit.spring.khaddem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.spring.khaddem.entities.Option;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EtudiantDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idEtudiant;
    private String prenomE;
    private String nomE;
    private Option op;
    private Integer departementId;  // Assuming you want to include the departementId in the DTO
    private List<Integer> equipeIds;  // Assuming you want to include the ids of the associated equipes

    @Override
    public String toString() {
        return "EtudiantDTO{" +
                "idEtudiant=" + idEtudiant +
                ", prenomE='" + prenomE + '\'' +
                ", nomE='" + nomE + '\'' +
                ", op=" + op +
                ", departementId=" + departementId +
                ", equipeIds=" + equipeIds +
                '}';
    }
}

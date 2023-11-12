package tn.esprit.spring.khaddem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UniversiteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idUniversite;
    private String nomUniv;
    private List<DepartementDTO> departements;


    @Override
    public String toString() {
        return "UniversiteDTO{" +
                "idUniversite=" + idUniversite +
                ", nomUniv='" + nomUniv + '\'' +
                ", departements=" + departements +
                '}';
    }
}
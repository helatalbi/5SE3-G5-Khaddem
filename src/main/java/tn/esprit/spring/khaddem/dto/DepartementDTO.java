package tn.esprit.spring.khaddem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartementDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idDepartement;
    private String nomDepart;


    @Override
    public String toString() {
        return "DepartementDTO{" +
                "idDepartement=" + idDepartement +
                ", nomDepart='" + nomDepart + '\'' +
                '}';
    }
}

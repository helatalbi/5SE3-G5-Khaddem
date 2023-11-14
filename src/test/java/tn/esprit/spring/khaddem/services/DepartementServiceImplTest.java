package tn.esprit.spring.khaddem.services;

import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.khaddem.entities.Departement;
import tn.esprit.spring.khaddem.repositories.DepartementRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartementServiceImplTest {
    @InjectMocks
    private DepartementServiceImpl departementService;

    @Mock
    private DepartementRepository departementRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateDepartement() {
        var departement = new Departement();
        departement.setIdDepartement(1);
        departement.setNomDepart("Computer Science");

        when(departementRepository.save(departement)).thenReturn(departement);
        var result = departementService.updateDepartement(departement);

        Mockito.verify(departementRepository, Mockito.times(1)).save(departement);
        assertEquals(departement, result);
    }

    @Test
    void retrieveDepartement() {
        Integer idDepart = 1;
        var departement = new Departement();
        departement.setIdDepartement(idDepart);
        departement.setNomDepart("Mathematics");
        when(departementRepository.findById(idDepart)).thenReturn(Optional.of(departement));
        var result = departementService.retrieveDepartement(idDepart);
        Mockito.verify(departementRepository, Mockito.times(1)).findById(idDepart);
        assertEquals(departement, result);

    }
}
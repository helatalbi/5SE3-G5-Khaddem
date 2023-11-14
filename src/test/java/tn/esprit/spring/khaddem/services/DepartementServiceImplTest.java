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
        // Sample Departement ID for testing
        Integer idDepart = 1;

        // Create a sample Departement
        var departement = new Departement();
        departement.setIdDepartement(idDepart);
        departement.setNomDepart("Mathematics");

        // Mock the repository findById method
        when(departementRepository.findById(idDepart)).thenReturn(Optional.of(departement));

        // Call the service method
        var result = departementService.retrieveDepartement(idDepart);

        // Verify that the repository findById method was called once with the correct ID
        Mockito.verify(departementRepository, Mockito.times(1)).findById(idDepart);

        // Assert that the result is the same as the sample Departement
        assertEquals(departement, result);

    }
}
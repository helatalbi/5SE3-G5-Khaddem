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
import tn.esprit.spring.khaddem.entities.Universite;
import tn.esprit.spring.khaddem.repositories.DepartementRepository;
import tn.esprit.spring.khaddem.repositories.UniversiteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartementServiceImplTest {

    @InjectMocks
    private DepartementServiceImpl departementService;

    @Mock
    private DepartementRepository departementRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void retrieveAllDepartements() {
        List<Departement> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            var departement = new Departement();
            departement.setIdDepartement(i);
            departement.setNomDepart("Departement " + i);
            list.add(departement);
        }

        when(departementRepository.findAll()).thenReturn(list);

        var result = departementService.retrieveAllDepartements();

        Mockito.verify(departementRepository, Mockito.times(1)).findAll();
        assertEquals(result.size(), list.size());
    }

    @Test
    void addDepartement() {
        var departement = new Departement();
        departement.setNomDepart("Departement IT");

        when(departementRepository.save(departement)).thenReturn(departement);

        var result = departementService.addDepartement(departement);

        Mockito.verify(departementRepository, Mockito.times(1)).save(departement);
        assertEquals(departement.getIdDepartement(), result.getIdDepartement());
    }

    @Test
    void retrieveDepartementsByUniversite() {
        Integer idUniversite = 1;
        Universite universite = new Universite();
        universite.setIdUniversite(idUniversite);

        List<Departement> departements = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            var departement = new Departement();
            departement.setIdDepartement(i);
            departement.setNomDepart("Departement " + i);
            departements.add(departement);
        }

        universite.setDepartements(departements);

        when(universiteRepository.findById(idUniversite)).thenReturn(Optional.of(universite));

        var result = departementService.retrieveDepartementsByUniversite(idUniversite);

        Mockito.verify(universiteRepository, Mockito.times(1)).findById(idUniversite);
        assertEquals(result.size(), departements.size());
    }
}


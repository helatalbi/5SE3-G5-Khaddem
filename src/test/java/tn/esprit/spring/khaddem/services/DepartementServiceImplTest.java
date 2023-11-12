package tn.esprit.spring.khaddem.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.khaddem.entities.Departement;
import tn.esprit.spring.khaddem.entities.Universite;
import tn.esprit.spring.khaddem.repositories.DepartementRepository;
import tn.esprit.spring.khaddem.repositories.UniversiteRepository;
import tn.esprit.spring.khaddem.services.DepartementServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class DepartementServiceImplTest {

    @InjectMocks
    private DepartementServiceImpl departementService;

    @Mock
    private DepartementRepository departementRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    @BeforeEach
    void setUp() {
        // Initialize mock data or configuration if needed
    }

    @Test
    void retrieveAllDepartements() {
        // Create a sample list of departments for testing
        List<Departement> departments = new ArrayList<>();
        departments.add(new Departement());

        // Mock the behavior of the repository
        Mockito.when(departementRepository.findAll()).thenReturn(departments);

        // Call the service method
        List<Departement> retrievedDepartments = departementService.retrieveAllDepartements();

        // Assertions to verify the results
        Assertions.assertEquals(1, retrievedDepartments.size());
        Assertions.assertEquals(departments.get(0), retrievedDepartments.get(0));
    }

    @Test
    void addDepartement() {
        // Create a sample department for testing
        Departement department = new Departement();

        // Mock the behavior of the repository
        Mockito.when(departementRepository.save(Mockito.any())).thenReturn(department);

        // Call the service method
        Departement addedDepartment = departementService.addDepartement(department);

        // Assertions to verify the results
        Assertions.assertNotNull(addedDepartment);
        Assertions.assertEquals(department, addedDepartment);
    }

    @Test
    void retrieveDepartementsByUniversite() {
        List<Departement> departements = new ArrayList<>();
        departements.add(new Departement(1, "Dept 1", new ArrayList<>()));
        departements.add(new Departement(2, "Dept 2", new ArrayList<>()));
        Universite universite = new Universite(1,"uni1",departements);

        Mockito.when(universiteRepository.findById(universite.getIdUniversite())).thenReturn(Optional.of(universite));

        List<Departement> retrievedDepartements = departementService.retrieveDepartementsByUniversite(universite.getIdUniversite());

        Assertions.assertEquals(departements, retrievedDepartements);
    }
}

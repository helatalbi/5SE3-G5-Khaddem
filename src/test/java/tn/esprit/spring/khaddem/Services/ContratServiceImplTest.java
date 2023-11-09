package tn.esprit.spring.khaddem.Services;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.khaddem.entities.Contrat;
import tn.esprit.spring.khaddem.entities.Etudiant;
import tn.esprit.spring.khaddem.entities.Specialite;
import tn.esprit.spring.khaddem.repositories.ContratRepository;
import tn.esprit.spring.khaddem.repositories.EtudiantRepository;
import tn.esprit.spring.khaddem.services.ContratServiceImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContratServiceImplTest {

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private ContratServiceImpl contratService;

    @BeforeEach
     void setup() {
        MockitoAnnotations.initMocks(this);

    }

    @AfterEach
     void cleanup() {
        etudiantRepository.deleteAll();
        contratRepository.deleteAll();
    }

    @Test
     void testAddContrat() {
        // Given
        Contrat contrat = new Contrat();
        contrat.setMontantContrat(1000);
        contrat.setIdContrat(1);

        // Mock the behavior of contratRepository.save
        when(contratRepository.save(contrat)).thenReturn(contrat);

        // When
        Contrat addedContrat = contratService.addContrat(contrat);

        // Then
        assertNotNull(addedContrat);
        assertEquals(contrat, addedContrat);
        verify(contratRepository, times(1)).save(contrat);
        System.out.println("Test addContrat passed!");
    }

    @Test
     void testUpdateContrat() {
        Contrat contrat = new Contrat();
        contrat.setMontantContrat(1500);
        contrat.setIdContrat(1);
        when(contratRepository.save(contrat)).thenReturn(contrat);
        Contrat contrat1 = contratService.addContrat(contrat);

        // Étape 2 : Modifier l'étudiant 1
        contrat1.setMontantContrat(1200);
        Contrat modifcontrat1 = contratService.updateContrat(contrat1);

        // Assertion to verify that the montantContrat has been modified
        assertEquals(1200, modifcontrat1.getMontantContrat());

        System.err.println("Étape 2 : Modification de contrat 1");
        System.err.println(modifcontrat1);
        System.out.println("Test updateContrat passed!");

    }


    @Test
     void testRetrieveContrat() {
        // Given
        Contrat contrat = new Contrat();
        contrat.setMontantContrat(1000);
        contrat.setIdContrat(1);

        // When
        when(contratRepository.save(contrat)).thenReturn(contrat);
        Contrat addedContrat = contratService.addContrat(contrat);

        // When
        when(contratRepository.findById(addedContrat.getIdContrat())).thenReturn(java.util.Optional.of(addedContrat));
        Contrat retrievedContrat = contratService.retrieveContrat(addedContrat.getIdContrat());

        // Then
        assertNotNull(retrievedContrat);
        assertEquals(addedContrat, retrievedContrat);
        verify(contratRepository, times(1)).findById(addedContrat.getIdContrat());
        System.out.println(addedContrat);
        System.out.println("Test retrieveContrat passed!");

    }

    @Test
     void testRemoveContrat() {
        // Given
        Contrat contrat = new Contrat();
        contrat.setMontantContrat(1000);
        contrat.setIdContrat(1);

        // When
        when(contratRepository.save(contrat)).thenReturn(contrat);
        Contrat addedContrat = contratService.addContrat(contrat);

        // When
        contratService.removeContrat(addedContrat.getIdContrat());

        // Then
        verify(contratRepository, times(1)).deleteById(addedContrat.getIdContrat());
        System.out.println("Test removeContrat passed!");

    }

    @Test
     void testAddAndAffectContratToEtudiant() {
        // Given
        Contrat contrat = new Contrat();
        contrat.setMontantContrat(1000);
        contrat.setIdContrat(1);

        String nomE = "John";
        String prenomE = "Doe";
        Etudiant etudiant = Mockito.mock(Etudiant.class);
        when(etudiantRepository.findByNomEAndPrenomE(nomE, prenomE)).thenReturn(etudiant);
        when(etudiant.getContrats()).thenReturn(new ArrayList<>());
        when(contratRepository.save(contrat)).thenReturn(contrat);

        // When
        Contrat addedContrat = contratService.addAndAffectContratToEtudiant(contrat, nomE, prenomE);

        // Then
        assertNotNull(addedContrat);
        assertEquals(etudiant, addedContrat.getEtudiant());
        verify(etudiantRepository, times(1)).findByNomEAndPrenomE(nomE, prenomE);
        verify(contratRepository, times(1)).save(contrat);
        System.out.println("Test addAndAffectContratToEtudiant passed!");

    }

    @Test
     void testRetrieveAndUpdateStatusContrat() {
        // Arrange
        List<Contrat> contrats = new ArrayList<>();
        Contrat contrat1 = new Contrat();
        contrat1.setIdContrat(1);
        contrat1.setDateFinContrat(new Date()); // Adjust based on your test scenario
        contrats.add(contrat1);

        when(contratRepository.findAll()).thenReturn(contrats);

        // Act
        contratService.retrieveAndUpdateStatusContrat();

        // Assert
        // Verify that the save method is called for each contract with the expected changes
        verify(contratRepository).save(contrat1);
        // Add more verification based on your business logic
        System.out.println("Test retrieveAndUpdateStatusContrat Passed!");
    }
    @Test
    void testGetChiffreAffaireEntreDeuxDates() throws ParseException {
        // Arrange
        String startDateString = "2022-01-01";
        String startFinString = "2023-12-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(startDateString);
        Date endDate = sdf.parse(startFinString);

        Contrat contrat = new Contrat(1, startDate, endDate, Specialite.IA, true, 1000, new Etudiant());
        Contrat contrat1 = new Contrat(2, startDate, endDate, Specialite.CLOUD, true, 1500, new Etudiant());
        Contrat contrat2 = new Contrat(3, startDate, endDate, Specialite.RESEAU, true, 2000, new Etudiant());
        Contrat contrat3 = new Contrat(4, startDate, endDate, Specialite.SECURITE, true, 2500, new Etudiant());

        List<Contrat> contrats = new ArrayList<>();
        contrats.add(contrat);
        contrats.add(contrat1);
        contrats.add(contrat2);
        contrats.add(contrat3);

        when(contratRepository.findAll()).thenReturn(contrats);

        // Act
        double chiffreAffaire = contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate);

        assertEquals(77933.3359375, chiffreAffaire, 0.001);
        System.out.println("Test getChiffreAffaireEntreDeuxDates Passed!");
    }

}

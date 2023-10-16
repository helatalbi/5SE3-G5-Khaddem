package tn.esprit.spring.khaddem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.khaddem.entities.*;
import tn.esprit.spring.khaddem.repositories.ContratRepository;
import tn.esprit.spring.khaddem.repositories.DepartementRepository;
import tn.esprit.spring.khaddem.repositories.EquipeRepository;
import tn.esprit.spring.khaddem.repositories.EtudiantRepository;
import tn.esprit.spring.khaddem.services.EtudiantServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class) // Assurez-vous que vous utilisez l'extension Mockito
@SpringBootTest(classes = KhaddemApplication.class)
public class EtudiantServiceTest {

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @Mock
    private EtudiantRepository etudiantRepository;
    @Mock
    private DepartementRepository departementRepository;
    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EquipeRepository equipeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() {
        reset(etudiantRepository);
        reset(etudiantRepository, departementRepository);
        reset(etudiantRepository, contratRepository, equipeRepository);


    }

    @Test
    public void testRetrieveAllEtudiants() {
        // Créez des étudiants factices pour simuler la réponse de votre repository
        Etudiant etudiant1 = new Etudiant();
        etudiant1.setIdEtudiant(1);
        etudiant1.setPrenomE("John");
        etudiant1.setNomE("Doe");

        Etudiant etudiant2 = new Etudiant();
        etudiant2.setIdEtudiant(2);
        etudiant2.setPrenomE("Jane");
        etudiant2.setNomE("Smith");

        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(etudiant1);
        etudiants.add(etudiant2);

        // Utilisez Mockito pour simuler le comportement de etudiantRepository.findAll
        when(etudiantRepository.findAll()).thenReturn(etudiants);

        // Appelez la méthode sous test
        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        // Vérifiez que la méthode a été appelée une fois
        verify(etudiantRepository, times(1)).findAll();

        // Vérifiez que le résultat correspond aux données simulées
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getIdEtudiant());
        assertEquals("John", result.get(0).getPrenomE());
        assertEquals("Doe", result.get(0).getNomE());
        assertEquals(2, result.get(1).getIdEtudiant());
        assertEquals("Jane", result.get(1).getPrenomE());
        assertEquals("Smith", result.get(1).getNomE());
    }

    @Test
    public void testAddEtudiant() {
        // Créez un étudiant factice à ajouter
        Etudiant etudiantToAdd = new Etudiant();
        etudiantToAdd.setIdEtudiant(1);
        etudiantToAdd.setPrenomE("John");
        etudiantToAdd.setNomE("Doe");

        // Configurez le comportement simulé du repository
        when(etudiantRepository.save(etudiantToAdd)).thenReturn(etudiantToAdd);

        // Appelez la méthode sous test
        Etudiant result = etudiantService.addEtudiant(etudiantToAdd);

        // Vérifiez que la méthode save a été appelée une fois avec l'étudiant à ajouter
        verify(etudiantRepository, times(1)).save(etudiantToAdd);

        // Vérifiez que l'étudiant résultant correspond à l'étudiant simulé
        assertEquals(1, result.getIdEtudiant());
        assertEquals("John", result.getPrenomE());
        assertEquals("Doe", result.getNomE());
    }
    @Test
    public void testUpdateEtudiant() {
        // Créez un étudiant factice à mettre à jour
        Etudiant etudiantToUpdate = new Etudiant();
        etudiantToUpdate.setIdEtudiant(1);
        etudiantToUpdate.setPrenomE("John");
        etudiantToUpdate.setNomE("Doe");

        // Configurez le comportement simulé du repository pour la mise à jour
        when(etudiantRepository.save(etudiantToUpdate)).thenReturn(etudiantToUpdate);

        // Appelez la méthode sous test
        Etudiant result = etudiantService.updateEtudiant(etudiantToUpdate);

        // Vérifiez que la méthode save a été appelée une fois avec l'étudiant à mettre à jour
        verify(etudiantRepository, times(1)).save(etudiantToUpdate);

        // Vérifiez que l'étudiant résultant correspond à l'étudiant simulé
        assertEquals(1, result.getIdEtudiant());
        assertEquals("John", result.getPrenomE());
        assertEquals("Doe", result.getNomE());
    }
    @Test
    public void testRetrieveEtudiant() {
        // Créez un étudiant factice à récupérer
        Etudiant etudiantToRetrieve = new Etudiant();
        etudiantToRetrieve.setIdEtudiant(1);
        etudiantToRetrieve.setPrenomE("John");
        etudiantToRetrieve.setNomE("Doe");

        // Configurez le comportement simulé du repository pour la récupération
        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiantToRetrieve));

        // Appelez la méthode sous test pour récupérer l'étudiant
        Etudiant result = etudiantService.retrieveEtudiant(1);

        // Vérifiez que la méthode findById a été appelée une fois avec l'ID 1
        verify(etudiantRepository, times(1)).findById(1);

        // Vérifiez que l'étudiant résultant correspond à l'étudiant simulé
        assertEquals(1, result.getIdEtudiant());
        assertEquals("John", result.getPrenomE());
        assertEquals("Doe", result.getNomE());
    }
    @Test
    public void testRemoveEtudiant() {
        // ID de l'étudiant à supprimer
        int etudiantId = 1;

        // Appelez la méthode sous test pour supprimer l'étudiant
        etudiantService.removeEtudiant(etudiantId);

        // Vérifiez que la méthode deleteById a été appelée une fois avec l'ID de l'étudiant
        verify(etudiantRepository, times(1)).deleteById(etudiantId);
    }
    @Test
    public void testAssignEtudiantToDepartement() {

    }
    @Test
    public void testFindByDepartementIdDepartement() {
        // ID du département factice
        int departementId = 1;

        // Créez une liste d'étudiants factices pour le département
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(new Etudiant());
        etudiants.add(new Etudiant());

        // Configurez le mock pour renvoyer la liste d'étudiants factices en fonction de l'ID du département
        when(etudiantRepository.findByDepartementIdDepartement(departementId)).thenReturn(etudiants);

        // Appelez la méthode sous test pour récupérer les étudiants par ID de département
        List<Etudiant> result = etudiantService.findByDepartementIdDepartement(departementId);

        // Vérifiez que le résultat correspond à la liste d'étudiants factices
        assertEquals(etudiants, result);
    }
    @Test
    public void testFindByEquipesNiveau() {
        // Niveau factice
        Niveau niveau = Niveau.JUNIOR;

        // Créez une liste d'étudiants factices pour le niveau
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(new Etudiant());
        etudiants.add(new Etudiant());

        // Configurez le mock pour renvoyer la liste d'étudiants factices en fonction du niveau
        when(etudiantRepository.findByEquipesNiveau(niveau)).thenReturn(etudiants);

        // Appelez la méthode sous test pour récupérer les étudiants par niveau
        List<Etudiant> result = etudiantService.findByEquipesNiveau(niveau);

        // Vérifiez que le résultat correspond à la liste d'étudiants factices
        assertEquals(etudiants, result);
    }
    @Test
    public void testRetrieveEtudiantsByContratSpecialite() {
        // Spécialité factice
        Specialite specialite = Specialite.IA;

        // Créez une liste d'étudiants factices pour la spécialité
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(new Etudiant());
        etudiants.add(new Etudiant());

        // Configurez le mock pour renvoyer la liste d'étudiants factices en fonction de la spécialité
        when(etudiantRepository.retrieveEtudiantsByContratSpecialite(specialite)).thenReturn(etudiants);

        // Appelez la méthode sous test pour récupérer les étudiants par spécialité
        List<Etudiant> result = etudiantService.retrieveEtudiantsByContratSpecialite(specialite);

        // Vérifiez que le résultat correspond à la liste d'étudiants factices
        assertEquals(etudiants, result);
    }
    @Test
    public void testRetrieveEtudiantsByContratSpecialiteSQL() {
        // Spécialité factice
        String specialite = "IA"; // Remarque : nous utilisons une chaîne pour représenter la spécialité, car c'est une requête SQL native.

        // Créez une liste d'étudiants factices pour la spécialité
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(new Etudiant());
        etudiants.add(new Etudiant());

        // Configurez le mock pour renvoyer la liste d'étudiants factices en fonction de la spécialité
        when(etudiantRepository.retrieveEtudiantsByContratSpecialiteSQL(specialite)).thenReturn(etudiants);

        // Appelez la méthode sous test pour récupérer les étudiants par spécialité
        List<Etudiant> result = etudiantService.retrieveEtudiantsByContratSpecialiteSQL(specialite);

        // Vérifiez que le résultat correspond à la liste d'étudiants factices
        assertEquals(etudiants, result);
    }
    @Test
    public void testAddAndAssignEtudiantToEquipeAndContract() {
        // Créez des entités factices
        Etudiant etudiant = new Etudiant();
        Contrat contrat = new Contrat();
        Equipe equipe = new Equipe();

        // Configurez le mock pour retourner les entités factices lors de l'appel à findById
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);
        when(contratRepository.findById(1)).thenReturn(java.util.Optional.of(contrat));
        when(equipeRepository.findById(1)).thenReturn(java.util.Optional.of(equipe));

        // Appelez la méthode sous test
        Etudiant result = etudiantService.addAndAssignEtudiantToEquipeAndContract(etudiant, 1, 1);

        // Vérifiez que l'entité retournée correspond à l'entité factice
        assertEquals(etudiant, result);
        // Vérifiez que le contrat est associé à l'étudiant
        assertEquals(etudiant, contrat.getEtudiant());
        // Vérifiez que l'équipe est associée à l'étudiant
        assertEquals(1, etudiant.getEquipes().size());
    }
    @Test
    public void testGetEtudiantsByDepartement() {

    }

}

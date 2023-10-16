import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.khaddem.KhaddemApplication;
import tn.esprit.spring.khaddem.entities.Universite;
import tn.esprit.spring.khaddem.repositories.UniversiteRepository;
import tn.esprit.spring.khaddem.services.UniversiteServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest (classes = KhaddemApplication.class)
public class UniversiteServiceImplTest {

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    @Mock
    private UniversiteRepository universiteRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUniversite() {
        // Mock data
        Universite universiteToSave = new Universite();
        universiteToSave.setIdUniversite(1);
        universiteToSave.setNomUniv("Test University");

        when(universiteRepository.save(Mockito.any(Universite.class))).thenReturn(universiteToSave);

        // Call the method under test
        Universite addedUniversite = universiteService.addUniversite(universiteToSave);

        // Assertions
        assertEquals(1, addedUniversite.getIdUniversite());
        assertEquals("Test University", addedUniversite.getNomUniv());
    }

    @Test
    public void testRetrieveAllUniversites() {
        // Mock data
        Universite universite1 = new Universite();
        universite1.setIdUniversite(1);
        universite1.setNomUniv("University 1");

        Universite universite2 = new Universite();
        universite2.setIdUniversite(2);
        universite2.setNomUniv("University 2");

        List<Universite> universiteList = new ArrayList<>();
        universiteList.add(universite1);
        universiteList.add(universite2);

        when(universiteRepository.findAll()).thenReturn(universiteList);

        // Call the method under test
        List<Universite> retrievedUniversites = universiteService.retrieveAllUniversites();

        // Assertions
        assertEquals(2, retrievedUniversites.size());
        assertEquals(1, retrievedUniversites.get(0).getIdUniversite());
        assertEquals("University 1", retrievedUniversites.get(0).getNomUniv());
        assertEquals(2, retrievedUniversites.get(1).getIdUniversite());
        assertEquals("University 2", retrievedUniversites.get(1).getNomUniv());
    }


}

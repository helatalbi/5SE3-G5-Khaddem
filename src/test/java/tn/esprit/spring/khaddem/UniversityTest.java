package tn.esprit.spring.khaddem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.khaddem.entities.Departement;
import tn.esprit.spring.khaddem.entities.Universite;
import tn.esprit.spring.khaddem.repositories.DepartementRepository;
import tn.esprit.spring.khaddem.repositories.UniversiteRepository;
import tn.esprit.spring.khaddem.services.UniversiteServiceImpl;

import javax.transaction.Transactional;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

     @ExtendWith(MockitoExtension.class)
    public class UniversityTest {

        @InjectMocks
        private UniversiteServiceImpl universiteService;

        @Mock
        private UniversiteRepository universiteRepository;
         @Mock

         private DepartementRepository departementRepository;


         @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }


        // Test for the add
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

            List<Universite> retrievedUniversites = universiteService.retrieveAllUniversites();

            // Assertions
            assertEquals(2, retrievedUniversites.size());
            assertEquals(1, retrievedUniversites.get(0).getIdUniversite());
            assertEquals("University 1", retrievedUniversites.get(0).getNomUniv());
            assertEquals(2, retrievedUniversites.get(1).getIdUniversite());
            assertEquals("University 2", retrievedUniversites.get(1).getNomUniv());
        }


         @Test
         public void testUpdateUniversite() {

             Universite university = new Universite();
             university.setIdUniversite(1);  // Set a sample ID
             university.setNomUniv("Test University");
             Mockito.when(universiteRepository.save(Mockito.any(Universite.class))).thenReturn(university);
             Universite updatedUniversity = universiteService.updateUniversite(university);
             assertSame(university, updatedUniversity);
             Mockito.verify(universiteRepository, Mockito.times(1)).save(Mockito.any(Universite.class));
         }

         @Test
         public void testRetrieveUniversite() {
             Universite university = new Universite();
             university.setIdUniversite(1);  // Set a sample ID
             Mockito.when(universiteRepository.findById(1)).thenReturn(Optional.of(university));
             Universite retrievedUniversity = universiteService.retrieveUniversite(1);
             assertNotNull(retrievedUniversity);
             assertEquals(1, retrievedUniversity.getIdUniversite());
         }


//             @Test
//             public void assignUniversiteToDepartement(Integer universiteId, Integer departementId) {
//             Universite universite = universiteRepository.findById(universiteId).orElse(null);
//             Departement departement = departementRepository.findById(departementId).orElse(null);
//
//             if (universite != null && departement != null) {
//                 List<Departement> departements = universite.getDepartements();
//
//                 if (departements == null) {
//                     departements = new ArrayList<>();
//                     universite.setDepartements(departements);
//                 }
//
//                 departements.add(departement);
//                 universiteRepository.save(universite);
//             }
//         }

     }



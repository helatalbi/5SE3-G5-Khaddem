package tn.esprit.spring.khaddem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.spring.khaddem.entities.Departement;
import tn.esprit.spring.khaddem.entities.Universite;
import tn.esprit.spring.khaddem.repositories.DepartementRepository;
import tn.esprit.spring.khaddem.repositories.UniversiteRepository;
import tn.esprit.spring.khaddem.services.UniversiteServiceImpl;

import javax.transaction.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
    public class UniversiteServiceImplTestdynamique {

        @Autowired
        private UniversiteServiceImpl universiteService;

        @Autowired
        private UniversiteRepository universiteRepository;

        @Autowired
        private DepartementRepository departementRepository;

        @Test
        public void testAddUniversite() {
            Universite universite = new Universite();
            universite.setNomUniv("Test University");

            Universite addedUniversite = universiteService.addUniversite(universite);

            assertNotNull(addedUniversite);
            assertNotNull(addedUniversite.getIdUniversite());
        }

        @Test
        public void testUpdateUniversite() {
            // Add test data to the database if needed.

            Universite universite = new Universite();
            universite.setNomUniv("Test University");
            universiteRepository.save(universite);

            universite.setNomUniv("Updated University");
            Universite updatedUniversite = universiteService.updateUniversite(universite);

            assertNotNull(updatedUniversite);
            assertEquals("Updated University", updatedUniversite.getNomUniv());
        }





        @Test
        public void testRetrieveAllUniversites() {
            // Add test data to the database if needed.

            List<Universite> universites = universiteService.retrieveAllUniversites();

            assertNotNull(universites);
            assertFalse(universites.isEmpty());
        }



        @Test
        public void testRetrieveUniversite() {
            // Add test data to the database if needed.

            Universite universite = new Universite();
            universite.setNomUniv("Test University");
            universiteRepository.save(universite);

            Integer idUniversite = universite.getIdUniversite();

            Universite retrievedUniversite = universiteService.retrieveUniversite(idUniversite);

            assertNotNull(retrievedUniversite);
            assertEquals(idUniversite, retrievedUniversite.getIdUniversite());
        }

//        @Test
//        @Transactional
//        public void testAssignUniversiteToDepartement() {
//            // Add test data to the database if needed.
//
//            Universite universite = new Universite();
//            universite.setNomUniv("Test University");
//            universiteRepository.save(universite);
//
//            Departement departement = new Departement();
//            departement.setNomDepart("Test Department");
//            departementRepository.save(departement);
//
//            universiteService.assignUniversiteToDepartement(universite.getIdUniversite(), departement.getIdDepartement());
//
//            Universite fetchedUniversite = universiteRepository.findById(universite.getIdUniversite()).get();
//
//            assertNotNull(fetchedUniversite);
//            assertEquals(1, fetchedUniversite.getDepartements().size());
//        }

    }
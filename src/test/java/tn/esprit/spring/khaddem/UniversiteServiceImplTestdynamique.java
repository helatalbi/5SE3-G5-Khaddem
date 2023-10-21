package tn.esprit.spring.khaddem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.khaddem.entities.Departement;
import tn.esprit.spring.khaddem.entities.Universite;
import tn.esprit.spring.khaddem.repositories.DepartementRepository;
import tn.esprit.spring.khaddem.repositories.UniversiteRepository;
import tn.esprit.spring.khaddem.services.UniversiteServiceImpl;

import javax.transaction.Transactional;

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
    public void testUpdateUniversite() {

        Universite universite = new Universite();
        universite.setNomUniv("Université de Test");

        Universite savedUniversite = universiteService.addUniversite(universite);

        savedUniversite.setNomUniv("Nouveau Nom");
        Universite updatedUniversite = universiteService.updateUniversite(savedUniversite);

        Universite fetchedUniversite = universiteRepository.findById(updatedUniversite.getIdUniversite()).get();

        assertNotNull(updatedUniversite);
        assertEquals("Nouveau Nom", updatedUniversite.getNomUniv());
        assertNotNull(fetchedUniversite);
        assertEquals("Nouveau Nom", fetchedUniversite.getNomUniv());
    }


}
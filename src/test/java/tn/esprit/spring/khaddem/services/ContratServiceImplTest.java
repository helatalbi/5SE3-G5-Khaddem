package tn.esprit.spring.khaddem.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.khaddem.entities.Contrat;
import tn.esprit.spring.khaddem.repositories.ContratRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ContratServiceImplTest {

    @Mock
    private ContratRepository contratRepository;
    @InjectMocks
    private ContratServiceImpl contratService;

    @Test
    void retrieveAndUpdateStatusContrat() {

        List<Contrat> contrats = new ArrayList<>();
        Contrat contrat1 = new Contrat();
        contrat1.setIdContrat(1);
        contrat1.setDateFinContrat(new Date());
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
}

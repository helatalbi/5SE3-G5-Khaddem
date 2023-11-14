package tn.esprit.spring.khaddem.services;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.khaddem.entities.Contrat;
import tn.esprit.spring.khaddem.repositories.ContratRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContratServiceImplTest {

    @InjectMocks
    private ContratServiceImpl contratService;

    @Mock
    private ContratRepository contratRepository;

    @Test
    void retrieveAndUpdateStatusContrat() {
        List<Contrat> contrats = new ArrayList<>();
        Contrat contrat1 = new Contrat();
        contrat1.setIdContrat(1);
        contrat1.setDateFinContrat(new Date());
        contrats.add(contrat1);

        Contrat contrat2 = new Contrat();
        contrat2.setIdContrat(2);
        contrat2.setDateFinContrat(new Date());
        contrats.add(contrat2);

        when(contratRepository.findAll()).thenReturn(contrats);
        contratService.retrieveAndUpdateStatusContrat();
        verify(contratRepository, times(2)).save(any(Contrat.class));
    }
}

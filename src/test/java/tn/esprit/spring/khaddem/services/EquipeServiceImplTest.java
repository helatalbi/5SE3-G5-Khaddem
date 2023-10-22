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
import tn.esprit.spring.khaddem.entities.Equipe;
import tn.esprit.spring.khaddem.entities.Niveau;
import tn.esprit.spring.khaddem.repositories.EquipeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EquipeServiceImplTest {
    @InjectMocks
    private EquipeServiceImpl equipeService;

    @Mock
    private EquipeRepository equipeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void retrieveAllEquipes(){
        List<Equipe> list = new ArrayList<>();
        for (int i=0; i<10; i++)
        {
            var equipe = Equipe.builder()
                    .idEquipe(i)
                    .niveau(Niveau.EXPERT)
                    .nomEquipe("Equipe IT")
                    .build();
            list.add(equipe);
        }
        when(equipeRepository.findAll()).thenReturn(list);
        var result = equipeService.retrieveAllEquipes();

        Mockito.verify(equipeRepository, Mockito.times(1)).findAll();
        assertEquals(result.size(), list.size());
    }
    @Test
    void addEquipe(){
        var equipe = Equipe.builder()
                .niveau(Niveau.EXPERT)
                .nomEquipe("Equipe IT")
                .build();
        when(equipeRepository.save(equipe)).thenReturn(equipe);
        var result = equipeService.addEquipe(equipe);
        Mockito.verify(equipeRepository, Mockito.times(1)).save(equipe);
        assertEquals(equipe.getIdEquipe(), result.getIdEquipe());

    }

    @Test
    void updateEquipe(){
        var equipe = Equipe.builder()
                .niveau(Niveau.EXPERT)
                .nomEquipe("Equipe IT")
                .build();
        when(equipeRepository.save(equipe)).thenReturn(equipe);
        equipeService.addEquipe(equipe);
        equipe.setNiveau(Niveau.SENIOR);
        var result = equipeService.updateEquipe(equipe);
        Mockito.verify(equipeRepository, Mockito.times(2)).save(equipe);
        assertEquals(equipe.getNiveau(), result.getNiveau());

    }
    @Test
    void retrieveEquipe(){
        var equipe = Equipe.builder()
                .idEquipe(22)
                .niveau(Niveau.EXPERT)
                .nomEquipe("Equipe IT")
                .build();
        when(equipeRepository.findById(equipe.getIdEquipe())).thenReturn(Optional.of(equipe));
        var result = equipeService.retrieveEquipe(equipe.getIdEquipe());

        Mockito.verify(equipeRepository, Mockito.times(1)).findById(equipe.getIdEquipe());
        assertEquals(equipe.getIdEquipe(), result.getIdEquipe());
    }

}

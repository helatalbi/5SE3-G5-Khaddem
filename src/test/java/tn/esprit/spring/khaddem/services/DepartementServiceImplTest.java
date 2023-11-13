package tn.esprit.spring.khaddem.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tn.esprit.spring.khaddem.entities.Departement;
import tn.esprit.spring.khaddem.repositories.DepartementRepository;
import tn.esprit.spring.khaddem.services.DepartementServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class DepartementServiceImplTest {

    @InjectMocks
    private DepartementServiceImpl departementService;

    @Mock
    private DepartementRepository departementRepository;

    @MockBean
    private ObjectMapper objectMapper; // Spring will inject a mock ObjectMapper

    @Test
    void updateDepartement() throws Exception {
        // Arrange
        Departement updatedDepartement = new Departement(1, "Updated Dept", null);

        // Mocking the objectMapper to handle serialization/deserialization
        when(objectMapper.writeValueAsString(updatedDepartement)).thenReturn("jsonString");
        when(objectMapper.readValue("jsonString", Departement.class)).thenReturn(updatedDepartement);

        when(departementRepository.save(updatedDepartement)).thenReturn(updatedDepartement);

        // Act
        Departement result = departementService.updateDepartement(updatedDepartement);

        // Assert
        assertEquals(updatedDepartement, result);
        verify(departementRepository, times(1)).save(updatedDepartement);
    }

    @Test
    void retrieveDepartement() {
        // Arrange
        int departementId = 1;
        Departement departement = new Departement(departementId, "Dept 1", null);
        when(departementRepository.findById(departementId)).thenReturn(Optional.of(departement));

        // Act
        Departement result = departementService.retrieveDepartement(departementId);

        // Assert
        assertEquals(departement, result);
        verify(departementRepository, times(1)).findById(departementId);
    }
}


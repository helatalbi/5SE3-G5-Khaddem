package tn.esprit.spring.khaddem.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.khaddem.dto.EquipeDTO;
import tn.esprit.spring.khaddem.entities.Equipe;
import tn.esprit.spring.khaddem.services.IEquipeService;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/equipe")
@CrossOrigin(origins = "*")
public class EquipeRestController {
    @Autowired

    IEquipeService equipeService;
    // http://localhost:8089/Kaddem/equipe/retrieve-all-equipes
    @GetMapping("/retrieve-all-equipes")
    @ResponseBody
    public List<Equipe> getEquipes() {
        return equipeService.retrieveAllEquipes();
    }



    // http://localhost:8089/Kaddem/equipe/retrieve-equipe/8
    @GetMapping("/retrieve-equipe/{equipe-id}")
    @ResponseBody
    public Equipe retrieveEquipe(@PathVariable("equipe-id") Integer equipeId) {
        return equipeService.retrieveEquipe(equipeId);
    }

    // http://localhost:8089/Kaddem/equipe/add-equipe
    /* cette méthode permet d'ajouter une équipe avec son détail*/
    @PostMapping("/add-equipe")
    @ResponseBody
    public EquipeDTO addEquipe(@RequestBody EquipeDTO equipeDTO) {
        // Convert EquipeDTO to Equipe entity before saving to the database
        Equipe savedEquipe = equipeService.addEquipe(convertDTOtoEntity(equipeDTO));

        // Convert the saved Equipe entity back to EquipeDTO for response
        return convertEntityToDTO(savedEquipe);
    }


    // http://localhost:8089/Kaddem/equipe/update-equipe
    @PutMapping("/update-equipe")
    @ResponseBody
    public EquipeDTO updateEquipe(@RequestBody EquipeDTO equipeDTO) {
        // Convert EquipeDTO to Equipe entity if needed before updating in the database
        Equipe updatedEquipe = equipeService.updateEquipe(convertDTOtoEntity(equipeDTO));

        // Convert the updated Equipe entity back to EquipeDTO for response
        return convertEntityToDTO(updatedEquipe);
    }



    // @Scheduled(cron="0 0 13 * * *")
    @Scheduled(cron="* * 13 * * *")
    @PutMapping("/faireEvoluerEquipes")
    public void faireEvoluerEquipes() {
        equipeService.evoluerEquipes() ;
    }
    private Equipe convertDTOtoEntity(EquipeDTO equipeDTO) {
        Equipe equipe = new Equipe();
        // Assuming the ID is not set for a new entity
        equipe.setIdEquipe(null);
        equipe.setNomEquipe(equipeDTO.getNomEquipe());
        equipe.setNiveau(equipeDTO.getNiveau());
        // Set other properties as needed
        return equipe;
    }

    private EquipeDTO convertEntityToDTO(Equipe equipe) {
        EquipeDTO equipeDTO = new EquipeDTO();
        equipeDTO.setIdEquipe(equipe.getIdEquipe());
        equipeDTO.setNomEquipe(equipe.getNomEquipe());
        equipeDTO.setNiveau(equipe.getNiveau());
        // Set other properties as needed
        return equipeDTO;
    }

}

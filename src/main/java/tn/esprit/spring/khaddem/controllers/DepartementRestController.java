package tn.esprit.spring.khaddem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.khaddem.dto.DepartementDTO;
import tn.esprit.spring.khaddem.entities.Contrat;
import tn.esprit.spring.khaddem.entities.Departement;
import tn.esprit.spring.khaddem.services.IDepartementService;

import java.util.List;

@RestController
@RequestMapping("/departement")
@CrossOrigin(origins = "*")
public class DepartementRestController {
    @Autowired

    IDepartementService departementService;
    // http://localhost:8089/Kaddem/departement/retrieve-all-departements
    @GetMapping("/retrieve-all-departements")
    @ResponseBody
    public List<Departement> getDepartements() {
        return departementService.retrieveAllDepartements();
    }


    // http://localhost:8089/Kaddem/departement/retrieve-departement/8
    @GetMapping("/retrieve-departement/{departement-id}")
    @ResponseBody
    public Departement retrieveDepartement(@PathVariable("departement-id") Integer departementId) {
        return departementService.retrieveDepartement(departementId);
    }

    // http://localhost:8089/Kaddem/departement/add-departement
    @PostMapping("/add-departement")
    @ResponseBody
    public DepartementDTO addDepartement(@RequestBody DepartementDTO departementDTO) {
        // Convert DepartementDTO to Departement entity before saving to the database
        Departement savedDepartement = departementService.addDepartement(convertDTOtoEntity(departementDTO));

        // Convert the saved Departement entity back to DepartementDTO for response
        return convertEntityToDTO(savedDepartement);
    }

    // http://localhost:8089/Kaddem/departement/update-departement
    @PutMapping("/update-departement")
    @ResponseBody
    public DepartementDTO updateDepartement(@RequestBody DepartementDTO departementDTO) {
        // Convert DepartementDTO to Departement entity if needed before updating in the database
        Departement updatedDepartement = departementService.updateDepartement(convertDTOtoEntity(departementDTO));

        // Convert the updated Departement entity back to DepartementDTO for response
        return convertEntityToDTO(updatedDepartement);
    }



    // http://localhost:8089/Kaddem/departement/retrieveDepartementsByUniversite/1
    @GetMapping("/retrieveDepartementsByUniversite/{idUniversite}")
    @ResponseBody
    public List<Departement> retrieveDepartementsByUniversite(@PathVariable("idUniversite") Integer idUniversite) {
        return departementService.retrieveDepartementsByUniversite(idUniversite);
    }
    private Departement convertDTOtoEntity(DepartementDTO departementDTO) {
        Departement departement = new Departement();
        // Assuming the ID is not set for a new entity
        // Set other properties as needed
        departement.setIdDepartement(departementDTO.getIdDepartement());
        departement.setNomDepart(departementDTO.getNomDepart());
        return departement;
    }


    private DepartementDTO convertEntityToDTO(Departement departement) {
        DepartementDTO departementDTO = new DepartementDTO();
        departementDTO.setIdDepartement(departement.getIdDepartement());
        departementDTO.setNomDepart(departement.getNomDepart());
        return departementDTO;
    }






}
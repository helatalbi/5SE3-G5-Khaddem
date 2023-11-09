package tn.esprit.spring.khaddem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.khaddem.dto.EtudiantDTO;
import tn.esprit.spring.khaddem.entities.Etudiant;
import tn.esprit.spring.khaddem.entities.Niveau;
import tn.esprit.spring.khaddem.entities.Specialite;
import tn.esprit.spring.khaddem.services.IEtudiantService;

import java.util.List;

@RestController
@RequestMapping("/etudiant")
@CrossOrigin(origins = "*")
public class EtudiantRestController {
    @Autowired
    IEtudiantService etudiantService;
    // http://localhost:8089/Kaddem/etudiant/retrieve-all-etudiants
    @GetMapping("/retrieve-all-etudiants")
    @ResponseBody
    public List<Etudiant> getEtudiants() {
        return etudiantService.retrieveAllEtudiants();
    }


    // http://localhost:8089/Kaddem/etudiant/retrieve-etudiant/8
    @GetMapping("/retrieve-etudiant/{etudiantId}")
    @ResponseBody
    public Etudiant retrieveContrat(@PathVariable("etudiantId") Integer etudiantId) {
        return etudiantService.retrieveEtudiant(etudiantId);
    }

    // http://localhost:8089/Kaddem/etudiant/add-etudiant
    @PostMapping("/add-etudiant")
    @ResponseBody
    public EtudiantDTO addEtudiant(@RequestBody EtudiantDTO etudiantDTO) {
        // Convert EtudiantDTO to Etudiant entity before saving to the database
        Etudiant savedEtudiant = etudiantService.addEtudiant(convertDTOtoEntity(etudiantDTO));

        // Convert the saved Etudiant entity back to EtudiantDTO for response
        return convertEntityToDTO(savedEtudiant);
    }


    // http://localhost:8089/Kaddem/etudiant/update-etudiant
    @PutMapping("/update-etudiant")
    @ResponseBody
    public EtudiantDTO updateEtudiant(@RequestBody EtudiantDTO etudiantDTO) {
        // Convert EtudiantDTO to Etudiant entity if needed before updating in the database
        Etudiant updatedEtudiant = etudiantService.updateEtudiant(convertDTOtoEntity(etudiantDTO));

        // Convert the updated Etudiant entity back to EtudiantDTO for response
        return convertEntityToDTO(updatedEtudiant);
    }

    // http://localhost:8089/Kaddem/etudiant/removeEtudiant
    @DeleteMapping("/removeEtudiant/{idEtudiant}")
    @ResponseBody
    public void removeEtudiant(@PathVariable("idEtudiant") Integer idEtudiant) {
        etudiantService.removeEtudiant(idEtudiant);
    }

    // http://localhost:8089/Kaddem/etudiant/assignEtudiantToDepartement/1/1
    @PutMapping("/assignEtudiantToDepartement/{etudiantId}/{departementId}")
    @ResponseBody
    public void assignEtudiantToDepartement(@PathVariable("etudiantId") Integer etudiantId
            ,@PathVariable("departementId") Integer departementId) {
        etudiantService.assignEtudiantToDepartement(etudiantId,departementId);
    }

    // http://localhost:8089/Kaddem/etudiant/findByDepartement/1
    @GetMapping("/findByDepartement/{departement-id}")
    @ResponseBody
    public List<Etudiant> findByDepartement(@PathVariable("departement-id") Integer departementId) {
        return etudiantService.findByDepartementIdDepartement(departementId);
    }

    // http://localhost:8089/Kaddem/etudiant/findByEquipesNiveau/JUNIOR
    @GetMapping("/findByEquipesNiveau/{niveau}")
    @ResponseBody
    public List<Etudiant> findByEquipesNiveau(@PathVariable("niveau") Niveau niveau) {
        return etudiantService.findByEquipesNiveau(niveau);
    }

    // http://localhost:8089/Kaddem/etudiant/retrieveEtudiantsByContratSpecialite/SECURITE
    @GetMapping("/retrieveEtudiantsByContratSpecialite/{specialite}")
    @ResponseBody
    public List<Etudiant> retrieveEtudiantsByContratSpecialite(@PathVariable("specialite") Specialite specialite) {
        return etudiantService.retrieveEtudiantsByContratSpecialite(specialite);
    }

    // http://localhost:8089/Kaddem/etudiant/retrieveEtudiantsByContratSpecialiteSQL/SECURITE
    @GetMapping("/retrieveEtudiantsByContratSpecialiteSQL/{specialite}")
    @ResponseBody
    public List<Etudiant> retrieveEtudiantsByContratSpecialiteSQL(@PathVariable("specialite") String specialite) {
        return etudiantService.retrieveEtudiantsByContratSpecialiteSQL(specialite);
    }

    // http://localhost:8089/Kaddem/etudiant/addAndAssignEtudiantToEquipeAndContract/1/1
    @PostMapping("/addAndAssignEtudiantToEquipeAndContract/{equipeId}/{contratId}")
    @ResponseBody
    public void addAndAssignEtudiantToEquipeAndContract(@RequestBody EtudiantDTO etudiantDTO,
                                                        @PathVariable("contratId") Integer contratId, @PathVariable("equipeId") Integer equipeId) {
        // Convert EtudiantDTO to Etudiant entity before processing
        Etudiant etudiant = convertDTOtoEntity(etudiantDTO);

        // Call the service method with the converted Etudiant entity
        etudiantService.addAndAssignEtudiantToEquipeAndContract(etudiant, contratId, equipeId);
    }
    // http://localhost:8089/Kaddem/etudiant/getEtudiantsByDepartement/1
    @GetMapping("/getEtudiantsByDepartement/{idDepartement}")
    @ResponseBody
    public List<Etudiant> getEtudiantsByDepartement(@PathVariable("idDepartement") Integer idDepartement) {
        return etudiantService.getEtudiantsByDepartement(idDepartement);
    }
    private Etudiant convertDTOtoEntity(EtudiantDTO etudiantDTO) {
        Etudiant etudiant = new Etudiant();
        // Assuming the ID is not set for a new entity
        etudiant.setIdEtudiant(null);
        etudiant.setPrenomE(etudiantDTO.getPrenomE());
        etudiant.setNomE(etudiantDTO.getNomE());
        etudiant.setOp(etudiantDTO.getOp());
        // Set other properties as needed
        return etudiant;
    }

    private EtudiantDTO convertEntityToDTO(Etudiant etudiant) {
        EtudiantDTO etudiantDTO = new EtudiantDTO();
        etudiantDTO.setIdEtudiant(etudiant.getIdEtudiant());
        etudiantDTO.setPrenomE(etudiant.getPrenomE());
        etudiantDTO.setNomE(etudiant.getNomE());
        etudiantDTO.setOp(etudiant.getOp());
        // Set other properties as needed
        return etudiantDTO;
    }

}

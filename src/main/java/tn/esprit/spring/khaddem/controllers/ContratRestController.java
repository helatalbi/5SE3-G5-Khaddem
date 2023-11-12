package tn.esprit.spring.khaddem.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.khaddem.dto.ContratDTO;
import tn.esprit.spring.khaddem.entities.Contrat;
import tn.esprit.spring.khaddem.services.IContratService;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/contrat")
@CrossOrigin(origins = "*")
public class ContratRestController {
    @Autowired

    IContratService contratService;

    // http://localhost:8089/Kaddem/contrat/retrieve-all-contrats
    @GetMapping("/retrieve-all-contrats")
    @ResponseBody
    public List<Contrat> getContrats() {
        return contratService.retrieveAllContrats();
    }


    // http://localhost:8089/Kaddem/contrat/retrieve-contrat/8
    @GetMapping("/retrieve-contrat/{contrat-id}")
    @ResponseBody
    public Contrat retrieveContrat(@PathVariable("contrat-id") Integer contratId) {
        return contratService.retrieveContrat(contratId);
    }

    // http://localhost:8089/Kaddem/contrat/add-contrat
    @PostMapping("/add-contrat")
    @ResponseBody
    public ContratDTO addContrat(@RequestBody ContratDTO contratDTO) {
        // Convert ContratDTO to Contrat entity if needed before saving to the database
        Contrat contrat = convertDTOtoEntity(contratDTO);
        Contrat savedContrat = contratService.addContrat(contrat);

        // Convert the saved Contrat entity back to ContratDTO for response
        return convertEntityToDTO(savedContrat);
    }


    // http://localhost:8089/Kaddem/contrat/update-contrat
    @PutMapping("/update-contrat")
    @ResponseBody
    public ContratDTO updateContrat(@RequestBody ContratDTO contratDTO) {
        // Convert ContratDTO to Contrat entity if needed before updating in the database
        Contrat updatedContrat = contratService.updateContrat(convertDTOtoEntity(contratDTO));

        // Convert the updated Contrat entity back to ContratDTO for response
        return convertEntityToDTO(updatedContrat);
    }



    // http://localhost:8089/Kaddem/contrat/addAndAffectContratToEtudiant/salah/ahmed
    @PostMapping("/addAndAffectContratToEtudiant/{nomE}/{prenomE}")
    public ResponseEntity<ContratDTO> addAndAffectContratToEtudiant(
            @RequestBody ContratDTO contratDTO,
            @PathVariable("nomE") String nomE,
            @PathVariable("prenomE") String prenomE) {

        try {
            // Convert ContratDTO to Contrat entity if needed before saving to the database
            Contrat contrat = convertDTOtoEntity(contratDTO);
            Contrat savedContrat = contratService.addAndAffectContratToEtudiant(contrat, nomE, prenomE);

            // Convert the saved Contrat entity back to ContratDTO for response
            ContratDTO savedContratDTO = convertEntityToDTO(savedContrat);

            return ResponseEntity.ok(savedContratDTO);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    //The most common ISO Date Format yyyy-MM-dd — for example, "2000-10-31".
    @GetMapping(value = "/getnbContratsValides/{startDate}/{endDate}")
    public Integer getnbContratsValides(@PathVariable(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                        @PathVariable(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        return contratService.nbContratsValides(startDate, endDate);
    }

    //Only no-arg methods may be annotated with @Scheduled
    @Scheduled(cron="0 0 13 * * *")//(cron="0 0 13 * * ?")(fixedRate =21600)
  //  @Scheduled(cron="45 * * * * *")//(cron="0 0 13 * * ?")(fixedRate =21600)
    @PutMapping(value = "/majStatusContrat")
    public void majStatusContrat (){
        contratService.retrieveAndUpdateStatusContrat();
    }

    //public float getChiffreAffaireEntreDeuxDate(Date startDate, Date endDate)

    @GetMapping("/calculChiffreAffaireEntreDeuxDate/{startDate}/{endDate}")
    @ResponseBody
    public float calculChiffreAffaireEntreDeuxDates(@PathVariable(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                    @PathVariable(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        return contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate);
    }
    private Contrat convertDTOtoEntity(ContratDTO contratDTO) {
        Contrat contrat = new Contrat();
        contrat.setDateDebutContrat(contratDTO.getDateDebutContrat());
        contrat.setDateFinContrat(contratDTO.getDateFinContrat());
        contrat.setSpecialite(contratDTO.getSpecialite());
        contrat.setArchived(contratDTO.getArchived());
        contrat.setMontantContrat(contratDTO.getMontantContrat());
        // Set other properties as needed
        return contrat;
    }

    private ContratDTO convertEntityToDTO(Contrat contrat) {
        ContratDTO contratDTO = new ContratDTO();
        contratDTO.setDateDebutContrat(contrat.getDateDebutContrat());
        contratDTO.setDateFinContrat(contrat.getDateFinContrat());
        contratDTO.setSpecialite(contrat.getSpecialite());
        contratDTO.setArchived(contrat.getArchived());
        contratDTO.setMontantContrat(contrat.getMontantContrat());
        // Set other properties as needed
        return contratDTO;
    }


}

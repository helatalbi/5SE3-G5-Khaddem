package tn.esprit.spring.khaddem.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import tn.esprit.spring.khaddem.entities.Contrat;
import tn.esprit.spring.khaddem.entities.Etudiant;
import tn.esprit.spring.khaddem.repositories.ContratRepository;
import tn.esprit.spring.khaddem.repositories.EtudiantRepository;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ContratServiceImpl implements  IContratService{


@Autowired
    ContratRepository contratRepository;
@Autowired
    EtudiantRepository etudiantRepository;

    @Override
    public List<Contrat> retrieveAllContrats() {
        log.info("debut methode retrieveAllContrats");
        return contratRepository.findAll();
    }

    @Override
    public Contrat updateContrat(Contrat ce) {
        log.info("debut methode updateContrat");
        contratRepository.save(ce);
        return ce;
    }

    @Override
    public Contrat retrieveContrat(Integer idContrat) {
        log.info("debut methode retrieveContrat");
        Optional<Contrat> optionalContrat = contratRepository.findById(idContrat);
        if (optionalContrat.isPresent()) {
            return optionalContrat.get();
        } else {
            // Handle the case when the Contrat with the given id is not found
            throw new NotFoundException("Contrat not found with id: " + idContrat);
        }
    }



    @Override
    public Contrat addContrat(Contrat c) {
       // start date t1
        contratRepository.save(c);

        return c;
    }

    @Transactional
    public Contrat addAndAffectContratToEtudiant(Contrat ce, String nomE, String prenomE) {
        Long startDate = new Date().getTime();
        log.info("startDate: "+startDate);
        log.info("debut methode addAndAffectContratToEtudiant");
        Etudiant etudiant=etudiantRepository.findByNomEAndPrenomE(nomE,prenomE);
        log.info("etudiant: "+etudiant.getNomE()+" "+etudiant.getPrenomE());
        // nb contrats actifs
        int nbContratsActifs= etudiant.getContrats().size();
        if(nbContratsActifs>5) {
            log.info("nombre de contrats autorisés est atteint");
            Long endDate = new Date().getTime();
            long executionTime = endDate-startDate;
            log.info("endDate: "+startDate);
            log.info("executionTime: "+executionTime+ " ms");
            return ce;
        }
        log.info("nb Contrats en cours: "+nbContratsActifs);
        contratRepository.save(ce);
        ce.setEtudiant(etudiant);
        log.info("fin methode addAndAffectContratToEtudiant");
        Long endDate = new Date().getTime();
        long executionTime = endDate-startDate;

        log.info("endDate: "+startDate);
        log.info("executionTime: "+executionTime+ " ms");

        return ce;
    }

    public 	Integer nbContratsValides(Date startDate, Date endDate){
        return contratRepository.getnbContratsValides(startDate, endDate);
    }

    public void retrieveAndUpdateStatusContrat(){
        log.info("debut methode retrieveAndUpdateStatusContrat");
        List<Contrat>contrats=contratRepository.findAll();
        log.info("total contrats :"+contrats.size());

        for (Contrat contrat : contrats) {
            log.info("id: "+contrat.getIdContrat());
            log.info("date fin"+contrat.getDateFinContrat());
            log.info("archived "+contrat.getArchived());

            Date dateSysteme = new Date();

            if (contrat.getArchived() == null || !contrat.getArchived()) {
                long differenceInTime = contrat.getDateFinContrat().getTime()-dateSysteme.getTime();
                long differenceInDays = (differenceInTime / (1000 * 60 * 60 * 24)) % 365;
                // il est préférable d'utiliser des méthodes prédéfinis de comparaison
               log.info("difference in days : "+differenceInDays);
                    if (differenceInDays==15){  // pour 15 jours exactement
                    log.info(" Contrat Commencant le : " + contrat.getDateDebutContrat()+"pour l'etudiant "+contrat.getEtudiant().getNomE()+
                            " "+contrat.getEtudiant().getPrenomE()+"  va bientot s achever le "
                    +contrat.getDateFinContrat());
                }
                if (differenceInDays==0) {
                    log.info("jour j: " + contrat.getIdContrat());
                    contrat.setArchived(true);
                    contratRepository.save(contrat);
                }
            }

            log.info("debut methode retrieveAndUpdateStatusContrat");
        }
    }
    public float getChiffreAffaireEntreDeuxDates(Date startDate, Date endDate) {
        long differenceInTime = endDate.getTime() - startDate.getTime();
        float differenceInDays = (float) differenceInTime / (1000 * 60 * 60 * 24);
        float differenceInMonths = differenceInDays / 30;

        List<Contrat> contrats = contratRepository.findAll();

        float chiffreAffaireEntreDeuxDates = 0;
        float chiffreAffaireEntreDeuxDatesIA = 0;
        float chiffreAffaireEntreDeuxDatesCloud = 0;
        float chiffreAffaireEntreDeuxDatesReseau = 0;
        float chiffreAffaireEntreDeuxDatesSecurite = 0;

        for (Contrat contrat : contrats) {
            float monthlyRevenue = differenceInMonths * contrat.getMontantContrat();

            switch (contrat.getSpecialite()) {
                case IA:
                    chiffreAffaireEntreDeuxDatesIA += monthlyRevenue;
                    break;
                case CLOUD:
                    chiffreAffaireEntreDeuxDatesCloud += monthlyRevenue;
                    break;
                case RESEAU:
                    chiffreAffaireEntreDeuxDatesReseau += monthlyRevenue;
                    break;
                case SECURITE:
                    chiffreAffaireEntreDeuxDatesSecurite += monthlyRevenue;
                    break;
            }

            chiffreAffaireEntreDeuxDates += monthlyRevenue;
        }

        log.info("chiffreAffaireEntreDeuxDates: " + chiffreAffaireEntreDeuxDates);
        log.info("chiffreAffaireEntreDeuxDatesIA: " + chiffreAffaireEntreDeuxDatesIA);
        log.info("chiffreAffaireEntreDeuxDatesCloud: " + chiffreAffaireEntreDeuxDatesCloud);
        log.info("chiffreAffaireEntreDeuxDatesReseau: " + chiffreAffaireEntreDeuxDatesReseau);
        log.info("chiffreAffaireEntreDeuxDatesSecurite: " + chiffreAffaireEntreDeuxDatesSecurite);

        return chiffreAffaireEntreDeuxDates;
    }


}

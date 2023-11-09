package tn.esprit.spring.khaddem.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.khaddem.entities.Contrat;
import tn.esprit.spring.khaddem.entities.Equipe;
import tn.esprit.spring.khaddem.entities.Etudiant;
import tn.esprit.spring.khaddem.entities.Niveau;
import tn.esprit.spring.khaddem.repositories.ContratRepository;
import tn.esprit.spring.khaddem.repositories.EquipeRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EquipeServiceImpl implements IEquipeService {
@Autowired
    private final EquipeRepository equipeRepository;
@Autowired
    private final ContratRepository contratRepository;

    @Override
    public List<Equipe> retrieveAllEquipes() {
        return equipeRepository.findAll();
    }

    @Transactional
    public Equipe addEquipe(Equipe e) {
        equipeRepository.save(e);
        return e;
    }


    @Override
    public Equipe updateEquipe(Equipe e) {
        equipeRepository.save(e);
        return e;
    }
    @Override
    public Equipe retrieveEquipe(Integer idEquipe) {
        return equipeRepository.findById(idEquipe).orElse(null);
    }

    public void evoluerEquipes() {
        log.info("debut methode evoluerEquipes");

        List<Equipe> equipes = equipeRepository.findAll();
        log.debug("nombre equipes : " + equipes.size());

        for (Equipe equipe : equipes) {
            if (!equipe.getEtudiants().isEmpty() && (equipe.getNiveau().equals(Niveau.JUNIOR) || equipe.getNiveau().equals(Niveau.SENIOR))) {
                evoluerEquipeNiveau(equipe);
            }
        }

        log.info("fin methode evoluerEquipes");
    }

    private void evoluerEquipeNiveau(Equipe equipe) {
        List<Etudiant> etudiants = equipe.getEtudiants();
        int nbEtudiantsAvecContratsActifs = 0;

        for (Etudiant etudiant : etudiants) {
            List<Contrat> contrats = contratRepository.findByEtudiantIdEtudiant(etudiant.getIdEtudiant());

            for (Contrat contrat : contrats) {
                long differenceInTime = contrat.getDateFinContrat().getTime() - contrat.getDateDebutContrat().getTime();
                long differenceInYears = (differenceInTime / (1000L * 60 * 60 * 24 * 365));

                if ((contrat.getArchived() == null || !contrat.getArchived()) && (differenceInYears > 1)) {
                    nbEtudiantsAvecContratsActifs++;
                    break;
                }
            }
        }

        log.info("nbEtudiantsAvecContratsActifs: " + nbEtudiantsAvecContratsActifs);

        if (nbEtudiantsAvecContratsActifs >= 3) {
            updateEquipeNiveau(equipe);
        }
    }

    private void updateEquipeNiveau(Equipe equipe) {
        if (equipe.getNiveau().equals(Niveau.JUNIOR)) {
            log.info("mise a jour du niveau de l equipe " + equipe.getNomEquipe() +
                    " du niveau " + equipe.getNiveau() + " vers le niveau supérieur SENIOR");
            equipe.setNiveau(Niveau.SENIOR);
            equipeRepository.save(equipe);
        } else if (equipe.getNiveau().equals(Niveau.SENIOR)) {
            log.info("mise a jour du niveau de l equipe " + equipe.getNomEquipe() +
                    " du niveau " + equipe.getNiveau() + " vers le niveau supérieur EXPERT");
            equipe.setNiveau(Niveau.EXPERT);
            equipeRepository.save(equipe);
        }
    }

}

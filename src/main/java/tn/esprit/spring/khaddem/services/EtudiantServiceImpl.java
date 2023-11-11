package tn.esprit.spring.khaddem.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.khaddem.entities.*;
import tn.esprit.spring.khaddem.repositories.ContratRepository;
import tn.esprit.spring.khaddem.repositories.DepartementRepository;
import tn.esprit.spring.khaddem.repositories.EquipeRepository;
import tn.esprit.spring.khaddem.repositories.EtudiantRepository;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class EtudiantServiceImpl implements IEtudiantService{
@Autowired
    EtudiantRepository etudiantRepository;
@Autowired

    DepartementRepository departementRepository;
@Autowired

    ContratRepository contratRepository;
@Autowired

    EquipeRepository equipeRepository;
    @Override
    public List<Etudiant> retrieveAllEtudiants() {
        return etudiantRepository.findAll();
    }

    @Override
    public Etudiant addEtudiant(Etudiant e) {
        etudiantRepository.save(e);
        return e;
    }

    @Override
    public Etudiant updateEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }


    @Override
    public Etudiant retrieveEtudiant(Integer idEtudiant) {
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findById(idEtudiant);
        return optionalEtudiant.orElse(null);
    }


    @Override
    public void removeEtudiant(Integer idEtudiant) {
        etudiantRepository.deleteById(idEtudiant);
    }

    @Override
    public void assignEtudiantToDepartement(Integer etudiantId, Integer departementId) {
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findById(etudiantId);
        Optional<Departement> optionalDepartement = departementRepository.findById(departementId);

        if (optionalEtudiant.isPresent() && optionalDepartement.isPresent()) {
            Etudiant e = optionalEtudiant.get();
            Departement d = optionalDepartement.get();
            e.setDepartement(d);
            etudiantRepository.save(e);
        }
    }



    @Override
    public List<Etudiant> findByDepartementIdDepartement(Integer idDepartement) {
        return etudiantRepository.findByDepartementIdDepartement(idDepartement);
    }

    @Override
    public List<Etudiant> findByEquipesNiveau(Niveau niveau) {
        return etudiantRepository.findByEquipesNiveau(niveau);
    }

    @Override
    public List<Etudiant> retrieveEtudiantsByContratSpecialite(Specialite specialite) {
        return etudiantRepository.retrieveEtudiantsByContratSpecialite(specialite);
    }

    @Override
    public List<Etudiant> retrieveEtudiantsByContratSpecialiteSQL(String specialite) {
        return etudiantRepository.retrieveEtudiantsByContratSpecialiteSQL(specialite);
    }

    @Transactional
    public Etudiant addAndAssignEtudiantToEquipeAndContract(Etudiant e, Integer idContrat, Integer idEquipe){
        Contrat c = contratRepository.findById(idContrat).orElse(null);
        Equipe eq = equipeRepository.findById(idEquipe).orElse(null);

        if (c != null) {
            c.setEtudiant(e);
            contratRepository.save(c);
        }

        if (eq != null) {
            eq.getEtudiants().add(e);
            equipeRepository.save(eq);
        }

        etudiantRepository.save(e);
        return e;
    }


    @Override
    public List<Etudiant> getEtudiantsByDepartement(Integer idDepartement) {
        Optional<Departement> optionalDepartement = departementRepository.findById(idDepartement);

        if (optionalDepartement.isPresent()) {
            Departement departement = optionalDepartement.get();
            return departement.getEtudiants();
        } else {
            // Handle the case when Departement is not found
            // You might throw an exception, log a message, or take other appropriate actions.
            return Collections.emptyList(); // Or another default value as needed
        }
    }


}

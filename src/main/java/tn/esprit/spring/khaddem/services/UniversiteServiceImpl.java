package tn.esprit.spring.khaddem.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import tn.esprit.spring.khaddem.entities.Departement;
import tn.esprit.spring.khaddem.entities.Universite;
import tn.esprit.spring.khaddem.repositories.DepartementRepository;
import tn.esprit.spring.khaddem.repositories.UniversiteRepository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UniversiteServiceImpl implements  IUniversiteService{
    @Autowired

    UniversiteRepository universiteRepository;
    @Autowired

    DepartementRepository departementRepository;
    @Override
    public List<Universite> retrieveAllUniversites() {
        return universiteRepository.findAll();
    }

    @Override
    public Universite addUniversite(Universite u) {
        log.debug("u :"+u.getNomUniv());
        universiteRepository.save(u);
        return u;
    }

    @Override
    public Universite updateUniversite(Universite u) {
        universiteRepository.save(u);
        return u;
    }

    @Override
    public Universite retrieveUniversite(Integer idUniversite) {
        Optional<Universite> universiteOptional = universiteRepository.findById(idUniversite);

        if (universiteOptional.isPresent()) {
            return universiteOptional.get();
        } else {
            // Handle the case when the Universite with the given id is not found
            throw new NotFoundException("Universite not found with id: " + idUniversite);
        }
    }


    @Transactional
    public void assignUniversiteToDepartement(Integer universiteId, Integer departementId) {
        Optional<Universite> universiteOptional = universiteRepository.findById(universiteId);
        Optional<Departement> departementOptional = departementRepository.findById(departementId);

        if (universiteOptional.isPresent() && departementOptional.isPresent()) {
            Universite universite = universiteOptional.get();
            Departement departement = departementOptional.get();

            universite.getDepartements().add(departement);
            log.info("Departments number: " + universite.getDepartements().size());
        } else {
            // Handle the case when either Universite or Departement is not found
            throw new NotFoundException("Universite or Departement not found. Universite ID: " + universiteId + ", Departement ID: " + departementId);
        }
    }

}

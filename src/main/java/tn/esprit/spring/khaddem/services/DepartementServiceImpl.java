package tn.esprit.spring.khaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import tn.esprit.spring.khaddem.entities.Departement;
import tn.esprit.spring.khaddem.entities.Universite;
import tn.esprit.spring.khaddem.repositories.DepartementRepository;
import tn.esprit.spring.khaddem.repositories.UniversiteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartementServiceImpl implements IDepartementService{
    @Autowired
    DepartementRepository departementRepository;
    @Autowired
    UniversiteRepository universiteRepository;
    @Override
    public List<Departement> retrieveAllDepartements() {
        return departementRepository.findAll();
    }

    @Override
    public Departement addDepartement(Departement d) {
        departementRepository.save(d);
        return d;
    }

    @Override
    public Departement updateDepartement(Departement updatedDepartement) {
        Optional<Departement> existingDepartementOptional = departementRepository.findById(updatedDepartement.getIdDepartement());

        if (existingDepartementOptional.isPresent()) {
            Departement existingDepartement = existingDepartementOptional.get();

            // Update the existing Departement with the new values
            existingDepartement.setNomDepart(updatedDepartement.getNomDepart());
            // Update other properties as needed

            // Save the updated Departement
            departementRepository.save(existingDepartement);

            return existingDepartement;
        } else {
            // Handle the case when the Departement with the given id is not found
            throw new NotFoundException("Departement not found with id: " + updatedDepartement.getIdDepartement());
        }
    }


    @Override
    public Departement retrieveDepartement(Integer idDepart) {
        Optional<Departement> optionalDepartement = departementRepository.findById(idDepart);
        if (optionalDepartement.isPresent()) {
            return optionalDepartement.get();
        } else {
            // Handle the case when the Departement with the given id is not found
            throw new NotFoundException("Departement not found with id: " + idDepart);
        }
    }

    @Override
    public List<Departement> retrieveDepartementsByUniversite(Integer idUniversite) {
        Optional<Universite> optionalUniversite = universiteRepository.findById(idUniversite);
        if (optionalUniversite.isPresent()) {
            return optionalUniversite.get().getDepartements();
        } else {
            // Handle the case when the Universite with the given id is not found
            throw new NotFoundException("Universite not found with id: " + idUniversite);
        }
    }

}

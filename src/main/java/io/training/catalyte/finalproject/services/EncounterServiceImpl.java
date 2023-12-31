package io.training.catalyte.finalproject.services;

import io.training.catalyte.finalproject.entities.Encounter;
import io.training.catalyte.finalproject.exceptions.ResourceNotFound;
import io.training.catalyte.finalproject.exceptions.ServiceUnavailable;
import io.training.catalyte.finalproject.repositories.EncounterRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * Implementation of Encounter crud methods
 */
@Service
public class EncounterServiceImpl implements EncounterService {

  private final Logger logger = LoggerFactory.getLogger(EncounterServiceImpl.class);

  @Autowired
  private EncounterRepository encounterRepository;

  /**
   * Retrieves all encounters from the database.
   *
   * @return a list of all encounters.
   */
  @Override
  public List<Encounter> getAll(Encounter encounter) {
    List<Encounter> encounterList = new ArrayList<>();

    try {
      if (encounter.isEmpty()) {
        encounterList = encounterRepository.findAll();
      } else {
        Example<Encounter> encounterExample = Example.of(encounter);
        return encounterRepository.findAll(encounterExample);
      }

    } catch (Exception e) {
      logger.error(e.getMessage());
      throw new ServiceUnavailable(e);
    }

    return encounterList;
  }

  /**
   * Retrieves an encounter from the database by its id.
   *
   * @param id the id of the encounter to retrieve
   * @return the specified encounter
   */
  @Override
  public Encounter getById(Long id) {
    Optional<Encounter> encounter = Optional.ofNullable(null);

    try {
      encounter = encounterRepository.findById(id);
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw new ServiceUnavailable(e);
    }

    if (encounter.isEmpty()) {
      throw new ResourceNotFound();
    } else {
      return encounter.get();
    }
  }
}

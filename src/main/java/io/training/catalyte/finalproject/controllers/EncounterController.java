package io.training.catalyte.finalproject.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.training.catalyte.finalproject.entities.Encounter;
import io.training.catalyte.finalproject.services.EncounterService;
import io.training.catalyte.finalproject.entities.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for encounters
 */
@RestController
@RequestMapping("/encounters")
public class EncounterController {

  private final Logger logger = LoggerFactory.getLogger(EncounterController.class);

  @Autowired
  private EncounterService encounterService;

  /**
   * Gets an encounter based off of its id
   *
   * @param id
   * @return
   */
  @GetMapping("/{id}")
  @ApiOperation("Retrieve an encounter by id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Patient.class)
  })
  public ResponseEntity<Encounter> getEncounterById(@PathVariable Long id) {
    logger.info(" Get all request received");
    return new ResponseEntity<>(encounterService.getById(id), HttpStatus.OK);
  }
}

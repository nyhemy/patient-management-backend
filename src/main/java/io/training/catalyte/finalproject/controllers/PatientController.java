package io.training.catalyte.finalproject.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.training.catalyte.finalproject.entities.Encounter;
import io.training.catalyte.finalproject.entities.Patient;
import io.training.catalyte.finalproject.services.PatientService;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller for patients
 */
@RestController
@RequestMapping("/patients")
public class PatientController {

  private final Logger logger = LoggerFactory.getLogger(PatientController.class);

  @Autowired
  private PatientService patientService;

  /**
   * This method retrieves all patients from the database
   *
   * @return a collection of patients and 200 status code
   */
  @GetMapping
  @ApiOperation("Retrieve all patients")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Patient.class)
  })
  public ResponseEntity<List<Patient>> getAllPatients() {
    logger.info(" Get all request received");
    return new ResponseEntity<>(patientService.getAll(), HttpStatus.OK);
  }

  /**
   * This method retrieves a single reservation from the database
   *
   * @param id the id of the reservation
   * @return a reservation by the id provided and 200 status code
   */
  @GetMapping("/{id}")
  @ApiOperation("Retrieve a patient by id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Patient.class)
  })
  public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
    logger.info(" Get request received");
    return new ResponseEntity<>(patientService.getById(id), HttpStatus.OK);
  }

  /**
   * Method that retrieves all encounters for a patient based of patient id
   *
   * @param id of patient
   * @return list of encounters that contain patient id provdied
   */
  @GetMapping("/{id}/encounters")
  @ApiOperation("Retrieve all encounters for patient by id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Encounter.class)
  })
  public ResponseEntity<List<Encounter>> getAllEncountersByPatientId(@PathVariable Long id) {
    logger.info(" Get all request received");
    return new ResponseEntity<>(patientService.findEncountersByPatientId(id), HttpStatus.OK);
  }

  /**
   * This method creates a new patient record and saves it to the database
   *
   * @param patient to be created
   * @return created patient and 201 status code
   */
  @PostMapping
  @ApiOperation("Add a single patient by the patient info provided")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Created", response = Patient.class),
      @ApiResponse(code = 400, message = "Invalid request", response = ResponseStatusException.class)
  })
  public ResponseEntity<Patient> createPatient(
      @Valid @RequestBody Patient patient) {
    logger.info(" Post request received");
    return new ResponseEntity<>(patientService.createPatient(patient),
        HttpStatus.CREATED);
  }

  /**
   * Creates new encounter for specified patient
   *
   * @param id id of patient to receive new encounter
   * @param encounter is encounter object to be created
   * @return created encounter and 201 status code
   */
  @PostMapping("/{id}/encounters")
  @ApiOperation("Add a single encounter to patient based off id, by encounter info provided")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Created", response = Patient.class),
      @ApiResponse(code = 400, message = "Invalid request", response = ResponseStatusException.class)
  })
  public ResponseEntity<Encounter> createEncounterForPatient(@PathVariable Long id, @Valid @RequestBody Encounter encounter) {
    logger.info(" Post request received");
    return new ResponseEntity<>(patientService.createEncounterForPatientById(id, encounter), HttpStatus.CREATED);
  }

  /**
   * This method updates and existing patient record
   *
   * @param patient updated patient information
   * @param id of the patient to be updated
   * @return updated patient and 200 status code
   */
  @PutMapping("/{id}")
  @ApiOperation("Update a single patient by id and patient info provided")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Patient.class),
      @ApiResponse(code = 400, message = "Invalid request", response = ResponseStatusException.class)
  })
  public ResponseEntity<Patient> updatePatient(@Valid @RequestBody Patient patient,
      @PathVariable Long id) {
    logger.info(" Put request received");
    return new ResponseEntity<>(patientService.updatePatient(id, patient),
        HttpStatus.OK);
  }

  /**
   * Method updates and existing encounter record for a specific patient
   *
   * @param id of patient
   * @param encounterId id of encounter to be updated
   * @param encounter updated encounter information
   * @return updated encounter and 200 status code
   */
  @PutMapping("/{id}/encounters/{encounterId}")
  @ApiOperation("Update a single encounter pertaining to a patient with encounter info provided")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Encounter.class),
      @ApiResponse(code = 400, message = "Invalid request", response = ResponseStatusException.class)
  })
  public ResponseEntity<Encounter> updateEncounterForPatient(@PathVariable Long id,
      @PathVariable Long encounterId, @Valid @RequestBody Encounter encounter) {
    logger.info (" Put request received");
    return new ResponseEntity<>(patientService.updateEncounterByPatientId(id, encounterId,
        encounter), HttpStatus.OK);
  }

  /**
   * This method deletes and existing patient record
   *
   * @param id of the patient to be deleted
   * @return 204 no content
   */
  @DeleteMapping("/{id}")
  @ApiOperation("Delete a single patient by id")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "No Content", response = Patient.class),
      @ApiResponse(code = 400, message = "Invalid request", response = ResponseStatusException.class)
  })
  public ResponseEntity<Patient> deletePatient(@PathVariable Long id) {
    logger.info(" Delete request received");
    patientService.deletePatient(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

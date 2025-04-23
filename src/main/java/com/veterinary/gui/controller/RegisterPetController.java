package com.veterinary.gui.controller;

import com.veterinary.business.dto.PetDTO;
import com.veterinary.db.dao.PetDAO;
import com.veterinary.gui.Modal;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;


public class RegisterPetController {
  private final PetDAO PET_DAO = new PetDAO();
  @FXML
  private TextField fieldOwnerEmail;
  @FXML
  private TextField fieldName;
  @FXML
  private TextField fieldSpecies;
  @FXML
  private TextField fieldBreed;
  @FXML
  private TextField fieldColour;
  @FXML
  private TextField fieldWeight;
  @FXML
  private TextField fieldHeight;
  @FXML
  private DatePicker fieldBirthDate;

  public void handleRegisterPet(ActionEvent event) {
    PetDTO dataObject = new PetDTO.PetBuilder()
      .setName(fieldName.getText())
      .setIDOwner(fieldOwnerEmail.getText())
      .setSpecies(fieldSpecies.getText())
      .setBreed(fieldBreed.getText())
      .setColour(fieldColour.getText())
      .setWeight(Float.parseFloat(fieldWeight.getText()))
      .setHeight(Float.parseFloat(fieldHeight.getText()))
      .setBirthDate(fieldBirthDate.getValue().atStartOfDay())
      .build();

    try {
      PET_DAO.createOne(dataObject);
      Modal.displaySuccess("Dueño ha sido creado con éxito.");
    } catch (Exception e) {
      Modal.displayError("No ha sido posible registrar el dueño debido a un error en el sistema");
    }
  }
}

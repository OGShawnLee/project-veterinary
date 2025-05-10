package com.veterinary.gui.controller.register;

import com.veterinary.business.dto.PetDTO;
import com.veterinary.business.dao.PetDAO;
import com.veterinary.gui.Modal;

import com.veterinary.gui.controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;


public class RegisterPetController extends Controller {
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

  public void handleRegisterPet() {
    try {
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

      PET_DAO.createOne(dataObject);
      Modal.displaySuccess("Dueño ha sido creado con éxito.");
    } catch (IllegalArgumentException e) {
      Modal.displayError(e.getMessage());
    } catch (SQLException e) {
      Modal.displayError("No ha sido posible registrar el dueño debido a un error en la base de datos" + e.getMessage());
    }
  }

  public static void navigateToRegisterPetPage(Stage currentStage) {
    navigateTo(currentStage, "Registrar Mascota", "RegisterPetPage");
  }
}

package com.veterinary.gui.controller.register;

import com.veterinary.business.dao.OwnerDAO;
import com.veterinary.business.dto.OwnerDTO;
import com.veterinary.business.dto.PetDTO;
import com.veterinary.business.dao.PetDAO;
import com.veterinary.gui.Modal;

import com.veterinary.gui.controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class RegisterPetController extends Controller {
  private static final OwnerDAO OWNER_DAO = new OwnerDAO();
  private static final PetDAO PET_DAO = new PetDAO();
  @FXML
  private ComboBox<OwnerDTO> comboBoxOwner;
  @FXML
  private TextField fieldName;
  @FXML
  private ComboBox<PetDTO.Species> comboBoxSpecies;
  @FXML
  private TextField fieldBreed;
  @FXML
  private TextField fieldColour;
  @FXML
  private TextField fieldWeight;
  @FXML
  private TextField fieldHeight;
  @FXML
  private DatePicker datePickerBirthDate;

  public void initialize() {
    loadComboBoxOwner(comboBoxOwner);
    loadComboBoxSpecies(comboBoxSpecies);
  }

  public static void loadComboBoxOwner(ComboBox<OwnerDTO> comboBoxOwner) {
    try {
      comboBoxOwner.getItems().addAll(OWNER_DAO.getAll());

      if (comboBoxOwner.getItems().isEmpty()) {
        Modal.displayError("No existe un dueño. Por favor, registre un dueño antes de registrar una mascota.");
        return;
      }

      comboBoxOwner.setValue(comboBoxOwner.getItems().get(0));
    } catch (SQLException e) {
      Modal.displayError("No ha sido posible cargar los dueños debido a un error en el sistema.");
    }
  }

  public static void loadComboBoxSpecies(ComboBox<PetDTO.Species> comboBoxSpecies) {
    comboBoxSpecies.getItems().addAll(PetDTO.Species.values());
    comboBoxSpecies.setValue(PetDTO.Species.DOG);
  }

  public void handleRegister() {
    try {
      PetDTO petDTO = new PetDTO.PetBuilder()
        .setName(fieldName.getText())
        .setIDOwner(comboBoxOwner.getValue().getEmail())
        .setSpecies(comboBoxSpecies.getValue())
        .setBreed(fieldBreed.getText())
        .setColour(fieldColour.getText())
        .setWeight(Float.parseFloat(fieldWeight.getText()))
        .setHeight(Float.parseFloat(fieldHeight.getText()))
        .setBirthDate(datePickerBirthDate.getValue().atStartOfDay())
        .build();

      PET_DAO.createOne(petDTO);
      Modal.displaySuccess("La máscota ha sido registrada exitosamente.");
    } catch (IllegalArgumentException e) {
      Modal.displayError(e.getMessage());
    } catch (SQLException e) {
      Modal.displayError("No ha sido posible registrar a la mascota debido a un error en la base de datos" + e.getMessage());
    }
  }

  public static void navigateToRegisterPetPage(Stage currentStage) {
    navigateTo(currentStage, "Registrar Mascota", "RegisterPetPage");
  }
}

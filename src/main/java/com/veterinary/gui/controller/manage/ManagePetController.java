package com.veterinary.gui.controller.manage;

import com.veterinary.business.dao.PetDAO;
import com.veterinary.business.dao.OwnerDAO;
import com.veterinary.business.dto.PetDTO;
import com.veterinary.business.dto.OwnerDTO;
import com.veterinary.gui.Modal;
import com.veterinary.gui.controller.register.RegisterPetController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ManagePetController extends ManageController<PetDTO> {
  private static final PetDAO PET_DAO = new PetDAO();
  private static final OwnerDAO OWNER_DAO = new OwnerDAO();
  @FXML
  private TextField fieldIDPet;
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

  public void initialize(PetDTO currentPet) {
    super.initialize(currentPet);
    RegisterPetController.loadComboBoxSpecies(comboBoxSpecies);
    loadComboBoxOwners();
    loadDataObjectFields();
  }

  private void loadComboBoxOwners() {
    try {
      comboBoxOwner.setItems(FXCollections.observableList(OWNER_DAO.getAll()));
    } catch (SQLException e) {
      Modal.displayError("No se pudieron cargar los dueños debido a un error en el sistema.");
    }
  }

  public void loadDataObjectFields() {
    fieldIDPet.setText(String.valueOf(getCurrentDataObject().getID()));

    try {
      for (OwnerDTO ownerDTO : comboBoxOwner.getItems()) {
        if (ownerDTO.getEmail().equals(getCurrentDataObject().getIDOwner())) {
          comboBoxOwner.setValue(ownerDTO);
          break;
        }
      }

      OwnerDTO owner = OWNER_DAO.getOne(getCurrentDataObject().getIDOwner());
      comboBoxOwner.setValue(owner);
    } catch (SQLException e) {
      Modal.displayError("No ha sido posible cargar el dueño de la mascota debido a un error en el sistema.");
    }

    fieldName.setText(getCurrentDataObject().getName());
    comboBoxSpecies.setValue(getCurrentDataObject().getSpecies());
    fieldBreed.setText(getCurrentDataObject().getBreed());
    fieldColour.setText(getCurrentDataObject().getColour());
    fieldWeight.setText(String.valueOf(getCurrentDataObject().getWeight()));
    fieldHeight.setText(String.valueOf(getCurrentDataObject().getHeight()));
    datePickerBirthDate.setValue(getCurrentDataObject().getBirthDate().toLocalDate());
  }

  @Override
  public void handleUpdate() {
    try {
      PetDTO updatedPet = new PetDTO.PetBuilder()
        .setID(getCurrentDataObject().getID())
        .setIDOwner(comboBoxOwner.getValue().getEmail())
        .setName(fieldName.getText())
        .setSpecies(comboBoxSpecies.getValue())
        .setBreed(fieldBreed.getText())
        .setColour(fieldColour.getText())
        .setWeight(Float.parseFloat(fieldWeight.getText()))
        .setHeight(Float.parseFloat(fieldHeight.getText()))
        .setBirthDate(datePickerBirthDate.getValue().atStartOfDay())
        .build();

      PET_DAO.updateOne(updatedPet);
      Modal.displaySuccess("La mascota ha sido actualizada exitosamente.");
    } catch (IllegalArgumentException e) {
      Modal.displayError(e.getMessage());
    } catch (SQLException e) {
      Modal.displayError("No ha sido posible actualizar la mascota debido a un error en el sistema.");
    }
  }
}
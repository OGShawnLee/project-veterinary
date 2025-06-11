package com.veterinary.gui.controller.manage;

import com.veterinary.business.dao.SicknessDAO;
import com.veterinary.business.dto.SicknessDTO;
import com.veterinary.gui.Modal;
import com.veterinary.gui.controller.register.RegisterSicknessController;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ManageSicknessController extends ManageController<SicknessDTO> {
  private static final SicknessDAO SICKNESS_DAO = new SicknessDAO();
  @FXML
  private TextField fieldIDSickness;
  @FXML
  private TextField fieldName;
  @FXML
  private TextField fieldDescription;
  @FXML
  private ComboBox<SicknessDTO.Species> comboBoxSpecies;
  @FXML
  private ComboBox<SicknessDTO.DangerLevel> comboBoxDangerLevel;

  public void initialize(SicknessDTO currentSickness) {
    super.initialize(currentSickness);
    RegisterSicknessController.loadComboBoxSpecies(comboBoxSpecies);
    RegisterSicknessController.loadComboBoxDangerLevel(comboBoxDangerLevel);
    loadDataObjectFields();
  }

  public void loadDataObjectFields() {
    fieldIDSickness.setText(String.valueOf(getCurrentDataObject().getID()));
    fieldName.setText(getCurrentDataObject().getName());
    fieldDescription.setText(getCurrentDataObject().getDescription());
    comboBoxSpecies.setValue(getCurrentDataObject().getSpecies());
    comboBoxDangerLevel.setValue(getCurrentDataObject().getDangerLevel());
  }

  @Override
  public void handleUpdate() {
    try {
      SicknessDTO updatedSickness = new SicknessDTO.SicknessBuilder()
        .setID(Integer.parseInt(fieldIDSickness.getText()))
        .setName(fieldName.getText())
        .setDescription(fieldDescription.getText())
        .setSpecies(comboBoxSpecies.getValue())
        .setDangerLevel(comboBoxDangerLevel.getValue())
        .build();

      SICKNESS_DAO.updateOne(updatedSickness);
      Modal.displaySuccess("La enfermedad ha sido actualizada exitosamente.");
    } catch (IllegalArgumentException e) {
      Modal.displayError(e.getMessage());
    } catch (SQLException e) {
      Modal.displayError("No ha sido posible actualizar la enfermedad debido a un error en el sistema.");
    }
  }
}
package com.veterinary.gui.controller.register;

import com.veterinary.business.dao.SicknessDAO;
import com.veterinary.business.dto.SicknessDTO;
import com.veterinary.gui.Modal;
import com.veterinary.gui.controller.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class RegisterSicknessController extends Controller {
  private static final SicknessDAO SICKNESS_DAO = new SicknessDAO();
  @FXML
  private TextField fieldName;
  @FXML
  private TextField fieldDescription;
  @FXML
  private ComboBox<SicknessDTO.Species> fieldSpecies;
  @FXML
  private ComboBox<SicknessDTO.DangerLevel> fieldDangerLevel;

  public void initialize() {
    loadComboBoxSpecies(fieldSpecies);
    loadComboBoxDangerLevel(fieldDangerLevel);
  }

  public static void loadComboBoxSpecies(ComboBox<SicknessDTO.Species> fieldSpecies) {
    fieldSpecies.getItems().addAll(SicknessDTO.Species.values());
    fieldSpecies.setValue(SicknessDTO.Species.DOG);
  }

  public static void loadComboBoxDangerLevel(ComboBox<SicknessDTO.DangerLevel> fieldDangerLevel) {
    fieldDangerLevel.getItems().addAll(SicknessDTO.DangerLevel.values());
    fieldDangerLevel.setValue(SicknessDTO.DangerLevel.LOW);
  }

  @FXML
  public void handleRegister() {
    try {
      SicknessDTO sicknessDTO = new SicknessDTO.SicknessBuilder()
        .setName(fieldName.getText())
        .setDescription(fieldDescription.getText())
        .setSpecies(fieldSpecies.getValue())
        .setDangerLevel(fieldDangerLevel.getValue())
        .build();

      SICKNESS_DAO.createOne(sicknessDTO);
      Modal.displaySuccess("La enfermedad ha sido registrada exitosamente.");
    } catch (IllegalArgumentException e) {
      Modal.displayError(e.getMessage());
    } catch (SQLException e) {
      Modal.displayError("No ha sido posible registrar la enfermedad debido a un error en la base de datos.");
    }
  }
}
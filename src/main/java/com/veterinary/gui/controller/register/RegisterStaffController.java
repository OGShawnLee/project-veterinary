package com.veterinary.gui.controller.register;

import com.veterinary.business.dao.StaffDAO;
import com.veterinary.business.dto.Account;
import com.veterinary.business.dto.StaffDTO;
import com.veterinary.gui.Modal;
import com.veterinary.gui.controller.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class RegisterStaffController extends Controller {
  private static final StaffDAO STAFF_DAO = new StaffDAO();
  @FXML
  private TextField fieldDisplayName;
  @FXML
  private TextField fieldName;
  @FXML
  private TextField fieldPaternalLastName;
  @FXML
  private TextField fieldMaternalLastName;
  @FXML
  private TextField fieldStreet;
  @FXML
  private TextField fieldColony;
  @FXML
  private TextField fieldNumber;
  @FXML
  private TextField fieldEmail;
  @FXML
  private TextField fieldPhoneNumber;
  @FXML
  private PasswordField fieldEncryptedPassword;
  @FXML
  private ComboBox<StaffDTO.Role> comboBoxRole;

  public void initialize() {
    loadComboBoxRole(comboBoxRole);
  }

  public static void loadComboBoxRole(ComboBox<StaffDTO.Role> comboBoxRole) {
    comboBoxRole.getItems().addAll(StaffDTO.Role.values());
    comboBoxRole.setValue(StaffDTO.Role.SECRETARY);
  }

  public void handleRegister() {
    try {
      StaffDTO staffDTO = new StaffDTO.StaffBuilder()
        .setDisplayName(fieldDisplayName.getText())
        .setName(fieldName.getText())
        .setPaternalLastName(fieldPaternalLastName.getText())
        .setMaternalLastName(fieldMaternalLastName.getText())
        .setStreet(fieldStreet.getText())
        .setColony(fieldColony.getText())
        .setNumber(Integer.parseInt(fieldNumber.getText()))
        .setPhoneNumber(fieldPhoneNumber.getText())
        .setRole(comboBoxRole.getValue())
        .build();

      Account account = new Account(
        fieldEmail.getText(),
        fieldDisplayName.getText(),
        fieldEncryptedPassword.getText(),
        Account.Role.fromStaffRole(comboBoxRole.getValue())
      );

      STAFF_DAO.createStaff(staffDTO, account);
      Modal.displaySuccess("El personal ha sido registrado exitosamente.");
    } catch (IllegalArgumentException e) {
      Modal.displayError(e.getMessage());
    } catch (SQLException e) {
      Modal.displayError("No ha sido posible registrar al personal debido a un error en la base de datos: " + e.getMessage());
    }
  }
}
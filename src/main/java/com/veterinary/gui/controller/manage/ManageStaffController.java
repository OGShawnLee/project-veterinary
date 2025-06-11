package com.veterinary.gui.controller.manage;

import com.veterinary.business.dao.StaffDAO;
import com.veterinary.business.dto.StaffDTO;
import com.veterinary.gui.Modal;

import com.veterinary.gui.controller.register.RegisterStaffController;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ManageStaffController extends ManageController<StaffDTO> {
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
  private TextField fieldPhoneNumber;
  @FXML
  private ComboBox<StaffDTO.Role> comboBoxRole;

  @Override
  public void initialize(StaffDTO currentStaff) {
    super.initialize(currentStaff);
    RegisterStaffController.loadComboBoxRole(comboBoxRole);
    loadDataObjectFields();
  }

  public void loadDataObjectFields() {
    fieldDisplayName.setText(getCurrentDataObject().getDisplayName());
    fieldName.setText(getCurrentDataObject().getName());
    fieldPaternalLastName.setText(getCurrentDataObject().getPaternalLastName());
    fieldMaternalLastName.setText(getCurrentDataObject().getMaternalLastName());
    fieldStreet.setText(getCurrentDataObject().getStreet());
    fieldColony.setText(getCurrentDataObject().getColony());
    fieldNumber.setText(String.valueOf(getCurrentDataObject().getNumber()));
    fieldPhoneNumber.setText(getCurrentDataObject().getPhoneNumber());
    comboBoxRole.setValue(getCurrentDataObject().getRole());
  }

  @Override
  public void handleUpdate() {
    try {
      StaffDTO updatedStaff = new StaffDTO.StaffBuilder()
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

      STAFF_DAO.updateOne(updatedStaff);
      Modal.displaySuccess("El personal ha sido actualizado exitosamente.");
    } catch (IllegalArgumentException e) {
      Modal.displayError(e.getMessage());
    } catch (SQLException e) {
      Modal.displayError("No ha sido posible actualizar el personal debido a un error en el sistema.");
    }
  }
}
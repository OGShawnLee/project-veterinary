package com.veterinary.gui.controller.manage;

import com.veterinary.business.dao.OwnerDAO;
import com.veterinary.business.dto.OwnerDTO;
import com.veterinary.gui.Modal;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ManageOwnerController extends ManageController<OwnerDTO> {
  private static final OwnerDAO OWNER_DAO = new OwnerDAO();
  @FXML
  private TextField fieldName;
  @FXML
  private TextField fieldPaternalLastName;
  @FXML
  private TextField fieldMaternalLastName;
  @FXML
  private TextField fieldEmail;
  @FXML
  private TextField fieldStreet;
  @FXML
  private TextField fieldColony;
  @FXML
  private TextField fieldNumber;

  @Override
  public void initialize(OwnerDTO currentOwner) {
    super.initialize(currentOwner);
    loadDataObjectFields();
  }

  public void loadDataObjectFields() {
    fieldName.setText(getCurrentDataObject().getName());
    fieldPaternalLastName.setText(getCurrentDataObject().getPaternalLastName());
    fieldMaternalLastName.setText(getCurrentDataObject().getMaternalLastName());
    fieldEmail.setText(getCurrentDataObject().getEmail());
    fieldStreet.setText(getCurrentDataObject().getStreet());
    fieldColony.setText(getCurrentDataObject().getColony());
    fieldNumber.setText(String.valueOf(getCurrentDataObject().getNumber()));
  }

  @Override
  public void handleUpdate() {
    try {
      OwnerDTO updatedOwner = new OwnerDTO.OwnerBuilder()
        .setName(fieldName.getText())
        .setPaternalLastName(fieldPaternalLastName.getText())
        .setMaternalLastName(fieldMaternalLastName.getText())
        .setEmail(fieldEmail.getText())
        .setStreet(fieldStreet.getText())
        .setColony(fieldColony.getText())
        .setNumber(Integer.parseInt(fieldNumber.getText()))
        .build();

      OWNER_DAO.updateOne(updatedOwner);
      Modal.displaySuccess("El propietario ha sido actualizado exitosamente.");
    } catch (IllegalArgumentException e) {
      Modal.displayError(e.getMessage());
    } catch (SQLException e) {
      Modal.displayError("No ha sido posible actualizar el propietario debido a un error en el sistema.");
    }
  }
}
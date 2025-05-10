package com.veterinary.gui.controller;

import com.veterinary.business.dto.OwnerDTO;
import com.veterinary.business.dao.OwnerDAO;
import com.veterinary.gui.Modal;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class RegisterOwnerController extends Controller {
  private final OwnerDAO OWNER_DAO = new OwnerDAO();
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

  public void handleRegisterOwner() {
    try {
      OwnerDTO dataObject = new OwnerDTO.OwnerBuilder()
        .setName(fieldName.getText())
        .setPaternalLastName(fieldPaternalLastName.getText())
        .setMaternalLastName(fieldMaternalLastName.getText())
        .setStreet(fieldStreet.getText())
        .setColony(fieldColony.getText())
        .setNumber(Integer.parseInt(fieldNumber.getText()))
        .setEmail(fieldEmail.getText())
        .build();

      OWNER_DAO.createOne(dataObject);
      Modal.displaySuccess("Dueño ha sido creado con éxito.");
    } catch (IllegalArgumentException e) {
      Modal.displayError(e.getMessage());
    } catch (SQLException e) {
      Modal.displayError("No ha sido posible registrar el dueño debido a un error en la base de datos");
    }
  }

  public static void navigateToRegisterOwnerPage(Stage currentStage) {
    navigateTo(currentStage, "Registrar Dueño", "RegisterOwnerPage");
  }
}

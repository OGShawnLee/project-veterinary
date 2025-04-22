package com.veterinary.gui.controller;

import com.veterinary.business.Result;
import com.veterinary.business.Validator;
import com.veterinary.business.dto.OwnerDTO;
import com.veterinary.db.dao.OwnerDAO;
import com.veterinary.gui.Modal;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class RegisterOwnerController {
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

  public void handleRegisterOwner(ActionEvent event) {
    Result<Integer> resultInt = Validator.getPositiveInteger(
      fieldNumber.getText(),
      "El número no puede estar vacío o ser menor a 1"
    );

    if (resultInt.isFailure()) {
      Modal.displayError(resultInt.getError());
      return;
    }

    OwnerDTO dataObject = new OwnerDTO.OwnerBuilder()
      .setName(fieldName.getText())
      .setPaternalLastName(fieldPaternalLastName.getText())
      .setMaternalLastName(fieldMaternalLastName.getText())
      .setStreet(fieldStreet.getText())
      .setColony(fieldColony.getText())
      .setNumber(Integer.parseInt(fieldNumber.getText()))
      .setEmail(fieldEmail.getText())
      .build();

    Result<String> resultString = Validator.getFilledString(
      dataObject.getName(),
      "El nombre no puede estar vacío o exceder 50 caracteres",
      32
    );

    if (resultString.isFailure()) {
      Modal.displayError(resultString.getError());
      return;
    }

    resultString = Validator.getFilledString(
      dataObject.getPaternalLastName(),
      "El apellido paterno no puede estar vacío o exceder 50 caracteres",
      32
    );

    if (resultString.isFailure()) {
      Modal.displayError(resultString.getError());
      return;
    }

    resultString = Validator.getFilledString(
      dataObject.getMaternalLastName(),
      "El apellido materno no puede estar vacío o exceder 50 caracteres",
      32
    );

    if (resultString.isFailure()) {
      Modal.displayError(resultString.getError());
      return;
    }

    resultString = Validator.getFilledString(
      dataObject.getStreet(),
      "La calle no puede estar vacía o exceder 50 caracteres",
      32
    );

    if (resultString.isFailure()) {
      Modal.displayError(resultString.getError());
      return;
    }

    resultString = Validator.getFilledString(
      dataObject.getColony(),
      "La colonia no puede estar vacía o exceder 50 caracteres",
      32
    );

    if (resultString.isFailure()) {
      Modal.displayError(resultString.getError());
      return;
    }

    resultString = Validator.getEmail(
      dataObject.getEmail(),
      "El correo electrónico no puede estar vacío o exceder 50 caracteres",
      128
    );

    if (resultString.isFailure()) {
      Modal.displayError(resultString.getError());
      return;
    }

    try {
      OWNER_DAO.createOne(dataObject);
      Modal.displaySuccess("Dueño ha sido creado con éxito.");
    } catch (Exception e) {
      Modal.displayError("No ha sido posible registrar el dueño debido a un error en el sistema");
    }
  }
}

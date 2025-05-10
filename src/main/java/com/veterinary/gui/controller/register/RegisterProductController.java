package com.veterinary.gui.controller.register;

import com.veterinary.business.dao.ProductDAO;
import com.veterinary.business.dto.ProductDTO;
import com.veterinary.gui.Modal;
import com.veterinary.gui.controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class RegisterProductController extends Controller {
  private final ProductDAO PRODUCT_DAO = new ProductDAO();
  @FXML
  private TextField fieldName;
  @FXML
  private TextField fieldDescription;
  @FXML
  private ComboBox<String> fieldKind;
  @FXML
  private TextField fieldStock;
  @FXML
  private ComboBox<String> fieldSpecies;
  @FXML
  private TextField fieldPrice;
  @FXML
  private TextField fieldBrand;

  public void initialize() {
    fieldKind.getItems().addAll("Accesorio", "Comida", "Medicamento");
    fieldSpecies.getItems().addAll("Gato", "Perro", "Ambos");
  }

  public void handleRegisterProduct() {
    try {
      ProductDTO dataObject = new ProductDTO.ProductBuilder()
        .setName(fieldName.getText())
        .setDescription(fieldDescription.getText())
        .setKind(fieldKind.getValue())
        .setStock(fieldStock.getText())
        .setSpecies(fieldSpecies.getValue())
        .setPrice(Float.parseFloat(fieldPrice.getText()))
        .setBrand(fieldBrand.getText())
        .build();

      PRODUCT_DAO.createOne(dataObject);
      Modal.displaySuccess("Producto ha sido creado con éxito.");
    } catch (IllegalArgumentException e) {
      Modal.displayError(e.getMessage());
    } catch (SQLException e) {
      Modal.displayError("No ha sido posible registrar el producto debido a un error de sistema.");
    }
  }

  public static void navigateToRegisterProductPage(Stage currentStage) {
    Controller.navigateTo(currentStage, "Registrar producto", "RegisterProductPage");
  }
}

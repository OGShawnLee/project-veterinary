package com.veterinary.gui.controller.manage;

import com.veterinary.business.dao.ProductDAO;
import com.veterinary.business.dto.BothSpecies;
import com.veterinary.business.dto.ProductDTO;
import com.veterinary.gui.Modal;

import com.veterinary.gui.controller.register.RegisterProductController;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ManageProductController extends ManageController<ProductDTO> {
  private static final ProductDAO PRODUCT_DAO = new ProductDAO();
  @FXML
  private TextField fieldID;
  @FXML
  private TextField fieldName;
  @FXML
  private TextField fieldDescription;
  @FXML
  private ComboBox<ProductDTO.Kind> comboBoxKind;
  @FXML
  private TextField fieldStock;
  @FXML
  private ComboBox<BothSpecies> comboBoxSpecies;
  @FXML
  private TextField fieldBrand;
  @FXML
  private TextField fieldPrice;

  @Override
  public void initialize(ProductDTO currentProduct) {
    super.initialize(currentProduct);
    RegisterProductController.loadComboBoxSpecies(comboBoxSpecies);
    RegisterProductController.loadComboBoxSpecies(comboBoxSpecies);
    loadDataObjectFields();
  }

  public void loadDataObjectFields() {
    fieldID.setText(String.valueOf(getCurrentDataObject().getID()));
    fieldName.setText(getCurrentDataObject().getName());
    fieldDescription.setText(getCurrentDataObject().getDescription());
    comboBoxKind.setValue(getCurrentDataObject().getKind());
    fieldStock.setText(String.valueOf(getCurrentDataObject().getStock()));
    comboBoxSpecies.setValue(getCurrentDataObject().getSpecies());
    fieldPrice.setText(String.valueOf(getCurrentDataObject().getPrice()));
    fieldBrand.setText(getCurrentDataObject().getBrand());
  }

  @Override
  public void handleUpdate() {
    try {
      ProductDTO updatedProduct = new ProductDTO.ProductBuilder()
        .setID(getCurrentDataObject().getID())
        .setName(fieldName.getText())
        .setKind(comboBoxKind.getValue())
        .setDescription(fieldDescription.getText())
        .setSpecies(comboBoxSpecies.getValue())
        .setPrice(Float.parseFloat(fieldPrice.getText()))
        .setStock(fieldStock.getText())
        .setBrand(fieldBrand.getText())
        .build();

      PRODUCT_DAO.updateOne(updatedProduct);
      Modal.displaySuccess("El producto ha sido actualizado exitosamente.");
    } catch (IllegalArgumentException e) {
      Modal.displayError(e.getMessage());
    } catch (SQLException e) {
      Modal.displayError("No ha sido posible actualizar el producto debido a un error en el sistema.");
    }
  }
}
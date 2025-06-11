package com.veterinary.gui.controller;

import com.veterinary.business.dao.ProductDAO;
import com.veterinary.business.dao.OwnerDAO;
import com.veterinary.business.dto.OwnerDTO;
import com.veterinary.business.dto.ProductDTO;
import com.veterinary.gui.Modal;
import com.veterinary.gui.controller.manage.ManageController;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class BuyProductController extends ManageController<ProductDTO> {
  private static final ProductDAO PRODUCT_DAO = new ProductDAO();
  private static final OwnerDAO OWNER_DAO = new OwnerDAO();

  @FXML
  private TextField fieldID;
  @FXML
  private TextField fieldName;
  @FXML
  private TextField fieldDescription;
  @FXML
  private TextField fieldKind;
  @FXML
  private TextField fieldStock;
  @FXML
  private TextField fieldSpecies;
  @FXML
  private TextField fieldPrice;
  @FXML
  private TextField fieldBrand;
  @FXML
  private ComboBox<OwnerDTO> comboBoxOwner;
  @FXML
  private TextField fieldBuyQuantity;

  @Override
  public void initialize(ProductDTO currentProduct) {
    super.initialize(currentProduct);
    loadComboBoxOwners();
    loadDataObjectFields();
  }

  private void loadComboBoxOwners() {
    try {
      comboBoxOwner.getItems().setAll(OWNER_DAO.getAll());

      if (comboBoxOwner.getItems().isEmpty()) {
        Modal.displayError("No hay clientes registrados. Por favor, registre un cliente antes de comprar un producto.");
        return;
      }

      comboBoxOwner.setValue(comboBoxOwner.getItems().get(0));
    } catch (SQLException e) {
      Modal.displayError("No se pudieron cargar los clientes.");
    }
  }

  public void loadDataObjectFields() {
    fieldID.setText(String.valueOf(getCurrentDataObject().getID()));
    fieldName.setText(getCurrentDataObject().getName());
    fieldDescription.setText(getCurrentDataObject().getDescription());
    fieldKind.setText(getCurrentDataObject().getKind().toString());
    fieldStock.setText(String.valueOf(getCurrentDataObject().getStock()));
    fieldSpecies.setText(getCurrentDataObject().getSpecies().toString());
    fieldPrice.setText(String.valueOf(getCurrentDataObject().getPrice()));
    fieldBrand.setText(getCurrentDataObject().getBrand());
  }

  public void handleUpdate() {
    try {
      OwnerDTO selectedClient = comboBoxOwner.getValue();
      if (selectedClient == null) {
        Modal.displayError("Debe seleccionar un cliente.");
        return;
      }

      PRODUCT_DAO.buyProduct(
        getCurrentDataObject().getID(),
        selectedClient.getEmail(),
        Integer.parseInt(fieldBuyQuantity.getText())
      );

      Modal.displaySuccess("El producto ha sido comprado exitosamente.");
    } catch (SQLException e) {
      Modal.displayError("No se pudo completar la compra debido a un error en el sistema.");
    }
  }
}
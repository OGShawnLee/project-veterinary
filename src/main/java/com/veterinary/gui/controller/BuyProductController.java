package com.veterinary.gui.controller;

import com.veterinary.business.Validator;
import com.veterinary.business.dao.OwnerDAO;
import com.veterinary.business.dao.ProductDAO;
import com.veterinary.business.dto.OwnerDTO;
import com.veterinary.business.dto.ProductDTO;
import com.veterinary.gui.Modal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class BuyProductController extends Controller {
  private static final ProductDAO PRODUCT_DAO = new ProductDAO();
  private static final OwnerDAO OWNER_DAO = new OwnerDAO();
  @FXML
  private TableView<ProductDTO> tableProduct;
  @FXML
  private TableColumn<ProductDTO, Integer> columnID;
  @FXML
  private TableColumn<ProductDTO, String> columnName;
  @FXML
  private TableColumn<ProductDTO, String> columnDescription;
  @FXML
  private TableColumn<ProductDTO, String> columnKind;
  @FXML
  private TableColumn<ProductDTO, Integer> columnStock;
  @FXML
  private TableColumn<ProductDTO, String> columnSpecies;
  @FXML
  private TableColumn<ProductDTO, Float> columnPrice;
  @FXML
  private TableColumn<ProductDTO, String> columnBrand;
  @FXML
  private TextField fieldQuantity;
  @FXML
  private TextField fieldEmail;
  private ProductDTO selectedProduct;

  public void initialize() {
    configureProductTable();
    loadProductList();
    tableProduct.setOnMouseClicked(event -> {
      if (event.getClickCount() == 2) {
        selectedProduct = tableProduct.getSelectionModel().getSelectedItem();
      }
    });
  }

  private void configureProductTable() {
    columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
    columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    columnKind.setCellValueFactory(new PropertyValueFactory<>("kind"));
    columnStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
    columnSpecies.setCellValueFactory(new PropertyValueFactory<>("species"));
    columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    columnBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
  }

  private void loadProductList() {
    try {
      List<ProductDTO> productList = PRODUCT_DAO.getAll();
      ObservableList<ProductDTO> observableListProduct = FXCollections.observableArrayList(productList);
      tableProduct.setItems(observableListProduct);
    } catch (SQLException e) {
      Modal.displayError(
        "No ha sido posible cargar la lista de productos debido a un error de sistema." + e.getMessage()
      );
    }
  }


  public void handleBuyProduct() {
    if (selectedProduct == null) {
      Modal.displayError("Por favor selecciona un producto al hacer doble click sobre una fila.");
      return;
    }

    try {
      int quantity = Validator.getValidPositiveInteger(fieldQuantity.getText());
      String email = Validator.getValidEmail(fieldEmail.getText());

      OwnerDTO dataObjectOwner = OWNER_DAO.getOne(email);

      if (dataObjectOwner == null) {
        throw new IllegalArgumentException("El dueño no existe.");
      }

      PRODUCT_DAO.buyProduct(selectedProduct, email, quantity);
      loadProductList();
      Modal.displaySuccess("Producto comprado con éxito.");
    } catch (IllegalArgumentException e) {
      Modal.displayError(e.getMessage());
    } catch (SQLException e) {
      Modal.displayError("No ha sido posible comprar el producto debido a un error en la base de datos." + e.getMessage());
    }
  }

  public static void navigateToBuyProductPage(Stage currentStage) {
    navigateTo(currentStage, "Comprar Producto", "BuyProductPage");
  }
}
package com.veterinary.gui.controller.review;

import com.veterinary.business.dao.ProductDAO;
import com.veterinary.business.dto.ProductDTO;
import com.veterinary.gui.Modal;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class ReviewProductListController extends ReviewListController {
  private static final ProductDAO PRODUCT_DAO = new ProductDAO();
  @FXML
  private TableView<ProductDTO> tableProduct;
  @FXML
  private TableColumn<ProductDTO, Integer> columnID;
  @FXML
  private TableColumn<ProductDTO, String> columnName;
  @FXML
  private TableColumn<ProductDTO, Double> columnPrice;
  @FXML
  private TableColumn<ProductDTO, Integer> columnStock;
  @FXML
  private TableColumn<ProductDTO, String> columnDescription;
  @FXML
  private TableColumn<ProductDTO, String> columnKind;
  @FXML
  private TableColumn<ProductDTO, String> columnSpecies;
  @FXML
  private TableColumn<ProductDTO, String> columnBrand;


  @Override
  public void initialize() {
    loadTableColumns();
    loadDataList();
  }

  @Override
  public void loadTableColumns() {
    columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
    columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    columnKind.setCellValueFactory(new PropertyValueFactory<>("kind"));
    columnSpecies.setCellValueFactory(new PropertyValueFactory<>("species"));
    columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    columnStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
    columnBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
  }

  @Override
  public void loadDataList() {
    try {
      tableProduct.setItems(
        FXCollections.observableList(
          PRODUCT_DAO.getAll()
        )
      );
    } catch (SQLException e) {
      Modal.displayError(
        "No ha sido posible cargar la lista de productos debido a un error en el sistema."
      );
    }
  }

  public void handleOpenRegister() {
    Modal.display(
      "Registrar Producto",
      "RegisterProductModal",
      this::loadDataList
    );
  }

  public void handleManage() {
    ProductDTO selectedProduct = tableProduct.getSelectionModel().getSelectedItem();

    if (selectedProduct == null) return;

    Modal.displayManageModal(
      "Gestionar Producto",
      "ManageProductModal",
      this::loadDataList,
      selectedProduct
    );
  }
}
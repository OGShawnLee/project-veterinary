package com.veterinary.gui.controller.review;

import com.veterinary.business.dao.SaleDAO;
import com.veterinary.business.dto.SaleDTO;
import com.veterinary.gui.Modal;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class ReviewSaleListController extends ReviewListController {
  private static final SaleDAO SALE_DAO = new SaleDAO();
  @FXML
  private TableView<SaleDTO> tableSale;
  @FXML
  private TableColumn<SaleDTO, Integer> columnIDSale;
  @FXML
  private TableColumn<SaleDTO, Integer> columnIDProduct;
  @FXML
  private TableColumn<SaleDTO, String> columnIDOwner;
  @FXML
  private TableColumn<SaleDTO, String> columnBoughtAt;
  @FXML
  private TableColumn<SaleDTO, Integer> columnQuantity;

  @FXML
  public void initialize() {
    loadTableColumns();
    loadDataList();
  }

  @Override
  public void loadTableColumns() {
    columnIDSale.setCellValueFactory(new PropertyValueFactory<>("IDSale"));
    columnIDProduct.setCellValueFactory(new PropertyValueFactory<>("IDProduct"));
    columnIDOwner.setCellValueFactory(new PropertyValueFactory<>("IDOwner"));
    columnBoughtAt.setCellValueFactory(new PropertyValueFactory<>("boughtAt"));
    columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
  }

  @Override
  public void loadDataList() {
    try {
      tableSale.getItems().setAll(SALE_DAO.getAll());
    } catch (SQLException e) {
      Modal.displayError("No se pudieron cargar las ventas.");
    }
  }
}
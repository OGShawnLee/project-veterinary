package com.veterinary.gui.controller.review;

import com.veterinary.business.dao.OwnerDAO;
import com.veterinary.business.dto.OwnerDTO;
import com.veterinary.gui.Modal;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class ReviewOwnerListController extends ReviewListController {
  private static final OwnerDAO OWNER_DAO = new OwnerDAO();

  @FXML
  private TableView<OwnerDTO> tableOwner;
  @FXML
  private TableColumn<OwnerDTO, String> columnName;
  @FXML
  private TableColumn<OwnerDTO, String> columnPaternalLastName;
  @FXML
  private TableColumn<OwnerDTO, String> columnMaternalLastName;
  @FXML
  private TableColumn<OwnerDTO, String> columnStreet;
  @FXML
  private TableColumn<OwnerDTO, String> columnColony;
  @FXML
  private TableColumn<OwnerDTO, String> columnNumber;
  @FXML
  private TableColumn<OwnerDTO, String> columnEmail;

  @Override
  public void initialize() {
    loadTableColumns();
    loadDataList();
  }

  @Override
  public void loadTableColumns() {
    columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    columnPaternalLastName.setCellValueFactory(new PropertyValueFactory<>("paternalLastName"));
    columnMaternalLastName.setCellValueFactory(new PropertyValueFactory<>("maternalLastName"));
    columnStreet.setCellValueFactory(new PropertyValueFactory<>("street"));
    columnColony.setCellValueFactory(new PropertyValueFactory<>("colony"));
    columnNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
  }

  @Override
  public void loadDataList() {
    try {
      tableOwner.setItems(
        FXCollections.observableList(
          OWNER_DAO.getAll()
        )
      );
    } catch (SQLException e) {
      Modal.displayError(
        "No ha sido posible cargar la lista de propietarios debido a un error en el sistema."
      );
    }
  }

  public void handleOpenRegister() {
    Modal.display(
      "Registrar Propietario",
      "RegisterOwnerModal",
      this::loadDataList
    );
  }

  public void handleManage() {
    OwnerDTO selectedOwner = tableOwner.getSelectionModel().getSelectedItem();

    if (selectedOwner == null) return;

    Modal.displayManageModal(
      "Gestionar Propietario",
      "ManageOwnerModal",
      this::loadDataList,
      selectedOwner
    );
  }
}
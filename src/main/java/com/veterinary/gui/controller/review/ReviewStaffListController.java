package com.veterinary.gui.controller.review;

import com.veterinary.business.dao.StaffDAO;
import com.veterinary.business.dto.StaffDTO;
import com.veterinary.gui.Modal;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class ReviewStaffListController extends ReviewListController {
  private static final StaffDAO STAFF_DAO = new StaffDAO();
  @FXML
  private TableView<StaffDTO> tableStaff;
  @FXML
  private TableColumn<StaffDTO, String> columnDisplayName;
  @FXML
  private TableColumn<StaffDTO, String> columnName;
  @FXML
  private TableColumn<StaffDTO, String> columnPaternalLastName;
  @FXML
  private TableColumn<StaffDTO, String> columnMaternalLastName;
  @FXML
  private TableColumn<StaffDTO, String> columnStreet;
  @FXML
  private TableColumn<StaffDTO, String> columnColony;
  @FXML
  private TableColumn<StaffDTO, String> columnNumber;
  @FXML
  private TableColumn<StaffDTO, String> columnPhoneNumber;
  @FXML
  private TableColumn<StaffDTO, StaffDTO.Role> columnRole;

  @Override
  public void initialize() {
    loadTableColumns();
    loadDataList();
  }

  @Override
  public void loadTableColumns() {
    columnDisplayName.setCellValueFactory(new PropertyValueFactory<>("displayName"));
    columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    columnPaternalLastName.setCellValueFactory(new PropertyValueFactory<>("paternalLastName"));
    columnMaternalLastName.setCellValueFactory(new PropertyValueFactory<>("maternalLastName"));
    columnStreet.setCellValueFactory(new PropertyValueFactory<>("street"));
    columnColony.setCellValueFactory(new PropertyValueFactory<>("colony"));
    columnNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
    columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    columnRole.setCellValueFactory(new PropertyValueFactory<>("role"));
  }

  @Override
  public void loadDataList() {
    try {
      tableStaff.setItems(
        FXCollections.observableList(
          STAFF_DAO.getAll()
        )
      );
    } catch (SQLException e) {
      Modal.displayError(
        "No ha sido posible cargar la lista de personal debido a un error en el sistema."
      );
    }
  }

  public void handleOpenRegister() {
    Modal.display(
      "Registrar Personal",
      "RegisterStaffModal",
      this::loadDataList
    );
  }

  public void handleManage() {
    StaffDTO selectedStaff = tableStaff.getSelectionModel().getSelectedItem();

    if (selectedStaff == null) return;

    Modal.displayManageModal(
      "Gestionar Personal",
      "ManageStaffModal",
      this::loadDataList,
      selectedStaff
    );
  }
}
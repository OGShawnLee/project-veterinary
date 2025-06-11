package com.veterinary.gui.controller.review;

import com.veterinary.business.dao.SicknessDAO;
import com.veterinary.business.dto.SicknessDTO;
import com.veterinary.gui.Modal;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class ReviewSicknessListController extends ReviewListController {
  private static final SicknessDAO SICKNESS_DAO = new SicknessDAO();
  @FXML
  private TableView<SicknessDTO> tableSickness;
  @FXML
  private TableColumn<SicknessDTO, Integer> columnID;
  @FXML
  private TableColumn<SicknessDTO, String> columnName;
  @FXML
  private TableColumn<SicknessDTO, String> columnDescription;
  @FXML
  private TableColumn<SicknessDTO, SicknessDTO.Species> columnSpecies;
  @FXML
  private TableColumn<SicknessDTO, SicknessDTO.DangerLevel> columnDangerLevel;

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
    columnSpecies.setCellValueFactory(new PropertyValueFactory<>("species"));
    columnDangerLevel.setCellValueFactory(new PropertyValueFactory<>("dangerLevel"));
  }

  @Override
  public void loadDataList() {
    try {
      tableSickness.setItems(
        FXCollections.observableList(
          SICKNESS_DAO.getAll()
        )
      );
    } catch (SQLException e) {
      Modal.displayError(
        "No ha sido posible cargar la lista de enfermedades debido a un error en el sistema."
      );
    }
  }

  public void handleOpenRegister() {
    Modal.display(
      "Registrar Enfermedad",
      "RegisterSicknessModal",
      this::loadDataList
    );
  }

  public void handleManage() {
    SicknessDTO selectedSickness = tableSickness.getSelectionModel().getSelectedItem();

    if (selectedSickness == null) return;

    Modal.displayManageModal(
      "Gestionar Enfermedad",
      "ManageSicknessModal",
      this::loadDataList,
      selectedSickness
    );
  }
}
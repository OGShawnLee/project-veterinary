package com.veterinary.gui.controller.review;

import com.veterinary.business.dao.PetDAO;
import com.veterinary.business.dto.PetDTO;
import com.veterinary.gui.Modal;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class ReviewPetListController extends ReviewListController {
  private static final PetDAO PET_DAO = new PetDAO();
  @FXML
  private TableView<PetDTO> tablePet;
  @FXML
  private TableColumn<PetDTO, String> columnID;
  @FXML
  private TableColumn<PetDTO, String> columnIDOwner;
  @FXML
  private TableColumn<PetDTO, String> columnName;
  @FXML
  private TableColumn<PetDTO, PetDTO.Species> columnSpecies;
  @FXML
  private TableColumn<PetDTO, String> columnBreed;
  @FXML
  private TableColumn<PetDTO, String> columnColour;
  @FXML
  private TableColumn<PetDTO, Float> columnWeight;
  @FXML
  private TableColumn<PetDTO, Float> columnHeight;
  @FXML
  private TableColumn<PetDTO, String> columnBirthDate;

  @Override
  public void initialize() {
    loadTableColumns();
    loadDataList();
  }

  @Override
  public void loadTableColumns() {
    columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
    columnIDOwner.setCellValueFactory(new PropertyValueFactory<>("IDOwner"));
    columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    columnSpecies.setCellValueFactory(new PropertyValueFactory<>("species"));
    columnBreed.setCellValueFactory(new PropertyValueFactory<>("breed"));
    columnColour.setCellValueFactory(new PropertyValueFactory<>("colour"));
    columnWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
    columnHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
    columnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
  }

  @Override
  public void loadDataList() {
    try {
      tablePet.setItems(
        FXCollections.observableList(
          PET_DAO.getAll()
        )
      );
    } catch (SQLException e) {
      Modal.displayError(
        "No ha sido posible cargar la lista de mascotas debido a un error en el sistema."
      );
    }
  }

  public void handleOpenRegister() {
    Modal.display(
      "Registrar Mascota",
      "RegisterPetModal",
      this::loadDataList
    );
  }

  public void handleManage() {
    PetDTO selectedPet = tablePet.getSelectionModel().getSelectedItem();

    if (selectedPet == null) return;

    Modal.displayManageModal(
      "Gestionar Mascota",
      "ManagePetModal",
      this::loadDataList,
      selectedPet
    );
  }
}
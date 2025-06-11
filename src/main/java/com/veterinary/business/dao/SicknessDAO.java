package com.veterinary.business.dao;

import com.veterinary.business.dto.SicknessDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SicknessDAO extends DAOPattern<SicknessDTO, Integer> {
  private final String CREATE_QUERY =
    "INSERT INTO Sickness (name, description, species, danger_level) VALUES (?, ?, ?, ?)";
  private final String GET_ALL_QUERY =
    "SELECT * FROM Sickness";
  private final String GET_ONE_QUERY =
    "SELECT * FROM Sickness WHERE id_sickness = ?";
  private final String UPDATE_QUERY =
    "UPDATE Sickness SET name = ?, description = ?, species = ?, danger_level = ? WHERE id_sickness = ?";
  private final String DELETE_QUERY =
    "DELETE FROM Sickness WHERE id_sickness = ?";

  @Override
  SicknessDTO createDTOInstanceFromResultSet(ResultSet resultSet) throws SQLException {
    return new SicknessDTO.SicknessBuilder()
      .setID(resultSet.getInt("id_sickness"))
      .setName(resultSet.getString("name"))
      .setDescription(resultSet.getString("description"))
      .setSpecies(SicknessDTO.Species.valueOf(resultSet.getString("species")))
      .setDangerLevel(SicknessDTO.DangerLevel.valueOf(resultSet.getString("danger_level")))
      .build();
  }

  @Override
  public void createOne(SicknessDTO dataObject) throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)
    ) {
      statement.setString(1, dataObject.getName());
      statement.setString(2, dataObject.getDescription());
      statement.setString(3, dataObject.getSpecies().toString());
      statement.setString(4, dataObject.getDangerLevel().toString());
      statement.executeUpdate();
    }
  }

  @Override
  public List<SicknessDTO> getAll() throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY);
      ResultSet resultSet = statement.executeQuery()
    ) {
      List<SicknessDTO> sicknesses = new ArrayList<>();

      while (resultSet.next()) {
        sicknesses.add(createDTOInstanceFromResultSet(resultSet));
      }

      return sicknesses;
    }
  }

  @Override
  public SicknessDTO getOne(Integer id) throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(GET_ONE_QUERY)
    ) {
      statement.setInt(1, id);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        return createDTOInstanceFromResultSet(resultSet);
      } else {
        return null;
      }
    }
  }

  @Override
  public void updateOne(SicknessDTO dataObject) throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)
    ) {
      statement.setString(1, dataObject.getName());
      statement.setString(2, dataObject.getDescription());
      statement.setString(3, dataObject.getSpecies().toString());
      statement.setString(4, dataObject.getDangerLevel().toString());
      statement.setInt(5, dataObject.getID());
      statement.executeUpdate();
    }
  }

  @Override
  public void deleteOne(Integer id) throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)
    ) {
      statement.setInt(1, id);
      statement.executeUpdate();
    }
  }
}
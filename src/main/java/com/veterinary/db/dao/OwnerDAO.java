package com.veterinary.db.dao;

import com.veterinary.business.dto.OwnerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OwnerDAO extends DAOPattern<OwnerDTO, String> {
  private final String CREATE_QUERY =
    "INSERT INTO Owner (email, name, paternal_last_name, maternal_last_name, colony, street, number) VALUES (?, ?, ?, ?, ?, ?, ?)";
  private final String GET_ALL_QUERY =
    "SELECT * FROM Owner";
  private final String GET_ONE_QUERY =
    "SELECT * FROM Owner WHERE id = ?";
  private final String UPDATE_QUERY =
    "UPDATE Owner SET name = ?, paternal_last_name = ?, maternal_last_name = ?, colony = ?, street = ?, number = ? WHERE email = ?";
  private final String DELETE_QUERY =
    "DELETE FROM Owner WHERE email = ?";

  @Override
  protected OwnerDTO createDTOInstanceFromResultSet(ResultSet resultSet) throws SQLException {
    return new OwnerDTO.OwnerBuilder()
      .setEmail(resultSet.getString("email"))
      .setName(resultSet.getString("name"))
      .setPaternalLastName(resultSet.getString("paternal_last_name"))
      .setMaternalLastName(resultSet.getString("maternal_last_name"))
      .setColony(resultSet.getString("colony"))
      .setStreet(resultSet.getString("street"))
      .setNumber(resultSet.getInt("number"))
      .build();
  }

  @Override
  public void createOne(OwnerDTO dataObject) throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)
    ) {
      statement.setString(1, dataObject.getEmail());
      statement.setString(2, dataObject.getName());
      statement.setString(3, dataObject.getPaternalLastName());
      statement.setString(4, dataObject.getMaternalLastName());
      statement.setString(5, dataObject.getColony());
      statement.setString(6, dataObject.getStreet());
      statement.setInt(7, dataObject.getNumber());
      statement.executeUpdate();
    }
  }

  @Override
  public List<OwnerDTO> getAll() throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY);
      ResultSet resultSet = statement.executeQuery()
    ) {
      ArrayList<OwnerDTO> list = new ArrayList<>();

      while (resultSet.next()) {
        list.add(createDTOInstanceFromResultSet(resultSet));
      }

      return list;
    }
  }

  @Override
  public OwnerDTO getOne(String email) throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(GET_ONE_QUERY)
    ) {
      statement.setString(1, email);

      OwnerDTO dataObject = null;

      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          dataObject = createDTOInstanceFromResultSet(resultSet);
        }
      }

      return dataObject;
    }
  }

  @Override
  public void updateOne(OwnerDTO dataObject) throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)
    ) {
      statement.setString(1, dataObject.getName());
      statement.setString(2, dataObject.getPaternalLastName());
      statement.setString(3, dataObject.getMaternalLastName());
      statement.setString(4, dataObject.getColony());
      statement.setString(5, dataObject.getStreet());
      statement.setInt(6, dataObject.getNumber());
      statement.setString(7, dataObject.getEmail());
      statement.executeUpdate();
    }
  }

  @Override
  public void deleteOne(String email) throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)
    ) {
      statement.setString(1, email);
      statement.executeUpdate();
    }
  }
}

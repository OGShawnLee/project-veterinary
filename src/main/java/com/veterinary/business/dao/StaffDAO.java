package com.veterinary.business.dao;

import com.veterinary.business.dto.Account;
import com.veterinary.business.dto.StaffDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO extends DAOPattern<StaffDTO, String> {
  private static final String CREATE_QUERY =
    "CALL create_staff(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  private static final String GET_ALL_QUERY =
    "SELECT * FROM Staff";
  private static final String GET_ONE_QUERY =
    "SELECT * FROM Staff WHERE display_name = ?";
  private static final String UPDATE_QUERY =
    "UPDATE Staff SET name = ?, paternal_last_name = ?, maternal_last_name = ?, " +
    "street = ?, colony = ?, number = ?, phone_number = ?, role = ? WHERE display_name = ?";
  private static final String DELETE_QUERY =
    "DELETE FROM Staff WHERE display_name = ?";

  @Override
  StaffDTO createDTOInstanceFromResultSet(ResultSet resultSet) throws SQLException {
    return new StaffDTO.StaffBuilder()
      .setDisplayName(resultSet.getString("display_name"))
      .setName(resultSet.getString("name"))
      .setPaternalLastName(resultSet.getString("paternal_last_name"))
      .setMaternalLastName(resultSet.getString("maternal_last_name"))
      .setStreet(resultSet.getString("street"))
      .setColony(resultSet.getString("colony"))
      .setNumber(resultSet.getInt("number"))
      .setPhoneNumber(resultSet.getString("phone_number"))
      .setRole(StaffDTO.Role.valueOf(resultSet.getString("role")))
      .build();
  }

  @Override
  public void createOne(StaffDTO dataObject) throws SQLException, UnsupportedOperationException {
    throw new UnsupportedOperationException("Use createStaff method instead.");
  }

  public void createStaff(StaffDTO dataObject, Account account) throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)
    ) {
      statement.setString(1, account.getEmail());
      statement.setString(2, dataObject.getDisplayName());
      statement.setString(3, dataObject.getName());
      statement.setString(4, dataObject.getPaternalLastName());
      statement.setString(5, dataObject.getMaternalLastName());
      statement.setString(6, dataObject.getStreet());
      statement.setString(7, dataObject.getColony());
      statement.setInt(8, dataObject.getNumber());
      statement.setString(9, dataObject.getPhoneNumber());
      statement.setString(10, account.getEmail());
      statement.setString(11, account.getRole().toString());
      statement.executeUpdate();
    }
  }

  @Override
  public List<StaffDTO> getAll() throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY);
      ResultSet resultSet = statement.executeQuery()
    ) {
      List<StaffDTO> staffList = new ArrayList<>();
      while (resultSet.next()) {
        staffList.add(createDTOInstanceFromResultSet(resultSet));
      }
      return staffList;
    }
  }

  @Override
  public StaffDTO getOne(String displayName) throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(GET_ONE_QUERY)
    ) {
      statement.setString(1, displayName);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return createDTOInstanceFromResultSet(resultSet);
        }
      }
    }
    return null;
  }

  @Override
  public void updateOne(StaffDTO dataObject) throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)
    ) {
      statement.setString(1, dataObject.getName());
      statement.setString(2, dataObject.getPaternalLastName());
      statement.setString(3, dataObject.getMaternalLastName());
      statement.setString(4, dataObject.getStreet());
      statement.setString(5, dataObject.getColony());
      statement.setInt(6, dataObject.getNumber());
      statement.setString(7, dataObject.getPhoneNumber());
      statement.setString(8, dataObject.getRole().toString());
      statement.setString(9, dataObject.getDisplayName());
      statement.executeUpdate();
    }
  }

  @Override
  public void deleteOne(String displayName) throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)
    ) {
      statement.setString(1, displayName);
      statement.executeUpdate();
    }
  }
}
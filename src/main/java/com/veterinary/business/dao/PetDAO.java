package com.veterinary.business.dao;

import com.veterinary.business.dto.PetDTO;
import com.veterinary.business.dao.filter.PetFilter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class PetDAO extends DAOPattern<PetDTO, PetFilter> {
  private final String CREATE_QUERY =
    "INSERT INTO Pet (id_owner, name, species, breed, colour, weight, height, birth_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
  private final String GET_ALL_QUERY =
    "SELECT * FROM Pet";
  private final String GET_ONE_QUERY =
    "SELECT * FROM Pet WHERE id_pet = ? AND id_owner = ?";
  private final String UPDATE_QUERY =
    "UPDATE Pet SET name = ?, species = ?, breed = ?, colour = ?, weight = ?, height = ?, birth_date = ? WHERE id_pet = ? AND id_owner = ?";
  private final String DELETE_QUERY =
    "DELETE FROM Pet WHERE id_pet = ? AND id_owner = ?";

  @Override
  PetDTO createDTOInstanceFromResultSet(ResultSet resultSet) throws SQLException {
    return new PetDTO.PetBuilder()
      .setID(resultSet.getString("id_pet"))
      .setIDOwner(resultSet.getString("id_owner"))
      .setName(resultSet.getString("name"))
      .setSpecies(PetDTO.Species.valueOf(resultSet.getString("species")))
      .setBreed(resultSet.getString("breed"))
      .setColour(resultSet.getString("colour"))
      .setWeight(resultSet.getFloat("weight"))
      .setHeight(resultSet.getFloat("height"))
      .setBirthDate(resultSet.getTimestamp("birth_date").toLocalDateTime())
      .build();
  }

  @Override
  public void createOne(PetDTO dataObject) throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)
    ) {
      statement.setString(1, dataObject.getIDOwner());
      statement.setString(2, dataObject.getName());
      statement.setString(3, dataObject.getSpecies().toString());
      statement.setString(4, dataObject.getBreed());
      statement.setString(5, dataObject.getColour());
      statement.setFloat(6, dataObject.getWeight());
      statement.setFloat(7, dataObject.getHeight());
      statement.setTimestamp(8, java.sql.Timestamp.valueOf(dataObject.getBirthDate()));
      statement.executeUpdate();
    }
  }

  @Override
  public List<PetDTO> getAll() throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY);
      ResultSet resultSet = statement.executeQuery()
    ) {
      List<PetDTO> pets = new ArrayList<>();

      while (resultSet.next()) {
        pets.add(createDTOInstanceFromResultSet(resultSet));
      }

      return pets;
    }
  }

  @Override
  public PetDTO getOne(PetFilter filter) throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(GET_ONE_QUERY)
    ) {
      statement.setInt(1, filter.getID());
      statement.setString(2, filter.getIDOwner());
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        return createDTOInstanceFromResultSet(resultSet);
      } else {
        return null;
      }
    }
  }

  @Override
  public void updateOne(PetDTO dataObject) throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)
    ) {
      statement.setString(1, dataObject.getName());
      statement.setString(2, dataObject.getSpecies().toString());
      statement.setString(3, dataObject.getBreed());
      statement.setString(4, dataObject.getColour());
      statement.setFloat(5, dataObject.getWeight());
      statement.setFloat(6, dataObject.getHeight());
      statement.setTimestamp(7, Timestamp.valueOf(dataObject.getBirthDate()));
      statement.setString(8, dataObject.getID());
      statement.setString(9, dataObject.getIDOwner());
      statement.executeUpdate();
    }
  }

  @Override
  public void deleteOne(PetFilter filter) throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)
    ) {
      statement.setInt(1, filter.getID());
      statement.setString(2, filter.getIDOwner());
      statement.executeUpdate();
    }
  }
}

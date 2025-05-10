package com.veterinary.business.dao;

import com.veterinary.business.dto.ProductDTO;
import com.veterinary.db.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductDAO extends DAOPattern<ProductDTO, Integer> {
  private final String CREATE_QUERY =
    "INSERT INTO Product (name, description, kind, stock, species, price, brand) VALUES (?, ?, ?, ?, ?, ?, ?)";

  @Override
  protected ProductDTO createDTOInstanceFromResultSet(ResultSet resultSet) throws SQLException {
    return new ProductDTO.ProductBuilder()
      .setId(resultSet.getInt("id"))
      .setName(resultSet.getString("name"))
      .setDescription(resultSet.getString("description"))
      .setKind(resultSet.getString("kind"))
      .setStock(resultSet.getString("stock"))
      .setSpecies(resultSet.getString("species"))
      .setPrice(resultSet.getFloat("price"))
      .build();
  }

  @Override
  public void createOne(ProductDTO dataObject) throws SQLException {
    try (
      Connection connection = DBConnector.getConnection();
      PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)
    ) {
      statement.setString(1, dataObject.getName());
      statement.setString(2, dataObject.getDescription());
      statement.setString(3, dataObject.getKind());
      statement.setInt(4, dataObject.getStock());
      statement.setString(5, dataObject.getSpecies());
      statement.setFloat(6, dataObject.getPrice());
      statement.setString(7, dataObject.getBrand());
      statement.executeUpdate();
    }
  }

  @Override
  public List<ProductDTO> getAll() throws SQLException {
    // Implementation for retrieving all products from the database
    return null;
  }

  @Override
  public ProductDTO getOne(Integer filter) throws SQLException {
    // Implementation for retrieving a single product by ID from the database
    return null;
  }

  @Override
  public void updateOne(ProductDTO dataObject) throws SQLException {
    // Implementation for updating a product in the database
  }

  @Override
  public void deleteOne(Integer filter) throws SQLException {
    // Implementation for deleting a product from the database
  }
}

package com.veterinary.business.dao;

import com.veterinary.business.dto.BothSpecies;
import com.veterinary.business.dto.ProductDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends DAOPattern<ProductDTO, Integer> {
  private final String CREATE_QUERY =
    "INSERT INTO Product (name, description, kind, stock, species, price, brand) VALUES (?, ?, ?, ?, ?, ?, ?)";
  private final String GET_ALL_QUERY = "SELECT * FROM Product";

  @Override
  ProductDTO createDTOInstanceFromResultSet(ResultSet resultSet) throws SQLException {
    return new ProductDTO.ProductBuilder()
      .setID(resultSet.getInt("id_product"))
      .setName(resultSet.getString("name"))
      .setDescription(resultSet.getString("description"))
      .setKind(ProductDTO.Kind.valueOf(resultSet.getString("kind")))
      .setStock(resultSet.getString("stock"))
      .setSpecies(BothSpecies.valueOf(resultSet.getString("species")))
      .setPrice(resultSet.getFloat("price"))
      .setBrand(resultSet.getString("brand"))
      .build();
  }

  public void buyProduct(ProductDTO dataObject, String emailOwner, int quantity) throws SQLException {
    try (
      Connection connection = getConnection();
    ) {
      connection.setAutoCommit(false);

      try (
        PreparedStatement reduceStockStatement = connection.prepareStatement(
          "UPDATE Product SET stock = stock - ? WHERE id_product = ?"
        );
        PreparedStatement createSaleStatement = connection.prepareStatement(
          "INSERT INTO Sale (id_product, id_owner, quantity) VALUES (?, ?, ?)"
        )
      ) {
        reduceStockStatement.setInt(1, quantity);
        reduceStockStatement.setInt(2, dataObject.getID());
        reduceStockStatement.executeUpdate();

        createSaleStatement.setInt(1, dataObject.getID());
        createSaleStatement.setString(2, emailOwner);
        createSaleStatement.setInt(3, quantity);
        createSaleStatement.executeUpdate();

        connection.commit();
      } catch (SQLException e) {
        connection.rollback();
      }
    }
  }

  @Override
  public void createOne(ProductDTO dataObject) throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)
    ) {
      statement.setString(1, dataObject.getName());
      statement.setString(2, dataObject.getDescription());
      statement.setString(3, dataObject.getKind().toString());
      statement.setInt(4, dataObject.getStock());
      statement.setString(5, dataObject.getSpecies().toString());
      statement.setFloat(6, dataObject.getPrice());
      statement.setString(7, dataObject.getBrand());
      statement.executeUpdate();
    }
  }

  @Override
  public List<ProductDTO> getAll() throws SQLException {
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY);
      ResultSet resultSet = statement.executeQuery()
    ) {
      List<ProductDTO> products = new ArrayList<>();

      while (resultSet.next()) {
        products.add(createDTOInstanceFromResultSet(resultSet));
      }

      return products;
    }
  }

  @Override
  public ProductDTO getOne(Integer id) throws SQLException {
    String query = "SELECT * FROM Product WHERE id_product = ?";
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setInt(1, id);

      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return createDTOInstanceFromResultSet(resultSet);
        }
      }
    }

    return null;
  }

  @Override
  public void updateOne(ProductDTO dataObject) throws SQLException {
    String query = "UPDATE Product SET name = ?, description = ?, kind = ?, stock = ?, species = ?, price = ?, brand = ? WHERE id_product = ?";
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setString(1, dataObject.getName());
      statement.setString(2, dataObject.getDescription());
      statement.setString(3, dataObject.getKind().toString());
      statement.setInt(4, dataObject.getStock());
      statement.setString(5, dataObject.getSpecies().toString());
      statement.setFloat(6, dataObject.getPrice());
      statement.setString(7, dataObject.getBrand());
      statement.setInt(8, dataObject.getID());
      statement.executeUpdate();
    }
  }

  @Override
  public void deleteOne(Integer id) throws SQLException {
    String query = "DELETE FROM Product WHERE id_product = ?";
    try (
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setInt(1, id);
      statement.executeUpdate();
    }
  }
}

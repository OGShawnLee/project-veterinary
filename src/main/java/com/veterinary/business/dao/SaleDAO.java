package com.veterinary.business.dao;

import com.veterinary.business.dto.SaleDTO;
import com.veterinary.db.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SaleDAO  {
  private final String GET_ALL_QUERY = "SELECT * FROM Sale";
  private final String GET_ONE_QUERY = "SELECT * FROM Sale WHERE id_sale = ?";

  SaleDTO createDTOInstanceFromResultSet(ResultSet resultSet) throws SQLException {
    return new SaleDTO.SaleBuilder()
      .setIDSale(resultSet.getInt("id_sale"))
      .setIDProduct(resultSet.getInt("id_product"))
      .setIDOwner(resultSet.getString("id_owner"))
      .setBoughtAt(resultSet.getTimestamp("bought_at").toLocalDateTime())
      .setQuantity(resultSet.getInt("quantity"))
      .build();
  }

  public List<SaleDTO> getAll() throws SQLException {
    try (
      Connection connection = DBConnector.getConnection();
      PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY);
      ResultSet resultSet = statement.executeQuery()
    ) {
      List<SaleDTO> sales = new ArrayList<>();

      while (resultSet.next()) {
        sales.add(createDTOInstanceFromResultSet(resultSet));
      }

      return sales;
    }
  }

  public SaleDTO getOne(int id) throws SQLException {
    try (
      Connection connection = DBConnector.getConnection();
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
}
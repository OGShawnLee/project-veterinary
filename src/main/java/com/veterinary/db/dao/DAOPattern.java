package com.veterinary.db.dao;

import com.veterinary.db.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class DAOPattern<T, F> extends DBConnector {
  protected abstract T createDTOInstanceFromResultSet(ResultSet resultSet) throws SQLException;

  public abstract void createOne(T dataObject) throws SQLException;

  public abstract List<T> getAll() throws SQLException;

  public abstract T getOne(F filter) throws SQLException;

  public abstract void updateOne(T dataObject) throws SQLException;

  public abstract void deleteOne(F filter) throws SQLException;
}

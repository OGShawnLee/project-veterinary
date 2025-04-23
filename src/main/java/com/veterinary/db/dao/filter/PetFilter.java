package com.veterinary.db.dao.filter;

public class PetFilter {
  private int id;
  private String idOwner;

  public PetFilter(int id, String idOwner) {
    this.id = id;
    this.idOwner = idOwner;
  }

  public int getID() {
    return id;
  }

  public String getIDOwner() {
    return idOwner;
  }
}

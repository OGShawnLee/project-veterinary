package com.veterinary.business.dto;

import java.time.LocalDateTime;

public class SaleDTO {
  private int idSale;
  private int idProduct;
  private String idOwner;
  private LocalDateTime boughtAt;
  private int quantity;

  public SaleDTO(int idSale, int idProduct, String idOwner, LocalDateTime boughtAt, int quantity) {
    this.idSale = idSale;
    this.idProduct = idProduct;
    this.idOwner = idOwner;
    this.boughtAt = boughtAt;
    this.quantity = quantity;
  }

  public int getIDSale() {
    return idSale;
  }

  public int getIDProduct() {
    return idProduct;
  }

  public String getIDOwner() {
    return idOwner;
  }

  public LocalDateTime getBoughtAt() {
    return boughtAt;
  }

  public int getQuantity() {
    return quantity;
  }

  public static class SaleBuilder {
    private int idSale;
    private int idProduct;
    private String idOwner;
    private LocalDateTime boughtAt;
    private int quantity;

    public SaleBuilder setIDSale(int idSale) {
      this.idSale = idSale;
      return this;
    }

    public SaleBuilder setIDProduct(int idProduct) {
      this.idProduct = idProduct;
      return this;
    }

    public SaleBuilder setIDOwner(String idOwner) {
      this.idOwner = idOwner;
      return this;
    }

    public SaleBuilder setBoughtAt(LocalDateTime boughtAt) {
      this.boughtAt = boughtAt;
      return this;
    }

    public SaleBuilder setQuantity(int quantity) {
      this.quantity = quantity;
      return this;
    }

    public SaleDTO build() {
      return new SaleDTO(idSale, idProduct, idOwner, boughtAt, quantity);
    }
  }
}
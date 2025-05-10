package com.veterinary.business.dto;

import com.veterinary.business.Validator;

public class ProductDTO {
  private int id;
  private String name;
  private String description;
  private String kind;
  private int stock;
  private String species;
  private float price;
  private String brand;

  public ProductDTO(ProductBuilder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.description = builder.description;
    this.kind = builder.kind;
    this.stock = builder.stock;
    this.species = builder.species;
    this.price = builder.price;
    this.brand = builder.brand;
  }

  public int getID() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getKind() {
    return kind;
  }

  public int getStock() {
    return stock;
  }

  public String getSpecies() {
    return species;
  }

  public float getPrice() {
    return price;
  }

  public String getBrand() {
    return brand;
  }

  public static class ProductBuilder {
    private int id;
    private String name;
    private String description;
    private String kind;
    private int stock;
    private String species;
    private float price;
    private String brand;

    public ProductBuilder setID(int id) {
      this.id = id;
      return this;
    }

    public ProductBuilder setName(String name) throws IllegalArgumentException {
      this.name = Validator.getValidFlexibleName(name, "Nombre", 3, 32);
      return this;
    }

    public ProductBuilder setDescription(String description) throws IllegalArgumentException {
      this.description = Validator.getValidText(description, "Descripción");
      return this;
    }

    public ProductBuilder setKind(String kind) {
      this.kind = Validator.getValidKind(kind);
      return this;
    }

    public ProductBuilder setStock(String stock) throws IllegalArgumentException {
      this.stock = Validator.getValidPositiveInteger(stock);
      return this;
    }

    public ProductBuilder setSpecies(String species) {
      this.species = Validator.getValidProductSpecies(species);
      return this;
    }

    public ProductBuilder setPrice(float price) {
      this.price = price;
      return this;
    }

    public ProductBuilder setBrand(String brand) throws IllegalArgumentException {
      this.brand = Validator.getValidFlexibleName(brand, "Marca", 3, 32);
      return this;
    }

    public ProductDTO build() {
      return new ProductDTO(this);
    }
  }
}

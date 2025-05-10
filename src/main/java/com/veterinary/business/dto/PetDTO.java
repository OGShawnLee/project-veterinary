package com.veterinary.business.dto;

import com.veterinary.business.Validator;

import java.time.LocalDateTime;

public class PetDTO {
  private final String id;
  private final String idOwner;
  private final String name;
  private final String species;
  private final String breed;
  private final String colour;
  private final float weight;
  private final float height;
  private final LocalDateTime birthDate;

  public PetDTO(PetBuilder builder) {
    this.id = builder.id;
    this.idOwner = builder.idOwner;
    this.name = builder.name;
    this.species = builder.species;
    this.breed = builder.breed;
    this.colour = builder.colour;
    this.weight = builder.weight;
    this.height = builder.height;
    this.birthDate = builder.birthDate;
  }

  public String getID() {
    return id;
  }

  public String getIDOwner() {
    return idOwner;
  }

  public String getName() {
    return name;
  }

  public String getSpecies() {
    return species;
  }

  public String getBreed() {
    return breed;
  }

  public String getColour() {
    return colour;
  }

  public float getWeight() {
    return weight;
  }

  public float getHeight() {
    return height;
  }

  public LocalDateTime getBirthDate() {
    return birthDate;
  }

  public static class PetBuilder {
    private String id;
    private String idOwner;
    private String name;
    private String species;
    private String breed;
    private String colour;
    private float weight;
    private float height;
    private LocalDateTime birthDate;

    public PetBuilder setID(String id) {
      this.id = id;
      return this;
    }

    public PetBuilder setIDOwner(String idOwner) throws IllegalArgumentException {
      this.idOwner = Validator.getValidEmail(idOwner);
      return this;
    }

    public PetBuilder setName(String name) throws IllegalArgumentException {
      this.name = Validator.getValidName(name, "Nombre", 3, 32);
      return this;
    }

    public PetBuilder setSpecies(String species) throws IllegalArgumentException {
      this.species = Validator.getValidPetSpecies(species);
      return this;
    }

    public PetBuilder setBreed(String breed) throws IllegalArgumentException {
      this.breed = Validator.getValidName(breed, "Raza", 3, 32);
      return this;
    }

    public PetBuilder setColour(String colour) throws IllegalArgumentException {
      this.colour = Validator.getValidName(colour, "Color", 3, 32);
      return this;
    }

    public PetBuilder setWeight(float weight) {
      this.weight = weight;
      return this;
    }

    public PetBuilder setHeight(float height) {
      this.height = height;
      return this;
    }

    public PetBuilder setBirthDate(LocalDateTime birthDate) {
      this.birthDate = birthDate;
      return this;
    }

    public PetDTO build() {
      return new PetDTO(this);
    }
  }
}

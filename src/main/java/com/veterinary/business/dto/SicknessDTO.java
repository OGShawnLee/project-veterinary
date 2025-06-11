package com.veterinary.business.dto;

public class SicknessDTO {
  private final int id;
  private final String name;
  private final String description;
  private final Species species;
  private final DangerLevel dangerLevel;

  public enum DangerLevel {
    LOW, MEDIUM, HIGH
  }

  public enum Species {
    DOG, CAT, BOTH
  }

  private SicknessDTO(SicknessBuilder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.description = builder.description;
    this.species = builder.species;
    this.dangerLevel = builder.dangerLevel;
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

  public Species getSpecies() {
    return species;
  }

  public DangerLevel getDangerLevel() {
    return dangerLevel;
  }

  public static class SicknessBuilder {
    private int id;
    private String name;
    private String description;
    private Species species;
    private DangerLevel dangerLevel;

    public SicknessBuilder setID(int id) {
      this.id = id;
      return this;
    }

    public SicknessBuilder setName(String name) {
      this.name = name;
      return this;
    }

    public SicknessBuilder setDescription(String description) {
      this.description = description;
      return this;
    }

    public SicknessBuilder setSpecies(Species species) {
      this.species = species;
      return this;
    }

    public SicknessBuilder setDangerLevel(DangerLevel dangerLevel) {
      this.dangerLevel = dangerLevel;
      return this;
    }

    public SicknessDTO build() {
      return new SicknessDTO(this);
    }
  }
}
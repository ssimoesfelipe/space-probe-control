package com.ssimoesfelipe.spaceprobecontrol.api.spaceprobe;

import java.util.UUID;

public class SpaceProbeResponse {

  private UUID id;
  private String name;
  private Integer horizontalPosition;
  private Integer verticalPosition;
  private String direction;
  private UUID planetId;
  private String planetName;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getHorizontalPosition() {
    return horizontalPosition;
  }

  public void setHorizontalPosition(Integer horizontalPosition) {
    this.horizontalPosition = horizontalPosition;
  }

  public Integer getVerticalPosition() {
    return verticalPosition;
  }

  public void setVerticalPosition(Integer verticalPosition) {
    this.verticalPosition = verticalPosition;
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }

  public UUID getPlanetId() {
    return planetId;
  }

  public void setPlanetId(UUID planetId) {
    this.planetId = planetId;
  }

  public String getPlanetName() {
    return planetName;
  }

  public void setPlanetName(String planetName) {
    this.planetName = planetName;
  }
}

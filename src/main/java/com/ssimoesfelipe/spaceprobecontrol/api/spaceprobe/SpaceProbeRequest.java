package com.ssimoesfelipe.spaceprobecontrol.api.spaceprobe;

import com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe.SpaceProbeDirection;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class SpaceProbeRequest {

  @NotNull
  private String name;

  @NotNull
  @Max(value = 5, message = "Horizontal position cannot be greater than 5!")
  private Integer horizontalPosition;

  @NotNull
  @Max(value = 5, message = "Vertical position cannot be greater than 5!")
  private Integer verticalPosition;

  @NotNull
  private SpaceProbeDirection direction;

  @NotNull
  private UUID planetId;

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

  public SpaceProbeDirection getDirection() {
    return direction;
  }

  public void setDirection(SpaceProbeDirection direction) {
    this.direction = direction;
  }

  public UUID getPlanetId() {
    return planetId;
  }

  public void setPlanetId(UUID planetId) {
    this.planetId = planetId;
  }
}

package com.ssimoesfelipe.spaceprobecontrol.api.planet;

import javax.validation.constraints.NotNull;

public class PlanetRequest {

  @NotNull
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

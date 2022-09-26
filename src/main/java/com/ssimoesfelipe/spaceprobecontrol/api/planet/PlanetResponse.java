package com.ssimoesfelipe.spaceprobecontrol.api.planet;

import com.ssimoesfelipe.spaceprobecontrol.api.spaceprobe.SpaceProbeResponse;
import java.util.List;
import java.util.UUID;

public class PlanetResponse {

  private UUID id;
  private String name;
  private Integer length;
  private Integer width;
  private List<SpaceProbeResponse> spaceProbes;

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

  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

  public List<SpaceProbeResponse> getSpaceProbes() {
    return spaceProbes;
  }

  public void setSpaceProbes(List<SpaceProbeResponse> spaceProbes) {
    this.spaceProbes = spaceProbes;
  }
}

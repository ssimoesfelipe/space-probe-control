package com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ssimoesfelipe.spaceprobecontrol.domain.planet.Planet;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "tb_space_probe")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class SpaceProbe {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String name;

  private Integer horizontalPosition;

  private Integer verticalPosition;

  @Enumerated(EnumType.STRING)
  private SpaceProbeDirection direction;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "planet_id")
  private Planet planet;

  public SpaceProbe() {
  }

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

  public SpaceProbeDirection getDirection() {
    return direction;
  }

  public void setDirection(SpaceProbeDirection guidance) {
    this.direction = guidance;
  }

  public Planet getPlanet() {
    return planet;
  }

  public void setPlanet(Planet planet) {
    this.planet = planet;
  }
}

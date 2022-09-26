package com.ssimoesfelipe.spaceprobecontrol.domain.planet;

import com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe.SpaceProbe;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_planet")
public class Planet {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "planet_id")
  private UUID id;

  private String name;

  private final Integer length = 5;

  private final Integer width = 5;

  @OneToMany(mappedBy = "planet", cascade = CascadeType.ALL)
  private List<SpaceProbe> spaceProbe;

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

  public Integer getWidth() {
    return width;
  }

  public List<SpaceProbe> getSpaceProbe() {
    return spaceProbe;
  }

  public void setSpaceProbe(List<SpaceProbe> spaceProbe) {
    this.spaceProbe = spaceProbe;
  }

}

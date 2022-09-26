package com.ssimoesfelipe.spaceprobecontrol.api;

import com.ssimoesfelipe.spaceprobecontrol.api.planet.PlanetResponse;
import com.ssimoesfelipe.spaceprobecontrol.api.spaceprobe.SpaceProbeResponse;
import com.ssimoesfelipe.spaceprobecontrol.domain.planet.Planet;
import com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe.SpaceProbe;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApiObjectsMapper {

  public static PlanetResponse toResponse(Planet planet) {
    PlanetResponse response = new PlanetResponse();
    response.setId(planet.getId());
    response.setName(planet.getName());
    response.setLength(planet.getLength());
    response.setWidth(planet.getWidth());
    response.setSpaceProbes(planet.getSpaceProbe() == null ? Collections.emptyList() : toSpaceProbesResponse(planet.getSpaceProbe()));

    return response;
  }

  public static SpaceProbeResponse toSpaceProbeResponse(SpaceProbe spaceProbe) {
    SpaceProbeResponse response = new SpaceProbeResponse();
    response.setId(spaceProbe.getId());
    response.setName(spaceProbe.getName());
    response.setHorizontalPosition(spaceProbe.getHorizontalPosition());
    response.setVerticalPosition(spaceProbe.getVerticalPosition());
    response.setDirection(spaceProbe.getDirection().name());
    response.setPlanetId(spaceProbe.getPlanet().getId());
    response.setPlanetName(spaceProbe.getPlanet().getName());

    return response;
  }

  private static List<SpaceProbeResponse> toSpaceProbesResponse(List<SpaceProbe> spaceProbes) {
    return spaceProbes
            .stream()
            .map(ApiObjectsMapper::toSpaceProbeResponse)
            .collect(Collectors.toList());
  }
}

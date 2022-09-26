package com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe;

import static com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe.SpaceProbeDirection.rotateLeft;
import static com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe.SpaceProbeDirection.rotateRight;

import com.ssimoesfelipe.spaceprobecontrol.domain.planet.Planet;
import com.ssimoesfelipe.spaceprobecontrol.domain.planet.PlanetService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class SpaceProbeService {

  private static final Integer STEP_VALUE = 1;

  private final PlanetService planetService;
  private final SpaceProbeRepository spaceProbeRepository;

  public SpaceProbeService(PlanetService planetService, SpaceProbeRepository spaceProbeRepository) {
    this.planetService = planetService;
    this.spaceProbeRepository = spaceProbeRepository;
  }

  public SpaceProbe launchProbe(SpaceProbe spaceProbe, UUID planetId) {
    Planet planet = planetService.findPlanetById(planetId);
    spaceProbe.setPlanet(planet);
    if (existsSpaceProbeInPosition(spaceProbe)) {
      throw new SpaceProbeException("This position in this planet is already occupied!");
    }
    return spaceProbeRepository.save(spaceProbe);
  }

  private Boolean existsSpaceProbeInPosition(SpaceProbe spaceProbe) {
    return spaceProbeRepository.existsByPlanetAndVerticalPositionAndHorizontalPosition(spaceProbe.getPlanet(),
            spaceProbe.getVerticalPosition(), spaceProbe.getHorizontalPosition());
  }

  public SpaceProbe moveSpaceProbe(UUID probeId, List<SpaceProbeCommands> commandsList) {
    SpaceProbe probe = findById(probeId);
    SpaceProbeDirection direction;
    for (SpaceProbeCommands command : commandsList) {
      switch (command) {
        case M:
          if (probe.getDirection() == SpaceProbeDirection.NORTH) {
            probe.setVerticalPosition(probe.getVerticalPosition() + STEP_VALUE);
          } else if (probe.getDirection() == SpaceProbeDirection.SOUTH) {
            probe.setVerticalPosition(probe.getVerticalPosition() - STEP_VALUE);
          } else if (probe.getDirection() == SpaceProbeDirection.EAST) {
            probe.setHorizontalPosition(probe.getHorizontalPosition() + STEP_VALUE);
          } else {
            probe.setHorizontalPosition(probe.getHorizontalPosition() - STEP_VALUE);
          }

          if (existsSpaceProbeInPosition(probe) || !existsNextPosition(probe))
            throw new SpaceProbeException("This position is occupied by other space probe!");
          break;
        case L:
          direction = probe.getDirection();
          probe.setDirection(rotateLeft(direction));
          break;
        case R:
          direction = probe.getDirection();
          probe.setDirection(rotateRight(direction));
          break;
        default:
          throw new SpaceProbeException("This command is invalid!");
      }
    }
    spaceProbeRepository.save(probe);
    return probe;
  }

  private boolean existsNextPosition(SpaceProbe probe) {
    boolean verticalPosition = probe.getVerticalPosition() > probe.getPlanet().getLength();
    boolean horizontalPosition = probe.getHorizontalPosition() > probe.getPlanet().getWidth();
    if (verticalPosition || horizontalPosition) {
      throw new SpaceProbeException("This position is outside the area of that planet!");
    }
    return true;
  }

  public SpaceProbe findById(UUID spaceProbeId) {
    return spaceProbeRepository.findById(spaceProbeId)
            .orElseThrow(() -> new SpaceProbeNotFoundException("Space Probe Not Found with " + spaceProbeId));
  }

  public void removeSpaceProbe(UUID spaceProbeId) {
    SpaceProbe spaceProbeToRemove = findById(spaceProbeId);
    spaceProbeRepository.delete(spaceProbeToRemove);
  }
}

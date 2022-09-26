package com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe;

import com.ssimoesfelipe.spaceprobecontrol.domain.planet.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface SpaceProbeRepository extends JpaRepository<SpaceProbe, UUID> {

  boolean existsByPlanetAndVerticalPositionAndHorizontalPosition(Planet planet, Integer verticalPosition, Integer horizontalPosition);

}

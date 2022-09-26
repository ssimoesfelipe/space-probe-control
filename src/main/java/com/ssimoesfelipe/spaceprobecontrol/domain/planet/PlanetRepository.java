package com.ssimoesfelipe.spaceprobecontrol.domain.planet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, UUID> {

}

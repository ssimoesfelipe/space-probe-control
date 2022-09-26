package com.ssimoesfelipe.spaceprobecontrol.domain.planet;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class PlanetService {

  private final PlanetRepository planetRepository;

  public PlanetService(PlanetRepository planetRepository) {
    this.planetRepository = planetRepository;
  }

  public Planet createPlanet(Planet planet) {
    return planetRepository.save(planet);
  }

  public Planet findPlanetById(UUID planetId) {
    return planetRepository.findById(planetId)
            .orElseThrow(() -> new PlanetNotFoundException("Planet Not Found"));
  }

  @Cacheable("planet")
  public Page<Planet> findAll(Pageable pageable) {
    return planetRepository.findAll(pageable);
  }
}

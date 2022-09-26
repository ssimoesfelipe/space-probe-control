package com.ssimoesfelipe.spaceprobecontrol.planet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ssimoesfelipe.spaceprobecontrol.api.planet.PlanetController;
import com.ssimoesfelipe.spaceprobecontrol.domain.planet.Planet;
import com.ssimoesfelipe.spaceprobecontrol.domain.planet.PlanetNotFoundException;
import com.ssimoesfelipe.spaceprobecontrol.domain.planet.PlanetRepository;
import com.ssimoesfelipe.spaceprobecontrol.domain.planet.PlanetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PlanetServiceTest {

  @Mock
  private PlanetRepository planetRepository;

  @InjectMocks
  private PlanetService planetService;

  @Test
  void shouldCreatePlanet() {
    Planet planet = new Planet();
    planet.setId(UUID.randomUUID());
    planet.setName("Marte");
    planet.setSpaceProbe(List.of());

    when(planetRepository.save(any())).thenReturn(planet);

    Planet createdPlanet = planetService.createPlanet(planet);

    assertEquals(planet.getId(), createdPlanet.getId());
    assertEquals(planet.getName(), createdPlanet.getName());
    assertEquals(5, createdPlanet.getLength());
    assertEquals(5, createdPlanet.getWidth());
    assertTrue(planet.getSpaceProbe().isEmpty());
  }

  @Test
  void shouldReturnAPageOfPlanets(){
    Planet planet = new Planet();
    planet.setId(UUID.randomUUID());
    planet.setName("Marte");
    planet.setSpaceProbe(List.of());

    PageImpl<Planet> planetPage = new PageImpl<>(List.of(planet));

    when(planetRepository.findAll(any(Pageable.class))).thenReturn(planetPage);

    Page<Planet> planetPageResult = planetService.findAll(PageRequest.of(1,1));

    assertNotNull(planetPageResult);
    assertFalse(planetPageResult.toList().isEmpty());
    assertEquals(1, planetPageResult.toList().size());
    assertEquals(planet.getId(), planetPageResult.toList().get(0).getId());
    assertEquals(planet.getName(), planetPageResult.toList().get(0).getName());
    assertTrue(planetPageResult.toList().get(0).getSpaceProbe().isEmpty());
  }
  @Test
  void shouldFindPlanetById() {
    Planet planet = new Planet();
    planet.setId(UUID.randomUUID());
    planet.setName("Marte");
    planet.setSpaceProbe(List.of());

    when(planetRepository.findById(any())).thenReturn(Optional.of(planet));

    Planet foundedPlanet = planetService.findPlanetById(planet.getId());

    assertEquals(planet.getId(), foundedPlanet.getId());
    assertEquals(planet.getName(), foundedPlanet.getName());
    assertEquals(5, foundedPlanet.getLength());
    assertEquals(5, foundedPlanet.getWidth());
    assertTrue(planet.getSpaceProbe().isEmpty());
  }

  @Test
  void shouldThrowExceptionWhenPlanetNotFound() {
    when(planetRepository.findById(any())).thenReturn(Optional.empty());

    PlanetNotFoundException planetNotFoundException = assertThrows(PlanetNotFoundException.class, () -> planetService.findPlanetById(UUID.randomUUID()));
    assertEquals("Planet Not Found", planetNotFoundException.getMessage());
  }
}
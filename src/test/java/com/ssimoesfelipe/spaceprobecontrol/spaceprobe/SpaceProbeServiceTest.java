package com.ssimoesfelipe.spaceprobecontrol.spaceprobe;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.ssimoesfelipe.spaceprobecontrol.domain.planet.Planet;
import com.ssimoesfelipe.spaceprobecontrol.domain.planet.PlanetNotFoundException;
import com.ssimoesfelipe.spaceprobecontrol.domain.planet.PlanetService;
import com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe.SpaceProbeCommands;
import com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe.SpaceProbeDirection;
import com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe.SpaceProbeRepository;
import com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe.SpaceProbeService;
import com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe.SpaceProbe;
import com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe.SpaceProbeException;
import com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe.SpaceProbeNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class SpaceProbeServiceTest {

  @Mock
  private SpaceProbeRepository spaceProbeRepository;

  @Mock
  private PlanetService planetService;

  @InjectMocks
  private SpaceProbeService service;

  @Test
  void shouldLaunchProbeSuccessfully() {
    Planet planet = new Planet();
    planet.setId(UUID.randomUUID());
    planet.setName("Marte");
    SpaceProbe probe = new SpaceProbe();
    probe.setId(UUID.randomUUID());
    probe.setPlanet(planet);
    probe.setName("James");
    probe.setDirection(SpaceProbeDirection.NORTH);
    probe.setHorizontalPosition(1);
    probe.setVerticalPosition(1);

    when(planetService.findPlanetById(any())).thenReturn(planet);
    when(spaceProbeRepository.existsByPlanetAndVerticalPositionAndHorizontalPosition(any(),
            any(), any())).thenReturn(false);
    when(spaceProbeRepository.save(any())).thenReturn(probe);

    SpaceProbe launchedProbe = service.launchProbe(probe, planet.getId());
    Planet targetPlanet = launchedProbe.getPlanet();

    assertNotNull(launchedProbe.getId());
    assertEquals(probe.getName(), launchedProbe.getName());
    assertEquals(probe.getHorizontalPosition(), launchedProbe.getHorizontalPosition());
    assertEquals(probe.getVerticalPosition(), launchedProbe.getVerticalPosition());
    assertEquals(probe.getDirection(), launchedProbe.getDirection());
    assertEquals(planet.getName(), targetPlanet.getName());
    assertEquals(planet.getLength(), targetPlanet.getLength());
    assertEquals(planet.getWidth(), targetPlanet.getWidth());

    verify(planetService,times(1)).findPlanetById(any());
    verify(spaceProbeRepository,times(1)).existsByPlanetAndVerticalPositionAndHorizontalPosition(any(), any(), any());
    verify(spaceProbeRepository,times(1)).save(any());
    verifyNoMoreInteractions(spaceProbeRepository);
  }

  @Test
  void shouldThrowExceptionWhenTargetPlanetNotExists() {
    SpaceProbe probe = new SpaceProbe();
    Planet planet = new Planet();
    planet.setId(UUID.randomUUID());
    planet.setName("Marte");

    when(planetService.findPlanetById(any())).thenThrow(PlanetNotFoundException.class);

    assertThrows(PlanetNotFoundException.class, () -> service.launchProbe(probe, planet.getId()));
  }

  @Test
  void shouldThrowExceptionWhenPositionInPlanetIsOccupied() {
    SpaceProbe probe = new SpaceProbe();
    Planet planet = new Planet();
    planet.setId(UUID.randomUUID());
    planet.setName("Marte");

    when(planetService.findPlanetById(any())).thenReturn(planet);
    when(spaceProbeRepository.existsByPlanetAndVerticalPositionAndHorizontalPosition(any(),
            any(), any())).thenReturn(true);

    SpaceProbeException exception = assertThrows(SpaceProbeException.class, () -> service.launchProbe(probe, planet.getId()));
    assertEquals("This position in this planet is already occupied!", exception.getMessage());
  }

  @Test
  void shouldFindSpaceProbeById() {
    Planet planet = new Planet();
    planet.setId(UUID.randomUUID());
    planet.setName("Marte");
    SpaceProbe probe = new SpaceProbe();
    probe.setId(UUID.randomUUID());
    probe.setPlanet(planet);
    probe.setName("James");
    probe.setDirection(SpaceProbeDirection.NORTH);
    probe.setHorizontalPosition(1);
    probe.setVerticalPosition(1);

    when(spaceProbeRepository.findById(any())).thenReturn(Optional.of(probe));

    SpaceProbe foundedProbe = service.findById(probe.getId());
    Planet targetPlanet = foundedProbe.getPlanet();

    assertNotNull(foundedProbe.getId());
    assertEquals(probe.getName(), foundedProbe.getName());
    assertEquals(probe.getHorizontalPosition(), foundedProbe.getHorizontalPosition());
    assertEquals(probe.getVerticalPosition(), foundedProbe.getVerticalPosition());
    assertEquals(probe.getDirection(), foundedProbe.getDirection());
    assertEquals(planet.getName(), targetPlanet.getName());
    assertEquals(planet.getLength(), targetPlanet.getLength());
    assertEquals(planet.getWidth(), targetPlanet.getWidth());

    verify(spaceProbeRepository, times(1)).findById(any());
    verifyNoMoreInteractions(spaceProbeRepository);
  }

  @Test
  void shouldThrowExceptionWhenSpaceProbeNotFound() {
    SpaceProbe probe = new SpaceProbe();
    probe.setId(UUID.randomUUID());

    when(spaceProbeRepository.findById(any())).thenReturn(Optional.empty());

    SpaceProbeNotFoundException exception = assertThrows(SpaceProbeNotFoundException.class, () -> service.findById(probe.getId()));
    assertEquals("Space Probe Not Found with " + probe.getId(), exception.getMessage());
  }

  private static Stream<Arguments> moveVerticalCommands() {
    return Stream.of(
            Arguments.of(SpaceProbeDirection.NORTH, List.of(SpaceProbeCommands.M), 2),
            Arguments.of(SpaceProbeDirection.SOUTH, List.of(SpaceProbeCommands.M), 0)
    );
  }

  @ParameterizedTest
  @MethodSource("moveVerticalCommands")
  void shouldSpaceProbeMoveVerticalForwardSuccessfully(SpaceProbeDirection direction, List<SpaceProbeCommands> commands, int finalPosition) {
    Planet planet = new Planet();
    planet.setId(UUID.randomUUID());
    planet.setName("Marte");
    SpaceProbe probe = new SpaceProbe();
    probe.setId(UUID.randomUUID());
    probe.setPlanet(planet);
    probe.setName("James");
    probe.setDirection(direction);
    probe.setHorizontalPosition(1);
    probe.setVerticalPosition(1);

    when(spaceProbeRepository.findById(any())).thenReturn(Optional.of(probe));

    SpaceProbe spaceProbe = service.moveSpaceProbe(probe.getId(), commands);

    assertEquals(finalPosition, spaceProbe.getVerticalPosition());
  }

  private static Stream<Arguments> moveHorizontalCommands() {
    return Stream.of(
            Arguments.of(SpaceProbeDirection.EAST, List.of(SpaceProbeCommands.M), 2),
            Arguments.of(SpaceProbeDirection.WEST, List.of(SpaceProbeCommands.M), 0)
    );
  }

  @ParameterizedTest
  @MethodSource("moveHorizontalCommands")
  void shouldSpaceProbeMoveHorizontalForwardSuccessfully(SpaceProbeDirection direction, List<SpaceProbeCommands> commands, int finalPosition) {
    Planet planet = new Planet();
    planet.setId(UUID.randomUUID());
    planet.setName("Marte");
    SpaceProbe probe = new SpaceProbe();
    probe.setId(UUID.randomUUID());
    probe.setPlanet(planet);
    probe.setName("James");
    probe.setDirection(direction);
    probe.setHorizontalPosition(1);
    probe.setVerticalPosition(1);

    when(spaceProbeRepository.findById(any())).thenReturn(Optional.of(probe));

    SpaceProbe spaceProbe = service.moveSpaceProbe(probe.getId(), commands);

    assertEquals(finalPosition, spaceProbe.getHorizontalPosition());
  }

  private static Stream<Arguments> rotateCommands() {
    return Stream.of(
            Arguments.of(SpaceProbeDirection.NORTH, List.of(SpaceProbeCommands.L), SpaceProbeDirection.WEST),
            Arguments.of(SpaceProbeDirection.NORTH, List.of(SpaceProbeCommands.R), SpaceProbeDirection.EAST),
            Arguments.of(SpaceProbeDirection.EAST, List.of(SpaceProbeCommands.L), SpaceProbeDirection.NORTH),
            Arguments.of(SpaceProbeDirection.EAST, List.of(SpaceProbeCommands.R), SpaceProbeDirection.SOUTH),
            Arguments.of(SpaceProbeDirection.SOUTH, List.of(SpaceProbeCommands.L), SpaceProbeDirection.EAST),
            Arguments.of(SpaceProbeDirection.SOUTH, List.of(SpaceProbeCommands.R), SpaceProbeDirection.WEST),
            Arguments.of(SpaceProbeDirection.WEST, List.of(SpaceProbeCommands.L), SpaceProbeDirection.SOUTH),
            Arguments.of(SpaceProbeDirection.WEST, List.of(SpaceProbeCommands.R), SpaceProbeDirection.NORTH)
    );
  }

  @ParameterizedTest
  @MethodSource("rotateCommands")
  void shouldSpaceProbeRotateSuccessfully(SpaceProbeDirection initialGuidance,
                                          List<SpaceProbeCommands> commands, SpaceProbeDirection expectedGuidance) {
    SpaceProbe probe = new SpaceProbe();
    probe.setId(UUID.randomUUID());
    probe.setDirection(initialGuidance);
    probe.setHorizontalPosition(1);
    probe.setVerticalPosition(1);

    when(spaceProbeRepository.findById(any())).thenReturn(Optional.of(probe));

    SpaceProbe spaceProbe = service.moveSpaceProbe(probe.getId(), commands);

    assertEquals(expectedGuidance, spaceProbe.getDirection());
  }

  @Test
  void shouldThrowExceptionWhenSpaceProbeMoveOutsideOfAreaPlanet() {
    Planet planet = new Planet();
    planet.setId(UUID.randomUUID());
    planet.setName("Marte");
    SpaceProbe probe = new SpaceProbe();
    probe.setId(UUID.randomUUID());
    probe.setPlanet(planet);
    probe.setName("James");
    probe.setDirection(SpaceProbeDirection.NORTH);
    probe.setHorizontalPosition(5);
    probe.setVerticalPosition(5);

    when(spaceProbeRepository.findById(any())).thenReturn(Optional.of(probe));

    SpaceProbeException spaceProbeException = assertThrows(SpaceProbeException.class, () -> service.moveSpaceProbe(probe.getId(), List.of(SpaceProbeCommands.M)));
    assertEquals("This position is outside the area of that planet!", spaceProbeException.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenTheNextPositionIsOccupiedByAnotherSpaceProbe() {
    Planet planet = new Planet();
    planet.setId(UUID.randomUUID());
    planet.setName("Marte");
    SpaceProbe probe = new SpaceProbe();
    probe.setId(UUID.randomUUID());
    probe.setPlanet(planet);
    probe.setName("James");
    probe.setDirection(SpaceProbeDirection.NORTH);
    probe.setHorizontalPosition(4);
    probe.setVerticalPosition(4);

    when(spaceProbeRepository.findById(any())).thenReturn(Optional.of(probe));
    when(spaceProbeRepository.existsByPlanetAndVerticalPositionAndHorizontalPosition(any(), any(), any()))
            .thenReturn(true);

    SpaceProbeException spaceProbeException = assertThrows(SpaceProbeException.class, () -> service.moveSpaceProbe(probe.getId(), List.of(SpaceProbeCommands.M)));
    assertEquals("This position is occupied by other space probe!",spaceProbeException.getMessage());
  }

  @Test
  void shouldRemoveSpaceProbe() {
    when(spaceProbeRepository.findById(any())).thenReturn(Optional.of(new SpaceProbe()));

    assertDoesNotThrow(() -> service.removeSpaceProbe(UUID.randomUUID()));
  }

  @Test
  void shouldThrowExceptionWhenSpaceProbeToDeleteNotFound() {
    when(spaceProbeRepository.findById(any())).thenReturn(Optional.empty());

    assertThrows(SpaceProbeNotFoundException.class,() -> service.removeSpaceProbe(UUID.randomUUID()));
  }
}
package com.ssimoesfelipe.spaceprobecontrol.spaceprobe;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssimoesfelipe.spaceprobecontrol.api.spaceprobe.SpaceProbeCommandsRequest;
import com.ssimoesfelipe.spaceprobecontrol.api.spaceprobe.SpaceProbeController;
import com.ssimoesfelipe.spaceprobecontrol.api.spaceprobe.SpaceProbeRequest;
import com.ssimoesfelipe.spaceprobecontrol.domain.planet.Planet;
import com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe.SpaceProbeCommands;
import com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe.SpaceProbeDirection;
import com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe.SpaceProbeRepository;
import com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe.SpaceProbeService;
import com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe.SpaceProbe;
import com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe.SpaceProbeNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SpaceProbeController.class)
public class SpaceProbeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SpaceProbeService spaceProbeService;

  @MockBean
  private SpaceProbeRepository spaceProbeRepository;

  @Test
  void shouldLaunchProbe() throws Exception {
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

    SpaceProbeRequest request = new SpaceProbeRequest();
    request.setName("Marte");
    request.setHorizontalPosition(1);
    request.setVerticalPosition(1);
    request.setDirection(SpaceProbeDirection.NORTH);
    request.setPlanetId(planet.getId());

    when(spaceProbeService.launchProbe(any(), any())).thenReturn(probe);

    mockMvc.perform(post("/v1/space-probes")
                    .content(new ObjectMapper().writeValueAsBytes(request))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", Matchers.is(probe.getId().toString())))
            .andExpect(jsonPath("$.name", Matchers.is(probe.getName())))
            .andExpect(jsonPath("$.horizontalPosition", Matchers.is(1)))
            .andExpect(jsonPath("$.verticalPosition", Matchers.is(1)))
            .andExpect(jsonPath("$.direction", Matchers.is("NORTH")))
            .andExpect(jsonPath("$.planetId", Matchers.is(planet.getId().toString())))
            .andExpect(jsonPath("$.planetName", Matchers.is(planet.getName())));
  }

  @Test
  void shouldFindSpaceProbeById() throws Exception {
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

    when(spaceProbeService.findById(any())).thenReturn(probe);

    mockMvc.perform(get("/v1/space-probes/" + probe.getId())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", Matchers.is(probe.getId().toString())))
            .andExpect(jsonPath("$.name", Matchers.is(probe.getName())))
            .andExpect(jsonPath("$.horizontalPosition", Matchers.is(1)))
            .andExpect(jsonPath("$.verticalPosition", Matchers.is(1)))
            .andExpect(jsonPath("$.direction", Matchers.is("NORTH")))
            .andExpect(jsonPath("$.planetId", Matchers.is(planet.getId().toString())))
            .andExpect(jsonPath("$.planetName", Matchers.is(planet.getName())));

  }

  @Test
  void shouldReturnNotFoundWhenSpaceProbeNotFound() throws Exception {

    when(spaceProbeService.findById(any())).thenThrow(new SpaceProbeNotFoundException("Not Found!"));

    mockMvc.perform(get("/v1/space-probes/" + UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnBadRequestWhenCreateInvalidSpaceProbe() throws Exception {
    SpaceProbeRequest request = new SpaceProbeRequest();

    mockMvc.perform(post("/v1/space-probes")
                    .content(new ObjectMapper().writeValueAsBytes(request))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
  }

  @Test
  void shouldMoveSpaceProbe() throws Exception {
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

    SpaceProbeCommandsRequest request = new SpaceProbeCommandsRequest();
    request.setCommandsList(List.of(SpaceProbeCommands.M));

    when(spaceProbeService.findById(any())).thenReturn(probe);
    when(spaceProbeService.moveSpaceProbe(any(), any())).thenReturn(probe);

    mockMvc.perform(patch("/v1/space-probes/" + probe.getId())
                    .content(new ObjectMapper().writeValueAsBytes(request))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", Matchers.is(probe.getId().toString())))
            .andExpect(jsonPath("$.name", Matchers.is(probe.getName())))
            .andExpect(jsonPath("$.horizontalPosition", Matchers.is(1)))
            .andExpect(jsonPath("$.verticalPosition", Matchers.is(1)))
            .andExpect(jsonPath("$.direction", Matchers.is("NORTH")))
            .andExpect(jsonPath("$.planetId", Matchers.is(planet.getId().toString())))
            .andExpect(jsonPath("$.planetName", Matchers.is(planet.getName())));
  }

  @Test
  void shouldRemoveSpaceProbe() throws Exception {
    doNothing().when(spaceProbeService).removeSpaceProbe(any());

    mockMvc.perform(delete("/v1/space-probes/" + UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
  }

  @Test
  void shouldReturnNotFoundWhenSpaceProbeToRemoveNotFound() throws Exception {
    doThrow(SpaceProbeNotFoundException.class).when(spaceProbeService).removeSpaceProbe(any());

    mockMvc.perform(delete("/v1/space-probes/" + UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
  }
}
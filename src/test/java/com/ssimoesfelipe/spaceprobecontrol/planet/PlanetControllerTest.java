package com.ssimoesfelipe.spaceprobecontrol.planet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssimoesfelipe.spaceprobecontrol.api.planet.PlanetController;
import com.ssimoesfelipe.spaceprobecontrol.api.planet.PlanetRequest;
import com.ssimoesfelipe.spaceprobecontrol.domain.planet.Planet;
import com.ssimoesfelipe.spaceprobecontrol.domain.planet.PlanetNotFoundException;
import com.ssimoesfelipe.spaceprobecontrol.domain.planet.PlanetRepository;
import com.ssimoesfelipe.spaceprobecontrol.domain.planet.PlanetService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PlanetController.class)
class PlanetControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PlanetService planetService;

  @MockBean
  private PlanetRepository repository;

  @Test
  void shouldCreatePlanet() throws Exception {
    PlanetRequest request = new PlanetRequest();
    request.setName("Marte");
    Planet planet = new Planet();
    planet.setId(UUID.randomUUID());
    planet.setName("Marte");
    planet.setSpaceProbe(List.of());
    when(planetService.createPlanet(any())).thenReturn(planet);
    mockMvc.perform(post("/v1/planets")
                    .content(new ObjectMapper().writeValueAsBytes(request))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", Matchers.is(planet.getId().toString())))
            .andExpect(jsonPath("$.name", Matchers.is(planet.getName())))
            .andExpect(jsonPath("$.length", Matchers.is(5)))
            .andExpect(jsonPath("$.width", Matchers.is(5)))
            .andExpect(jsonPath("$.spaceProbes", Matchers.hasSize(0)));
  }

  @Test
  void shouldFindAllPlanets() throws Exception {
    Planet planet = new Planet();
    planet.setId(UUID.randomUUID());
    planet.setName("Marte");
    planet.setSpaceProbe(List.of());
    PageImpl<Planet> planetPage = new PageImpl<>(List.of(planet));

    when(planetService.findAll(any(Pageable.class))).thenReturn(planetPage);

    mockMvc.perform(get("/v1/planets")
                    .param("page", "5")
                    .param("size", "10"))// <-- no space after comma!!!
            .andExpect(status().isOk());

    ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
    verify(planetService).findAll(pageableCaptor.capture());
    PageRequest pageable = (PageRequest) pageableCaptor.getValue();

    assertEquals(5, pageable.getPageNumber());
    assertEquals(10, pageable.getPageSize());
  }
  @Test
  void shouldFindPlanetById() throws Exception {
    Planet planet = new Planet();
    planet.setId(UUID.randomUUID());
    planet.setName("Marte");
    planet.setSpaceProbe(List.of());

    when(planetService.findPlanetById(any())).thenReturn(planet);

    mockMvc.perform(get("/v1/planets/" + planet.getId())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", Matchers.is(planet.getId().toString())))
            .andExpect(jsonPath("$.name", Matchers.is(planet.getName())))
            .andExpect(jsonPath("$.length", Matchers.is(5)))
            .andExpect(jsonPath("$.width", Matchers.is(5)))
            .andExpect(jsonPath("$.spaceProbes", Matchers.hasSize(0)));
  }

  @Test
  void shouldReturnNotFoundWhenPlanetNotFound() throws Exception {

    when(planetService.findPlanetById(any())).thenThrow(new PlanetNotFoundException("Not Found!"));

    mockMvc.perform(get("/v1/planets/" + UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnBadRequestWhenCreateInvalidPlanet() throws Exception {
    PlanetRequest request = new PlanetRequest();

    mockMvc.perform(post("/v1/planets")
                    .content(new ObjectMapper().writeValueAsBytes(request))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
  }

}
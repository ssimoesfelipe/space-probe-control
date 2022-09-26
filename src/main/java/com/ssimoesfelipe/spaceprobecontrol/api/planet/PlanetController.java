package com.ssimoesfelipe.spaceprobecontrol.api.planet;

import static com.ssimoesfelipe.spaceprobecontrol.api.ApiObjectsMapper.toResponse;

import com.ssimoesfelipe.spaceprobecontrol.api.ApiObjectsMapper;
import com.ssimoesfelipe.spaceprobecontrol.domain.planet.Planet;
import com.ssimoesfelipe.spaceprobecontrol.domain.planet.PlanetService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/v1/planets")
@RestController
public class PlanetController {

  private final PlanetService planetService;

  public PlanetController(PlanetService planetService) {
    this.planetService = planetService;
  }

  @ApiOperation("Create a new planet.")
  @PostMapping
  public ResponseEntity<PlanetResponse> createPlanet(@RequestBody @Valid PlanetRequest planetRequest) {
    Planet planet = new Planet();
    BeanUtils.copyProperties(planetRequest, planet);
    return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(planetService.createPlanet(planet)));
  }

  @GetMapping
  public ResponseEntity<Page<PlanetResponse>> findAll(Pageable pageable) {
    return ResponseEntity.ok(planetService.findAll(pageable).map(ApiObjectsMapper::toResponse));
  }

  @ApiOperation("Find a planet by id.")
  @GetMapping("/{planetId}")
  public ResponseEntity<PlanetResponse> findPlanetById(@PathVariable("planetId") UUID planetId) {
    return ResponseEntity.ok(toResponse(planetService.findPlanetById(planetId)));
  }
}

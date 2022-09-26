package com.ssimoesfelipe.spaceprobecontrol.api.spaceprobe;

import static com.ssimoesfelipe.spaceprobecontrol.api.ApiObjectsMapper.toSpaceProbeResponse;

import com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe.SpaceProbe;
import com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe.SpaceProbeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/v1/space-probes")
@RestController
public class SpaceProbeController {

  private final SpaceProbeService service;

  public SpaceProbeController(SpaceProbeService service) {
    this.service = service;
  }

  @ApiOperation("Launch a space probe.")
  @PostMapping
  public ResponseEntity<SpaceProbeResponse> launchProbe(@RequestBody @Valid SpaceProbeRequest spaceProbeRequest) {
    SpaceProbe spaceProbe = new SpaceProbe();
    BeanUtils.copyProperties(spaceProbeRequest, spaceProbe);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(toSpaceProbeResponse(service.launchProbe(spaceProbe, spaceProbeRequest.getPlanetId())));
  }

  @ApiOperation("Find space probe by id.")
  @GetMapping("/{spaceProbeId}")
  public ResponseEntity<SpaceProbeResponse> findById(@PathVariable("spaceProbeId") UUID spaceProbeId){
    return ResponseEntity.ok(toSpaceProbeResponse(service.findById(spaceProbeId)));
  }

  @ApiOperation("Move space probe.")
  @PatchMapping("/{spaceProbeId}")
  public ResponseEntity<SpaceProbeResponse> moveProbe(@PathVariable("spaceProbeId") UUID probeId, @RequestBody SpaceProbeCommandsRequest commandsRequest) {
    return ResponseEntity.ok(toSpaceProbeResponse(service.moveSpaceProbe(probeId, commandsRequest.getCommandsList())));
  }

  @ApiOperation("Remove space probe.")
  @DeleteMapping("/{spaceProbeId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeSpaceProbe(@PathVariable("spaceProbeId") UUID probeId) {
    service.removeSpaceProbe(probeId);
  }
}

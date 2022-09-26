package com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SpaceProbeNotFoundException extends RuntimeException{

  public SpaceProbeNotFoundException(String message) {
    super(message);
  }
}

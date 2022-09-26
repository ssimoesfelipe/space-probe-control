package com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SpaceProbeException extends RuntimeException {

  public SpaceProbeException(String message) {
    super(message);
  }
}

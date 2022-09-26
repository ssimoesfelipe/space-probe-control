package com.ssimoesfelipe.spaceprobecontrol.domain.planet;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PlanetNotFoundException extends RuntimeException {

  public PlanetNotFoundException(String message) {
    super(message);
  }
}

package com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe;

public enum SpaceProbeCommands {
  M("Move forward"),
  L("Rotate 90 degrees left"),
  R("Rotate 90 degrees right");

  SpaceProbeCommands(String description) {
  }
}

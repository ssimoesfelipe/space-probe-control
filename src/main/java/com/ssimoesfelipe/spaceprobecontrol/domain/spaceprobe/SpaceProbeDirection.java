package com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe;

public enum SpaceProbeDirection {
  NORTH,
  EAST,
  SOUTH,
  WEST;

  static SpaceProbeDirection rotateLeft(SpaceProbeDirection direction) {
    return direction.ordinal() == 0 ? SpaceProbeDirection.WEST : SpaceProbeDirection.values()[direction.ordinal() - 1];
  }

  static SpaceProbeDirection rotateRight(SpaceProbeDirection direction) {
    return direction.ordinal() == 3 ? SpaceProbeDirection.NORTH : SpaceProbeDirection.values()[direction.ordinal() + 1];
  }
}

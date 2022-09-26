package com.ssimoesfelipe.spaceprobecontrol.api.spaceprobe;

import com.ssimoesfelipe.spaceprobecontrol.domain.spaceprobe.SpaceProbeCommands;
import java.util.List;

public class SpaceProbeCommandsRequest {

  private List<SpaceProbeCommands> commandsList;

  public List<SpaceProbeCommands> getCommandsList() {
    return commandsList;
  }

  public void setCommandsList(List<SpaceProbeCommands> commandsList) {
    this.commandsList = commandsList;
  }
}

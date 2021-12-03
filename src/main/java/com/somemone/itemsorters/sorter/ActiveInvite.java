package com.somemone.itemsorters.sorter;

import java.util.UUID;

public class ActiveInvite {

  private UUID inviteFrom;
  private UUID inviteTo;

  public ActiveInvite(UUID inviteFrom, UUID inviteTo) {
    this.inviteFrom = inviteFrom;
    this.inviteTo = inviteTo;
  }

  public UUID getFrom() {
    return inviteFrom;
  }

  public UUID getTo() {
    return inviteTo;
  }
}
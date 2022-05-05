package me.kxmpxtxnt.paypaul.transaction;

import org.bukkit.entity.Player;

import java.util.UUID;

public class Transaction {

  private final UUID sender;
  private final UUID receiver;
  private final Long amount;
  private final Long time;

  public Transaction(UUID sender, UUID receiver, Long amount, Long time) {
    this.sender = sender;
    this.receiver = receiver;
    this.amount = amount;
    this.time = time;
  }

  public Transaction(UUID sender, UUID receiver, Long amount) {
    this.sender = sender;
    this.receiver = receiver;
    this.amount = amount;
    this.time = System.currentTimeMillis();
  }

  public UUID sender(){
    return sender;
  }

  public UUID receiver(){
    return receiver;
  }

  public Long amount(){
    return amount;
  }

  public Long time(){
    return time;
  }
}

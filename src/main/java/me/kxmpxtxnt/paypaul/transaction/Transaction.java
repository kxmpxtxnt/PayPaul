package me.kxmpxtxnt.paypaul.transaction;

import org.bukkit.entity.Player;

public class Transaction {

  private final Player sender;
  private final Player receiver;
  private final Long amount;
  private final Long time;

  public Transaction(Player sender, Player receiver, Long amount, Long time) {
    this.sender = sender;
    this.receiver = receiver;
    this.amount = amount;
    this.time = time;
  }

  public Transaction(Player sender, Player receiver, Long amount) {
    this.sender = sender;
    this.receiver = receiver;
    this.amount = amount;
    this.time = System.currentTimeMillis();
  }

  public Player sender(){
    return sender;
  }

  public Player receiver(){
    return receiver;
  }

  public Long amount(){
    return amount;
  }

  public Long time(){
    return time;
  }
}

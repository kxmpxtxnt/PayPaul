package me.kxmpxtxnt.paypaul.transaction;

import org.bukkit.entity.Player;

public record Transaction(Player sender, Player receiver, Long amount, Long time) {

}

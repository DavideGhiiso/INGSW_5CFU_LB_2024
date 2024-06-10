package it.polimi.ingsw.events;

/**
 * Enum that contains all possible server response to a client trying to connect
 */
public enum Response {
    OK, // connection accepted, game isn't started yet
    CAN_REPLACE_BOT, // connection accepted, player can join the onging game by replacing a bot
    GAME_FULL, // connection refused, server full
    OK_ONGOING, // connection acceptation confirmation after a client response to CAN_REPLACE_BOT
    USERNAME_TAKEN // connection refused, a client is already connected with passed username
}

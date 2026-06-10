package com.mycompany.chatapppart1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Represents a single chat message.
 * Handles validation, hash generation, and formatting.
 * Student :
 */
public class Message {

    private static int totalMessages = 0;

    private final String messageID;
    private final String recipient;
    private final String messageContent;
    private final String messageHash;
    private final int    messageNumber;

    public Message(String recipient, String messageContent) {
        totalMessages++;
        this.messageNumber  = totalMessages;
        this.recipient      = recipient;
        this.messageContent = messageContent;
        this.messageID      = generateMessageID();
        this.messageHash    = generateMessageHash();
    }

    // ── Validation ────────────────────────────────────────────────

    /** Message ID must be 10 digits or fewer. */
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    /** Message body must be 250 characters or fewer. */
    public boolean checkMessageLength() {
        return messageContent.length() <= 250;
    }

    // ── Generation ────────────────────────────────────────────────

    /** Creates a random 10-digit numeric ID. */
    private String generateMessageID() {
        long id = (long) (Math.random() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(id);
    }

    /**
     * Hash format: <messageNum>:<firstWord>:<lastWord>   (all uppercase)
     * Example: "1:HI:MIKE"
     */
    public String generateMessageHash() {
        String[] words = messageContent.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord  = words[words.length - 1];
        return (messageNumber + ":" + firstWord + ":" + lastWord).toUpperCase();
    }

    // ── Display ───────────────────────────────────────────────────

    public String displayMessage() {
        return "Message ID: "      + messageID      + "\n"
             + "Message Hash: "    + messageHash    + "\n"
             + "Recipient: "       + recipient      + "\n"
             + "Message: "         + messageContent + "\n"
             + "Message Number: "  + messageNumber;
    }

    // ── Getters ───────────────────────────────────────────────────

    public String getMessageID()      { return messageID;      }
    public String getRecipient()      { return recipient;      }
    public String getMessageContent() { return messageContent; }
    public String getMessageHash()    { return messageHash;    }
    public int    getMessageNumber()  { return messageNumber;  }

    public static int getTotalMessages() { return totalMessages; }
    public static void resetTotalMessages() { totalMessages = 0; }
}

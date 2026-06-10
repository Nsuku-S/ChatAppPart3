package com.mycompany.chatapppart1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 * Manages all messages for Part 3.
 *
 * Five parallel arrays (ArrayLists) track every message:
 *   sentMessages       – messages the user chose to SEND
 *   storedMessages     – messages the user chose to STORE for later
 *   disregardedMessages– messages the user chose to DISREGARD (delete)
 *   messageHashes      – hash string for each stored message
 *   messageIDs         – ID string for each stored message
 *   recipientList      – recipient for each stored message
 *
 * Student: ST10482781
 */
public class MessageManager {

    // ── Part 3 Arrays ─────────────────────────────────────────────
    private static List<String> sentMessages        = new ArrayList<>();
    private static List<String> disregardedMessages = new ArrayList<>();
    private static List<String> storedMessages      = new ArrayList<>();
    private static List<String> messageHashes       = new ArrayList<>();
    private static List<String> messageIDs          = new ArrayList<>();
    private static List<String> recipientList       = new ArrayList<>();

    // ── Send / Store / Disregard ──────────────────────────────────

    /**
     * Records a message as SENT.
     * Returns a confirmation string.
     */
    public static String sendMessage(Message msg) {
        if (!msg.checkMessageLength()) {
            return "Message exceeds 250 characters. Please shorten your message.";
        }
        sentMessages.add(msg.getMessageContent());
        storedMessages.add(msg.getMessageContent());
        messageHashes.add(msg.getMessageHash());
        messageIDs.add(msg.getMessageID());
        recipientList.add(msg.getRecipient());

        // Also persist to JSON file
        saveMessageToJson(msg);

        return "Message successfully sent.\n" + msg.displayMessage();
    }

    /**
     * Records a message as STORED (saved for later, NOT sent).
     */
    public static String storeMessage(Message msg) {
        storedMessages.add(msg.getMessageContent());
        messageHashes.add(msg.getMessageHash());
        messageIDs.add(msg.getMessageID());
        recipientList.add(msg.getRecipient());
        return "Message successfully stored.";
    }

    /**
     * Records a message as DISREGARDED (discarded without sending).
     */
    public static String disregardMessage(Message msg) {
        disregardedMessages.add(msg.getMessageContent());
        return "Message disregarded.";
    }

    // ── Part 3 Required Methods ───────────────────────────────────

    /**
     * Returns the longest message from the sentMessages list.
     */
    public static String displayLongestMessage() {
        String longest = "";
        for (String message : sentMessages) {
            if (message.length() > longest.length()) {
                longest = message;
            }
        }
        if (longest.isEmpty()) {
            return "No sent messages found.";
        }
        return "Longest message: " + longest;
    }

    /**
     * Searches storedMessages by message ID.
     * Returns the recipient and message body if found.
     */
    public static String searchByMessageID(String id) {
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(id)) {
                return "Recipient: " + recipientList.get(i)
                     + "\nMessage: "  + storedMessages.get(i);
            }
        }
        return "Message not found.";
    }

    /**
     * Searches storedMessages by recipient name.
     * Returns all messages sent to that recipient.
     */
    public static String searchByRecipient(String recipient) {
        StringBuilder results = new StringBuilder();
        for (int i = 0; i < recipientList.size(); i++) {
            if (recipientList.get(i).equals(recipient)) {
                if (i < storedMessages.size()) {
                    results.append(storedMessages.get(i)).append("\n");
                }
            }
        }
        if (results.length() == 0) {
            return "No messages found.";
        }
        return results.toString();
    }

    /**
     * Deletes a stored message by its hash value.
     * Also removes the matching entries from all parallel arrays.
     */
    public static String deleteByHash(String hash) {
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equals(hash)) {
                String deletedMessage = storedMessages.get(i);

                // Remove from all parallel arrays
                messageHashes.remove(i);
                messageIDs.remove(i);
                recipientList.remove(i);
                storedMessages.remove(i);

                return "Message: \"" + deletedMessage
                     + "\" successfully deleted.";
            }
        }
        return "Hash not found.";
    }

    /**
     * Loads stored messages from messages.json into storedMessages.
     * Each line in the file must be a JSON object with a "Message" key.
     *
     * Attribution: org.json library
     * https://mvnrepository.com/artifact/org.json/json
     */
    public static void loadStoredMessages() {
        try (BufferedReader reader =
                new BufferedReader(new FileReader("messages.json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                JSONObject object = new JSONObject(line);
                storedMessages.add(object.getString("Message"));
            }
        } catch (IOException e) {
            System.out.println("No stored messages found.");
        }
    }

    /**
     * Saves a single message as a JSON line to messages.json.
     */
    private static void saveMessageToJson(Message msg) {
        try (FileWriter fw = new FileWriter("messages.json", true)) {
            JSONObject obj = new JSONObject();
            obj.put("MessageID",  msg.getMessageID());
            obj.put("Hash",       msg.getMessageHash());
            obj.put("Recipient",  msg.getRecipient());
            obj.put("Message",    msg.getMessageContent());
            fw.write(obj.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Could not save message to file.");
        }
    }

    /**
     * Prints a full report of all stored messages with their
     * hash, recipient, and content.
     */
    public static String displayReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== MESSAGE REPORT ===\n");
        for (int i = 0; i < storedMessages.size(); i++) {
            report.append("--------------------------\n");
            report.append("Message Hash: ")
                  .append(messageHashes.size() > i ? messageHashes.get(i) : "N/A")
                  .append("\n");
            report.append("Recipient: ")
                  .append(recipientList.size() > i ? recipientList.get(i) : "N/A")
                  .append("\n");
            report.append("Message: ")
                  .append(storedMessages.get(i))
                  .append("\n");
        }
        if (storedMessages.isEmpty()) {
            report.append("No messages to display.\n");
        }
        return report.toString();
    }

    /**
     * Returns all recently sent messages as a formatted string.
     */
    public static String displaySentMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages sent yet.";
        }
        StringBuilder sb = new StringBuilder("=== SENT MESSAGES ===\n");
        for (int i = 0; i < sentMessages.size(); i++) {
            sb.append((i + 1)).append(". ").append(sentMessages.get(i)).append("\n");
        }
        return sb.toString();
    }

    // ── GETTERS (used by tests) ───────────────────────────────────

    public static List<String> getSentMessages()        { return sentMessages;        }
    public static List<String> getStoredMessages()      { return storedMessages;      }
    public static List<String> getDisregardedMessages() { return disregardedMessages; }
    public static List<String> getMessageHashes()       { return messageHashes;       }
    public static List<String> getMessageIDs()          { return messageIDs;          }
    public static List<String> getRecipientList()       { return recipientList;       }

    /** Clears all arrays – used between unit tests. */
    public static void clearAll() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
        recipientList.clear();
    }
}

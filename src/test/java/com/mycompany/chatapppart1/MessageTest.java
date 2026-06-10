package com.mycompany.chatapppart1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Part 3 Unit Tests – all 6 tests must pass.
 Student : ST10516027
 */
public class MessageTest {

    @BeforeEach
    public void setUp() {
        // Clear all arrays and reset message counter before each test
        MessageManager.clearAll();
        Message.resetTotalMessages();
    }

    // ── Test 1: Send a message and confirm it appears in sentMessages ──

    @Test
    public void testMessageSentSuccessfully() {
        Message msg = new Message("Mike", "Hi Mike, please call me.");
        MessageManager.sendMessage(msg);

        assertFalse(MessageManager.getSentMessages().isEmpty(),
            "Sent messages list should not be empty after sending.");
        assertEquals("Hi Mike, please call me.",
            MessageManager.getSentMessages().get(0),
            "Sent message content should match.");
    }

    // ── Test 2: Store a message and confirm it appears in storedMessages ──

    @Test
    public void testMessageStoredSuccessfully() {
        Message msg = new Message("Sarah", "Can we meet tomorrow?");
        MessageManager.storeMessage(msg);

        assertFalse(MessageManager.getStoredMessages().isEmpty(),
            "Stored messages list should not be empty after storing.");
        assertTrue(MessageManager.getStoredMessages().contains("Can we meet tomorrow?"),
            "Stored messages should contain the stored message.");
    }

    // ── Test 3: displayLongestMessage returns the correct message ──

    @Test
    public void testDisplayLongestMessage() {
        Message short1 = new Message("Tom", "Hello.");
        Message long1  = new Message("Tom", "This is a much longer message to test the longest.");

        MessageManager.sendMessage(short1);
        MessageManager.sendMessage(long1);

        String result = MessageManager.displayLongestMessage();
        assertTrue(result.contains("This is a much longer message to test the longest."),
            "Should return the longest sent message.");
    }

    // ── Test 4: searchByMessageID finds the correct message ──

    @Test
    public void testSearchByMessageID() {
        Message msg = new Message("Jake", "Meet me at the office.");
        MessageManager.sendMessage(msg);

        String result = MessageManager.searchByMessageID(msg.getMessageID());
        assertTrue(result.contains("Jake"),
            "Search by ID should return the correct recipient.");
        assertTrue(result.contains("Meet me at the office."),
            "Search by ID should return the correct message.");
    }

    // ── Test 5: searchByRecipient finds all messages for that recipient ──

    @Test
    public void testSearchByRecipient() {
        Message msg1 = new Message("Alice", "First message to Alice.");
        Message msg2 = new Message("Alice", "Second message to Alice.");
        Message msg3 = new Message("Bob",   "Message to Bob.");

        MessageManager.sendMessage(msg1);
        MessageManager.sendMessage(msg2);
        MessageManager.sendMessage(msg3);

        String result = MessageManager.searchByRecipient("Alice");
        assertTrue(result.contains("First message to Alice."),
            "Should find first message for Alice.");
        assertTrue(result.contains("Second message to Alice."),
            "Should find second message for Alice.");
        assertFalse(result.contains("Message to Bob."),
            "Should not include Bob's message in Alice's results.");
    }

    // ── Test 6: deleteByHash removes the correct message ──

    @Test
    public void testDeleteByHash() {
        Message msg = new Message("Dave", "Delete this message.");
        MessageManager.sendMessage(msg);

        String hash = msg.getMessageHash();
        String result = MessageManager.deleteByHash(hash);

        assertTrue(result.contains("successfully deleted"),
            "Should confirm message was deleted.");
        assertFalse(MessageManager.getMessageHashes().contains(hash),
            "Hash should no longer exist in the list after deletion.");
    }
}

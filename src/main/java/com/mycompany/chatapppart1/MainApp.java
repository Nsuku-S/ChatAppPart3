package com.mycompany.chatapppart1;

import java.util.Scanner;

/**
 * Entry point for the Chat App.
 * Part 1: Login/Register
 * Part 2: Send messages
 * Part 3: Stored messages sub-menu, search, delete, report
 *
 * Student: ST10516027
 */
public class MainApp {

    static Scanner scanner = new Scanner(System.in);
    static Login login = new Login();

    public static void main(String[] args) {

        // ── ASCII Art (required from Part 1) ──────────────────────
        System.out.println("  ____  _           _   _    _             ");
        System.out.println(" / ___|| |__   __ _| |_| |  | |__  _   _  ");
        System.out.println(" \\___ \\| '_ \\ / _` | __| |  | '_ \\| | | | ");
        System.out.println("  ___) | | | | (_| | |_| |__| |_) | |_| | ");
        System.out.println(" |____/|_| |_|\\__,_|\\__|\\____/_.__/ \\__, | ");
        System.out.println("                                     |___/  ");
        System.out.println("========================================");
        System.out.println("  Welcome to QuickChat  |    ");
        System.out.println("========================================\n");

        // ── REGISTRATION ──────────────────────────────────────────────
        System.out.println("--- REGISTER ---");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Username (max 5 chars, must include _): ");
        String username = scanner.nextLine();
        System.out.print("Password (8+ chars, capital, number, special): ");
        String password = scanner.nextLine();
        System.out.print("Cell Number (e.g. +0123456789): ");
        String cell = scanner.nextLine();

        String regResult = login.registerUser(firstName, lastName, username, password, cell);
        System.out.println(regResult);

        if (!login.checkUserName(username) || !login.checkPasswordComplexity(password)) {
            System.out.println("Registration failed. Please restart and try again.");
            return;
        }

        // ── Login ─────────────────────────────────────────────────
        System.out.println("\n--- LOGIN ---");
        System.out.print("Username: ");
        String loginUser = scanner.nextLine();
        System.out.print("Password: ");
        String loginPass = scanner.nextLine();

        String loginStatus = login.returnLoginStatus(loginUser, loginPass);
        System.out.println(loginStatus);

        if (!login.loginUser(loginUser, loginPass)) {
            System.out.println("Login failed. Exiting.");
            return;
        }

        // Load any messages saved from a previous session
        MessageManager.loadStoredMessages();

        // ── Main Menu ─────────────────────────────────────────────
        boolean running = true;
        while (running) {
            System.out.println("\n=== MENU ===");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> sendMessageFlow();
                case "2" -> System.out.println(MessageManager.displaySentMessages());
                case "3" -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                case "4" -> storedMessagesMenu();
                default  -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // ── Send Message Flow ─────────────────────────────────────────

    private static void sendMessageFlow() {
        System.out.print("Recipient: ");
        String recipient = scanner.nextLine();
        System.out.print("Message (max 250 chars): ");
        String content = scanner.nextLine();

        if (content.length() > 250) {
            System.out.println("Message exceeds 250 characters ("
                + content.length() + "). Please shorten your message.");
            return;
        }

        Message msg = new Message(recipient, content);

        System.out.println("\nWhat would you like to do?");
        System.out.println("1) Send");
        System.out.println("2) Store for later");
        System.out.println("3) Disregard");
        System.out.print("Choice: ");
        String action = scanner.nextLine().trim();

        switch (action) {
            case "1" -> System.out.println(MessageManager.sendMessage(msg));
            case "2" -> System.out.println(MessageManager.storeMessage(msg));
            case "3" -> System.out.println(MessageManager.disregardMessage(msg));
            default  -> System.out.println("Invalid option. Message not saved.");
        }
    }

    // ── Stored Messages Sub-Menu (Part 3 – Menu Option 4) ─────────

    private static void storedMessagesMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== STORED MESSAGES ===");
            System.out.println("1) Show all stored messages (report)");
            System.out.println("2) Search by Message ID");
            System.out.println("3) Search by Recipient");
            System.out.println("4) Display longest sent message");
            System.out.println("5) Delete message by Hash");
            System.out.println("6) Back to main menu");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> System.out.println(MessageManager.displayReport());
                case "2" -> {
                    System.out.print("Enter Message ID: ");
                    String id = scanner.nextLine();
                    System.out.println(MessageManager.searchByMessageID(id));
                }
                case "3" -> {
                    System.out.print("Enter recipient name: ");
                    String rec = scanner.nextLine();
                    System.out.println(MessageManager.searchByRecipient(rec));
                }
                case "4" -> System.out.println(MessageManager.displayLongestMessage());
                case "5" -> {
                    System.out.print("Enter message hash to delete: ");
                    String hash = scanner.nextLine();
                    System.out.println(MessageManager.deleteByHash(hash));
                }
                case "6" -> back = true;
                default  -> System.out.println("Invalid option.");
            }
        }
    }
}

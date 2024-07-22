package com.example.chatapplication;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ChatController extends ChatGui implements ChatConnectionInterface {

    protected Socket socket;
    protected PrintWriter out;
    protected BufferedReader in;
    protected TextArea messageArea;

    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 5001;

    public ChatController() {
    }

    @Override
    public void initializeConnection(TextArea messageArea) {
    }
    @Override
    public void connectToServer() {
    }
    @Override
    public void readMessagesFromServer() {
    }
    @Override
    public void setupCloseRequest() {
    }


    @FXML
    @Override
    protected void initialize() {
        initializeConnection(messageArea);
    }

    @FXML
    public void onHelloButtonClick() {
        name = inputName.getText();
        if (name.isEmpty()) {
            welcomeText.setText("Welcome to JavaFX Application!");
        } else {
            welcomeText.setText("Welcome, " + name + "!");
        }
    }

    @FXML
    protected void onSendButtonClick() {
        String message = inputField.getText();
        String sendingMessage;

        // Check if name is initialized and not empty
        if (name != null && !name.isEmpty()) {
            sendingMessage = name + ": " + message;
        } else {
            sendingMessage = "Anonymous User: " + message;
        }

        // Send the message to the server
        if (out != null) {
            out.println(sendingMessage);
        }

        // Clear the input field after sending the message
        inputField.clear();
    }

    
    @SuppressWarnings("exports")
    public TextArea getMessageArea() {
        return messageArea;
    }
}

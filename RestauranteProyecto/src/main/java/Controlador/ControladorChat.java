/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
/**
 *
 * @author Gfmt
 */
public class ControladorChat {


public class ChatApp {
    private static final int PORT = 12345;
    private static ArrayList<PrintWriter> clientWriters = new ArrayList<>();
    
    // GUI Components
    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;
    private JButton sendButton;

    public ChatApp() {
        // Setup GUI
        frame = new JFrame("Chat Application");
        textArea = new JTextArea(20, 50);
        textField = new JTextField(40);
        sendButton = new JButton("Send");

        textArea.setEditable(false);
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.add(textField);
        panel.add(sendButton);
        frame.add(panel, BorderLayout.SOUTH);

        // Send button action
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void sendMessage() {
        String message = textField.getText();
        if (!message.isEmpty()) {
            textArea.append("You: " + message + "\n");
            textField.setText("");
            // Broadcast message to all clients
            broadcastMessage(":User  " + message);
        }
    }

    private void broadcastMessage(String message) {
        for (PrintWriter writer : clientWriters) {
            writer.println(message);
        }
    }

    // Server method
    public static void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                clientWriters.add(out);
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Client handler
    private static class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    System.out.println("Received: " + message);
                    // Here you can add logic to display the message in the GUI
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        // Start the server in a new thread
        new Thread(ChatApp::startServer).start();
        // Start the chat application GUI
        //SwingUtilities.invokeLater(ChatApp::new);
    }
}
    
}

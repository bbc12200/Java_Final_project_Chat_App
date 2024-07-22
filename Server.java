import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 5001;
    private static Set<PrintWriter> clients = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Server is running...");
        try (ServerSocket listener = new ServerSocket(PORT)) {
            while (true) {
                new ClientHandler(listener.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addClient(PrintWriter out) {
        synchronized (clients) {
            clients.add(out);
            broadcast("A new client has connected.");
        }
    }

    private static void removeClient(PrintWriter out) {
        synchronized (clients) {
            clients.remove(out);
        }
    }

    private static void broadcast(String message) {
        synchronized (clients) {
            for (PrintWriter client : clients) {
                client.println(message);
            }
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                setupStreams();
                addClient(out);
                handleClientMessages();
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                cleanup();
            }
        }

        private void setupStreams() throws IOException {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        }

        private void handleClientMessages() throws IOException {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received: " + message);
                broadcast(message);
            }
        }

        private void cleanup() {
            if (out != null) {
                removeClient(out);
            }
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}

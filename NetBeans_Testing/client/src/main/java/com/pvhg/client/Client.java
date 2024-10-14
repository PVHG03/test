package com.pvhg.client;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Server address
        int port = 5123; // Server port
        String filePath = "client_files/hello-world.txt"; // Path to the file you want to send

        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("Connected to the server.");

            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("File not found: " + filePath);
                return;
            }

            // Send file metadata to server
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(outputStream);
            dos.writeUTF(file.getName());   // Send file name
            dos.writeLong(file.length());   // Send file size

            // Send file content to server
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            System.out.println("File sent: " + filePath);
        } catch (IOException e) {
            System.err.println("Error connecting to the server: " + e.getMessage());
        }
    }
}

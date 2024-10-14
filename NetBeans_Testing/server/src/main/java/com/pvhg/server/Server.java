/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.pvhg.server;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phamh
 */
public class Server {

    public static void main(String[] args) {
        int port = 5123;
        try (ServerSocket serverSocker = new ServerSocket(port)) {
            System.out.println("Server has started at " + port + " and is waiting for a client");
            
            while (true) {
                try (Socket socket = serverSocker.accept()) {
                    System.out.println("Client connected: " + socket.getInetAddress());
                    
                    InputStream is = socket.getInputStream();
                    DataInputStream dis = new DataInputStream(is);
                    String filename = dis.readUTF();
                    long filesize = dis.readLong();
                    
                    File file = new File("Server_Files/" + filename);
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        long totalByteRead = 0;
                        
                        while ((bytesRead = is.read(buffer)) > 0) {
                            fos.write(buffer, 0, bytesRead);
                            totalByteRead += bytesRead;
                            if (totalByteRead >= filesize) {
                                break;
                            }
                        }
                    }
                    
                    System.out.println("File received: " + filename);
                } catch (IOException e) {
                    System.err.println("Error handling client connection: " + e.getMessage());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Server failed to start: " + ex.getMessage());
        }
    }
}

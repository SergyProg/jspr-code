package ru.netology;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server extends Thread{
    public final List<String> validPaths = List.of("/index.html", "/spring.svg", "/spring.png", "/resources.html", "/styles.css", "/app.js", "/links.html", "/forms.html", "/classic.html", "/events.html", "/events.js");
    public static final String RESOURCE_DIR = "01_web\\http-server\\public";
    private static ServerSocket serverSocket = null;
    public static final int DEFAULT_PORT = 23445; //9999;

    Server(int port){
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Сервер стартовал, порт: "
                    + serverSocket.getLocalPort() + "\n");
        }
        catch (IOException ex) {
            System.out.println("Ошибка при старте сервера, порт: " + port);
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        Socket clientSocket = null;
        try {
            while (true) {
                clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket, this);
                new Thread(client).start();
            }
        } catch (IOException ex) {
            System.out.println("Ошибка при установке соединения с сервером.");
            ex.printStackTrace(System.out);
        }
    }
}

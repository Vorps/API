package net.vorps.api.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Project Updater Created by Vorps on 04/09/2016 at 12:29.
 */

public class NetWorkServer {

    private static ServerSocket server;
    private static boolean isRunning;

    public static void open(String host, int port, Action action) throws IOException {
        NetWorkServer.isRunning = true;
        NetWorkServer.server = new ServerSocket(port, 100, InetAddress.getByName(host));
        new Thread(() -> {
            try {
                while(NetWorkServer.isRunning) {
                    Socket socket = NetWorkServer.server.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String[] data = in.readLine().split(":");
                    action.action(data[0], Integer.parseInt(data[1]), ServerState.valueOf(data[2]));
                    socket.close();
                }
                NetWorkServer.server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void close(){
        NetWorkServer.isRunning = false;
    }
}
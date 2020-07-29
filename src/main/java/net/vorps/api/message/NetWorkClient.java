package net.vorps.api.message;

import net.vorps.api.API;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Project Updater Created by Vorps on 03/09/2016 at 18:49.
 */
public class NetWorkClient {

    private static Socket connexion;

    public static void sendData(){
        new Thread(() -> {
            try {
                NetWorkClient.connexion = new Socket("localhost", 6666);
                PrintWriter writer = new PrintWriter(NetWorkClient.connexion.getOutputStream(), true);
                System.out.println(API.getName()+":"+API.getPlugin().getServer().getPort()+":"+ServerState.getState().toString());
                writer.write(API.getName()+":"+API.getPlugin().getServer().getPort()+":"+ServerState.getState().toString());
                writer.close();
                NetWorkClient.connexion.close();
            } catch (IOException e){
                e.printStackTrace();
            }

        }).start();
    }
}
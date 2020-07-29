package net.vorps.api.utils;

import java.io.*;

/**
 * Project Updater Created by Vorps on 03/09/2016 at 14:15.
 */
public class Serialize {

    /**
     * Serialisation data
     * @param object Object
     * @param path String
     */
    public static void serializable(Object object, String path) throws IOException{
        ObjectOutputStream objectInputStream = new ObjectOutputStream(new FileOutputStream(path));
        objectInputStream.writeObject(object);
        objectInputStream.close();
    }

    /**
     * Return object serializable
     * @param path String
     * @return Object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object deSerialisation(String path) throws IOException, ClassNotFoundException{
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path));
        Object object = objectInputStream.readObject();
        objectInputStream.close();
        return object;
    }
}

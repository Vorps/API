package net.vorps.api.threads;

/**
 * Project API Created by Vorps on 18/03/2017 at 15:10.
 */
public class Thread extends java.lang.Thread {

    private final ThreadAction threadAction;
    private final int time;

    public Thread(ThreadAction threadAction, int time){
        this.threadAction = threadAction;
        this.time = time;
        this.start();
    }

    public void run(){
        try {
            Thread.sleep(this.time*1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        this.threadAction.run();
    }
}

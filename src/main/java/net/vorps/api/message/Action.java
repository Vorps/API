package net.vorps.api.message;

public interface Action {

    void action(String name, int port, ServerState serverState);
}

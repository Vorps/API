package net.vorps.api.commands;

import org.junit.jupiter.api.Test;

class CommandTest {

    @Test
    void test() {
        Command command = new Command("Test", 0, CommandImp.class);
    }
}

class CommandImp{

    public static void test(CommandSender commandSender, int test){
        System.out.println("Test2 "+test);
    }
}
package net.vorps.api.data;
import lombok.Getter;
import net.vorps.api.players.InitFunction;

public abstract class SettingCore{

    protected static InitFunction initFunction;
    private static @Getter String consoleLang;
    private static @Getter String console;
    private static @Getter String serverName;

    public static void initSettings(){
        SettingCore.consoleLang = Settings.getSettings("console_lang").getMessage();
        SettingCore.console = Settings.getSettings("console_name").getMessage();
        SettingCore.serverName = Settings.getSettings("server_name").getMessage();
        System.out.println("OKKKK");
        if(SettingCore.initFunction != null){
            System.out.println("OKKKK");
            SettingCore.initFunction.init();
        }
    }

}

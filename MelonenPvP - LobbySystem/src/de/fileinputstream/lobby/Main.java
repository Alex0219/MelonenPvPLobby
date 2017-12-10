package de.fileinputstream.lobby;

import de.fileinputstream.lobby.commands.*;
import de.fileinputstream.lobby.files.FileManager;
import de.fileinputstream.lobby.listeners.*;
import de.fileinputstream.lobby.protocol.AntiSpy;
import de.fileinputstream.lobby.rank.score.NameTags;
import de.fileinputstream.lobby.sql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public static MySQL mysql;

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        NameTags.initScoreboardTeams();
        mysql = new MySQL("", "", "", "");
        mysql.connect();
        mysql.update("CREATE TABLE IF NOT EXISTS Rank (UUID VARCHAR(100), RANG VARCHAR(100))");
        mysql.update("CREATE TABLE IF NOT EXISTS History (Typ VARCHAR(100), UUID VARCHAR(100), Grund VARCHAR(100), Von VARCHAR(100), Dauer VARCHAR(100), Datum VARCHAR(100), Name VARCHAR(100))");
        mysql.update("CREATE TABLE IF NOT EXISTS Bans (Spielername VARCHAR(100), UUID VARCHAR(100), Ende VARCHAR(100), Grund VARCHAR(100), Dauer VARCHAR(100), Banner VARCHAR(100) , BanID VARCHAR(100))");
        mysql.update("CREATE TABLE IF NOT EXISTS Mutes (Spielername VARCHAR(100), UUID VARCHAR(100), Ende VARCHAR(100), Grund VARCHAR(100), Dauer VARCHAR(100), Muter VARCHAR(100))");
        new FileManager().createDirecories();
        loadCommands();
        loadListeners();
        setupBanandMute();

    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public void loadCommands() {
        getCommand("spawn").setExecutor(new CommandSpawn());
        getCommand("setspawn").setExecutor(new CommandSetSpawn());
        getCommand("warp").setExecutor(new CommandWarp());
        getCommand("setwarp").setExecutor(new CommandSetWarp());
        getCommand("delwarp").setExecutor(new CommandDelWarp());

        List<String> aliases = new ArrayList<String>();
        aliases.add("ts");

        getCommand("teamspeak").setExecutor(new CommandTeamspeak());
        getCommand("ts").setExecutor(new CommandTeamspeak());
        getCommand("reloadplugins").setExecutor(new CommandReloadPlugins());
        getCommand("rank").setExecutor(new CommandRank());

        ExtrernalCommands commands = new ExtrernalCommands();
        getCommand("gamemode").setExecutor(commands);
        getCommand("vanish").setExecutor(commands);
        getCommand("tp").setExecutor(commands);
        getCommand("fly").setExecutor(commands);
        getCommand("build").setExecutor(new CommandBuild());
        getCommand("lobby").setExecutor(new CommandSpawn());
        getCommand("l").setExecutor(new CommandSpawn());
        getCommand("hub").setExecutor(new CommandSpawn());
        getCommand("cc").setExecutor(new CommandCC());
        getCommand("skull").setExecutor(new CommandSkull());
        getCommand("schafmodus").setExecutor(new CommandSchafModus());

    }

    public void loadListeners() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PlayerChatListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new AntiSpy(), this);
        pm.registerEvents(new SignInteractListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new PlayerLoginListener(),this);
    }

    public static Main getInstance() {
        return instance;
    }

    public void setupBanandMute() {

        getCommand("ban").setExecutor(new CommandBan());
        getCommand("unban").setExecutor(new CommandUnban());
        getCommand("history").setExecutor(new CommandHistory());
        getCommand("check").setExecutor(new CommandCheck());
        getCommand("mute").setExecutor(new CommandMute());
        getCommand("tempban").setExecutor(new CommandTempBan());
        getCommand("unmute").setExecutor(new CommandUnmute());
        getCommand("tempmute").setExecutor(new CommandTempMute());
        getCommand("checkbanid").setExecutor(new CommandCheckBanID());


    }


}

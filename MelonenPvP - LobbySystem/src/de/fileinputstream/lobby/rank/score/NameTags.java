package de.fileinputstream.lobby.rank.score;

import de.fileinputstream.lobby.rank.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class NameTags {

    private static HashMap<String, Object> teams = new HashMap<>();


    public static void updateTeams() {
        for (Map.Entry entry : teams.entrySet()) {
            Object packet = teams.get(entry.getKey());

            for (Player all : Bukkit.getOnlinePlayers()) {
                try {
                    Constructor<?> scoreboardTeamConstructor = getNMSClass("PacketPlayOutScoreboardTeam")
                            .getConstructor(getNMSClass("ScoreboardTeam"), int.class);

                    sendPacket(all, scoreboardTeamConstructor.newInstance(entry.getValue(), 1));

                    sendPacket(all, scoreboardTeamConstructor.newInstance(entry.getValue(), 0));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }


    public static void addToTeam(Player player) {
        teams.entrySet().forEach(entry -> {
            Object packet = teams.get(entry.getKey());
            try {
                Field f = packet.getClass().getDeclaredField("c");
                f.setAccessible(true);
                Set<String> list = new HashSet<>();
                list.addAll((Collection<? extends String>) f.get(packet));
                if (list.contains(player.getName())) {
                    list.remove(player.getName());
                    setField(packet, "c", list);
                    teams.put(entry.getKey(), packet);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        if (RankManager.getRank(player.getUniqueId().toString()).equalsIgnoreCase("admin".toLowerCase())) {
            setPlayer(player, "admin");
            player.setPlayerListName("§4Admin §7● §4" + player.getName());
            return;
        } else if(RankManager.getRank(player.getUniqueId().toString()).equalsIgnoreCase("owner".toLowerCase())) {
            setPlayer(player, "owner");
            player.setPlayerListName("§4Owner §7● §4" + player.getName());
            return;
        } else if(RankManager.getRank(player.getUniqueId().toString()).equalsIgnoreCase("dev".toLowerCase())) {
            setPlayer(player, "dev");
            player.setPlayerListName("§3Dev §7● §3" + player.getName());
            return;
        } else  if(RankManager.getRank(player.getUniqueId().toString()).equalsIgnoreCase("sup".toLowerCase())) {
            setPlayer(player, "sup");
            player.setPlayerListName("§2Sup §7● §2" + player.getName());
            return;
        } else  if(RankManager.getRank(player.getUniqueId().toString()).equalsIgnoreCase("builder".toLowerCase())) {
            setPlayer(player, "builder");
            player.setPlayerListName("§eBuilder §7● §e" +player.getName());
            return;
        } else  if(RankManager.getRank(player.getUniqueId().toString()).equalsIgnoreCase("youtuber".toLowerCase())) {
            setPlayer(player, "youtube");
            player.setPlayerListName("§5YouTuber §7● §5" + player.getName());
            return;
        } else  if(RankManager.getRank(player.getUniqueId().toString()).equalsIgnoreCase("premium".toLowerCase())) {
            setPlayer(player, "premium");
            player.setPlayerListName("§6" + player.getName());
            return;
        } else if(RankManager.getRank(player.getUniqueId().toString()).equalsIgnoreCase("spieler".toLowerCase())) {
            setPlayer(player, "spieler");
            player.setPlayerListName("§7" + player.getName());
            return;
        } else if(RankManager.getRank(player.getUniqueId().toString()).equalsIgnoreCase("premiumplus".toLowerCase())) {
            setPlayer(player, "premiumplus");
            player.setPlayerListName("§6Premium+ §7● §6" + player.getName());
        }  else if(RankManager.getRank(player.getUniqueId().toString()).equalsIgnoreCase("mod".toLowerCase())) {
            setPlayer(player, "mod");
            player.setPlayerListName("§cMod §7● §c" + player.getName());
            return;
        }



    }

    public static void initScoreboardTeams() {
        String suffix = "§8┃ §7";

        try {
            Constructor<?> boardConstructor = getNMSClass("Scoreboard").getConstructor();
            Object board = boardConstructor.newInstance();


            init("00000001Owner", "owner", "§4Owner" + suffix, board);
            init("00000002Admin","admin","§4Admin" + suffix, board);
            init("00000002Dev","dev","§3Dev" + suffix, board);
            init("00000003Mod", "mod", "§cMod" + suffix, board);
            init("00000004Sup", "sup", "§2Sup" + suffix, board);
            init("00000005Builder", "builder", "§eBuilder" + suffix, board);
            init("00000006YouTube", "youtube", "§5YouTuber" + suffix, board);
            init("00000007Premium+", "premiumplus", "§6Premium+" + suffix, board);
            init("00000008Premium", "premium", "§6" + "", board);
            init("00000009Spieler", "spieler", "§7" + "", board);

        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§4Die Scoreboard Teams konnten nicht initialisiert werden!");
        }

    }

    private static void init(String teamname, String HashMapName, String prefix, Object board) {

        try {

            Constructor<?> teamConstructor = getNMSClass("ScoreboardTeam").getConstructor(getNMSClass("Scoreboard"),
                    String.class);

            Object packet = teamConstructor.newInstance(board, teamname);

            Object enumVisibility = getNMSClass("ScoreboardTeamBase").getDeclaredClasses()[0].getField("ALWAYS")
                    .get(null);
            setField(packet, "j", enumVisibility);

            setField(packet, "e", prefix);

            teams.put(HashMapName, packet);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static void setPlayer(Player p, String HashMapName) {
        Object packet = teams.get(HashMapName);
        try {
            Field f = packet.getClass().getDeclaredField("c");
            f.setAccessible(true);
            Set<String> list = new HashSet<>();
            list.addAll((Collection<? extends String>) f.get(packet));
            list.add(p.getName());
            setField(packet, "c", list);

            teams.put(HashMapName, packet);

            for (Map.Entry e : teams.entrySet()) {

                Constructor<?> scoreboardTeamConstructor = getNMSClass("PacketPlayOutScoreboardTeam")
                        .getConstructor(getNMSClass("ScoreboardTeam"), int.class);
                Object teampacket = scoreboardTeamConstructor.newInstance(e.getValue(), 0);
                sendPacket(p, teampacket);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setField(Object object, String fieldname, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldname);
            field.setAccessible(true);
            field.set(object, value);
            field.setAccessible(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        }

        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}

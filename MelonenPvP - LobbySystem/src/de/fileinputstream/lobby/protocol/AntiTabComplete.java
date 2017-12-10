package de.fileinputstream.lobby.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.FieldAccessException;
import de.fileinputstream.lobby.Main;

public class AntiTabComplete  {

    private ProtocolManager pm;

    public AntiTabComplete()
    {
        this.pm = ProtocolLibrary.getProtocolManager();

        setupPackets();
    }

    public void setupPackets()
    {
        this.pm.addPacketListener(new PacketAdapter(Main.getInstance(), ListenerPriority.NORMAL, new PacketType[] { PacketType.Play.Client.TAB_COMPLETE })
    {
        public void onPacketReceiving(PacketEvent event)
        {
            if (event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) {
                try
                {
                    PacketContainer packet = event.getPacket();

                    String message = ((String)packet.getSpecificModifier(String.class).read(0)).toLowerCase();
                    if (message.startsWith("/"))
                    {
                        if (event.getPlayer().hasPermission("lobby.bypass")) {
                            return;
                        }
                        event.setCancelled(true);
                    }
                }
                catch (FieldAccessException localFieldAccessException) {}
            }
        }
    });
    }
}

package me.weebo.utilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Clickable {
	
    List<TextComponent> components;
    
    public Clickable(String msg) {
        components = new ArrayList<TextComponent>();
        TextComponent message = new TextComponent(msg);
        components.add(message);
    }
    
    public Clickable(String msg, String hoverMsg, String clickString) {
        components = new ArrayList<TextComponent>();
        add(msg, hoverMsg, clickString);
    }
    
    public Clickable() {
        components = new ArrayList<TextComponent>();
    }
    
    public TextComponent add(String msg, String hoverMsg, String clickString) {
        TextComponent message = new TextComponent(msg);
        if (hoverMsg != null) {
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverMsg).create()));
        }
        if (clickString != null) {
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, clickString));
        }
        components.add(message);
        return message;
    }
    
    public void add(String message) {
        components.add(new TextComponent(message));
    }
    
    public void send(Player player) {
        player.spigot().sendMessage((BaseComponent[])asComponents());
    }
    
    public TextComponent[] asComponents() {
        return components.toArray(new TextComponent[0]);
    }
}

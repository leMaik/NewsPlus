package de.craften.plugins.newsplus;

import de.craften.plugins.newsplus.providers.NewsEntry;
import de.craften.plugins.newsplus.providers.RssFeedNewsProvider;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.logging.Level;

/**
 * A broadcaster that sends news to all players.
 */
class NewsBroadcaster extends BukkitRunnable {
    private final RssFeedNewsProvider provider;
    private final int count;
    private final String format;

    public NewsBroadcaster(RssFeedNewsProvider provider, int count, String format) {
        this.provider = provider;
        this.count = count;
        this.format = format;
    }

    @Override
    public void run() {
        try {
            for (NewsEntry entry : provider.getLatestNews(count)) {
                Bukkit.spigot().broadcast(formatNewsEntry(entry));
            }
        } catch (IOException e) {
            NewsPlus.getPlugin(NewsPlus.class).getLogger().log(Level.WARNING, "Could not read news from feed", e);
        }
    }

    private static String formatNewsEntry(String format, NewsEntry entry) {
        return ChatColor.translateAlternateColorCodes('&', format
                .replaceAll("\\{\\{title\\}\\}", entry.getTitle())
                //.replaceAll("\\{\\{date\\}\\}", entry.getDate().toString()) //TODO add formatted date
                .replaceAll("\\{\\{link\\}\\}", entry.getLink())
                .replaceAll("\\{\\{content\\}\\}", entry.getContent())
        );
    }

    public void broadcastTo(Player player) {
        try {
            for (NewsEntry entry : provider.getLatestNews(count)) {
                player.spigot().sendMessage(formatNewsEntry(entry));
            }
        } catch (IOException e) {
            NewsPlus.getPlugin(NewsPlus.class).getLogger().log(Level.WARNING, "Could not read news from feed", e);
        }
    }

    private TextComponent formatNewsEntry(NewsEntry entry) {
        TextComponent message = new TextComponent(TextComponent.fromLegacyText(formatNewsEntry(format, entry)));
        if (entry.getLink() != null) {
            message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, entry.getLink()));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to open in your browser.").create()));
        }
        return message;
    }
}

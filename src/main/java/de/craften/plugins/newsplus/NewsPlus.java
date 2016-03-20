package de.craften.plugins.newsplus;

import de.craften.plugins.newsplus.providers.RssFeedNewsProvider;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A plugin that shows news ingame.
 */
public class NewsPlus extends JavaPlugin {
    private List<NewsBroadcaster> news;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        news = new ArrayList<>();

        for (Map newsProviderConfig : getConfig().getMapList("news")) {
            if ("rss".equalsIgnoreCase(newsProviderConfig.get("type").toString())) {
                RssFeedNewsProvider provider;
                try {
                    provider = new RssFeedNewsProvider(new URL(newsProviderConfig.get("url").toString()));
                } catch (MalformedURLException e) {
                    getLogger().warning("Invalid URL for news feed: " + newsProviderConfig.get("url"));
                    break;
                }

                int count = Integer.parseInt(newsProviderConfig.get("count").toString());
                String format = newsProviderConfig.get("format").toString();
                long period = Long.parseLong(newsProviderConfig.get("interval").toString()) * 60 * 20;

                final NewsBroadcaster broadcaster = new NewsBroadcaster(provider, count, format);
                news.add(broadcaster);
                if (period > 0) {
                    broadcaster.runTaskTimerAsynchronously(this, 0, period);
                }
                if (Boolean.valueOf(String.valueOf(newsProviderConfig.get("sendOnJoin"))) == Boolean.TRUE) {
                    getServer().getPluginManager().registerEvents(new Listener() {
                        @EventHandler
                        public void onJoin(final PlayerJoinEvent event) {
                            getServer().getScheduler().runTaskAsynchronously(NewsPlus.this, new Runnable() {
                                @Override
                                public void run() {
                                    broadcaster.broadcastTo(event.getPlayer());
                                }
                            });
                        }
                    }, this);
                }
            } else {
                getLogger().warning("Unsupported news source type: " + newsProviderConfig.get("type"));
            }
        }
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && command.getName().equals("news")) {
            getServer().getScheduler().runTaskAsynchronously(NewsPlus.this, new Runnable() {
                @Override
                public void run() {
                    for (NewsBroadcaster broadcaster : news) {
                        broadcaster.broadcastTo((Player) sender);
                    }
                }
            });
            return true;
        }
        return false;
    }
}

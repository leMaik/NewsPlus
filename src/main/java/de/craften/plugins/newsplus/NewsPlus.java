package de.craften.plugins.newsplus;

import de.craften.plugins.newsplus.providers.RssFeedNewsProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * A plugin that shows news ingame.
 */
public class NewsPlus extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();

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

                new NewsBroadcaster(provider, count, format).runTaskTimerAsynchronously(this, 0, period);
            } else {
                getLogger().warning("Unsupported news source type: " + newsProviderConfig.get("type"));
            }
        }
    }
}

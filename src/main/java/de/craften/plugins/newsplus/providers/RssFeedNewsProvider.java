package de.craften.plugins.newsplus.providers;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;

/**
 * A news provider that grabs news from an RSS feed.
 */
public class RssFeedNewsProvider implements NewsProvider {
    private final URL feedUrl;

    /**
     * Creates a new news provider for the given RSS feed.
     *
     * @param feedUrl URL of an RSS feed
     */
    public RssFeedNewsProvider(URL feedUrl) {
        this.feedUrl = feedUrl;
    }

    @Override
    public Collection<NewsEntry> getLatestNews(int count) {
        return Collections.emptyList(); //TODO
    }
}

package de.craften.plugins.newsplus.providers;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
    public Collection<NewsEntry> getLatestNews(int count) throws IOException {
        SyndFeedInput input = new SyndFeedInput();
        try {
            SyndFeed feed = input.build(new XmlReader(feedUrl));
            List<SyndEntry> entries = feed.getEntries();
            List<NewsEntry> newsEntries = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                if (entries.size() <= i) {
                    break;
                }

                final SyndEntry entry = entries.get(i);
                newsEntries.add(new NewsEntry() {
                    @Override
                    public String getTitle() {
                        return entry.getTitle();
                    }

                    @Override
                    public String getContent() {
                        return entry.getDescription().getValue();
                    }

                    @Override
                    public String getLink() {
                        return entry.getLink();
                    }

                    @Override
                    public Date getDate() {
                        return entry.getPublishedDate();
                    }
                });
            }
            return newsEntries;
        } catch (FeedException e) {
            throw new IOException("Could not fetch feed", e);
        }
    }
}

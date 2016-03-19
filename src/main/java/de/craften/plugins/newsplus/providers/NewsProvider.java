package de.craften.plugins.newsplus.providers;

import java.io.IOException;
import java.util.Collection;

/**
 * A provider for news.
 */
public interface NewsProvider {
    /**
     * Gets the latest news.
     *
     * @param count maximum number of news to get
     * @return latest news (may be less than <code>count</code>)
     */
    Collection<NewsEntry> getLatestNews(int count) throws IOException;
}

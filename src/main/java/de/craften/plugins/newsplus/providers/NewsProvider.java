package de.craften.plugins.newsplus.providers;

import java.util.Collection;

/**
 * A provider for news.
 */
public interface NewsProvider {
    /**
     * Gets the latest news.
     *
     * @param count maximum number of news to get
     * @return latest news (may be more or less than <code>count</code>)
     */
    Collection<NewsEntry> getLatestNews(int count);
}

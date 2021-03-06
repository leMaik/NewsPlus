package de.craften.plugins.newsplus.providers;

import java.util.Date;

/**
 * A news entry.
 */
public interface NewsEntry {
    /**
     * Gets the title of this entry.
     *
     * @return the title of this entry
     */
    String getTitle();

    /**
     * Gets the content of this entry.
     *
     * @return the content of this entry
     */
    String getContent();

    /**
     * Gets the link of this entry.
     *
     * @return the link of this entry
     */
    String getLink();

    /**
     * Get the date on which this news entry was published.
     *
     * @return the date on which this news entry was published
     */
    Date getDate();
}

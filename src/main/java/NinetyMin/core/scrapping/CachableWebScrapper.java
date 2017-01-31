package NinetyMin.core.scrapping;

/**
 * Created by orlavy on 1/31/17.
 */
public interface CachableWebScrapper<T> extends WebScrapper<T> {
    T reScrapeData();
}

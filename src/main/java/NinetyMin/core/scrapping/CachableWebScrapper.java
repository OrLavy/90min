package NinetyMin.core.scrapping;

/**
 * Created by orlavy on 1/31/17.
 */
public interface CachableWebScrapper<T,E> extends WebScrapper<T> {
    T reScrapeData();
    T reScrapeData(E reScrappingParameter);
}

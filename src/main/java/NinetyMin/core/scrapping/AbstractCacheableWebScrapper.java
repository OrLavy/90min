package NinetyMin.core.scrapping;

import jodd.http.HttpBrowser;
import jodd.http.HttpRequest;
import jodd.io.FileUtil;
import jodd.io.NetUtil;
import jodd.jerry.Jerry;
import jodd.util.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by orlavy on 1/30/17.
 */
public abstract class AbstractCacheableWebScrapper<T> implements CachableWebScrapper<T> {

    private final URL url;
    private final HttpBrowser browser;
    private final Jerry baseDocument;

    protected AbstractCacheableWebScrapper(URL url){
        this.url = url;
        this.browser = new HttpBrowser();
        this.baseDocument = this.downloadBaseDocument();
    }

    public URL getUrl() {
        return url;
    }

    protected HttpBrowser getBrowser() {
        return browser;
    }

    public Jerry getBaseDocument() {
        return baseDocument;
    }

    @Override
    public T scrapeData() {
        return this.scrapeFromDoc(this.crawlToTargetDocument(this.getBaseDocument()));
    }

    @Override
    public T reScrapeData() {
        return this.scrapeData();
    }

    /**
     * Returns the basic document found at this.url .
     * @return
     */
    protected Jerry downloadBaseDocument(){
        HttpRequest request = HttpRequest.get(this.getUrl().toString());
        browser.sendRequest(request);
        return Jerry.jerry(this.getBrowser().getPage());
    }

    /**
     * Allows child classes to implement a crawling mechanism if needed.
     * @param doc
     * @return
     */
    protected Jerry crawlToTargetDocument(Jerry doc){
        return doc;
    }

    protected abstract T scrapeFromDoc(Jerry doc);
}

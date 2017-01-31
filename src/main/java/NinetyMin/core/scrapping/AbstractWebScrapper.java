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
public abstract class AbstractWebScrapper<T> implements WebScrapper<T> {

    private final URL url;
    private final HttpBrowser browser;

    protected AbstractWebScrapper(URL url){
        this.url = url;
        this.browser = new HttpBrowser();
    }

    public URL getUrl() {
        return url;
    }

    protected HttpBrowser getBrowser() {
        return browser;
    }

    @Override
    public T scrapeData() throws IOException {
        return this.scrapeFromDoc(this.getDocumentFromWeb());
    }

    protected Jerry getDocumentFromWeb() throws IOException {
        return this.crawlToTargetDocument(this.getBaseDocument());
    }

    /**
     * Returns the basic document found at this.url .
     * @return
     */
    protected Jerry getBaseDocument(){
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

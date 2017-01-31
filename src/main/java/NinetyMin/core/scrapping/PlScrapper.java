package NinetyMin.core.scrapping;

import jodd.http.HttpBrowser;
import jodd.http.HttpRequest;
import jodd.jerry.Jerry;
import jodd.jerry.JerryFormHandler;
import jodd.jerry.JerryFunction;

import java.io.IOException;
import java.util.Map;

/**
 * Created by orlavy on 1/31/17.
 */
public class PlScrapper {

    public void doScrape() throws IOException {
        final HttpBrowser browser = new HttpBrowser();

        HttpRequest request = HttpRequest.get("http://www.bbc.com/sport/football/results");
        browser.sendRequest(request);

        String page = browser.getPage();
        Jerry doc = Jerry.jerry(page);

        doc.form("#filter-nav", new JerryFormHandler() {
            @Override
            public void onForm(Jerry form, Map<String, String[]> parameters) {
                for (Map.Entry<String,String[]> entry : parameters.entrySet()){
                    String[] values = entry.getValue();
                    String name = entry.getKey();
                    for (String value : values){
                        System.out.println(name + " : " + value);
                    }
                }

                String formAction = form.attr("action");
                HttpRequest loginRequest = HttpRequest.post("http://www.bbc.com/sport/football/results");

                loginRequest.form("filter","competition-118996114",true);
                browser.sendRequest(loginRequest);
            }
        });


        Jerry newDoc = Jerry.jerry(browser.getPage());
        newDoc.$(".team-home").each(new JerryFunction() {
            @Override
            public Boolean onNode(Jerry $this, int index) {
                System.out.println($this.text());

                return true;
            }
        });
    }
}

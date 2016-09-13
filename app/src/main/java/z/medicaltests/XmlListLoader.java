package z.medicaltests;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Created by Жаров on 08.09.2016.
 */
public class XmlListLoader {
    private String FileName = "";
    private String Names[],Files[], Test[];
    public String Example;
    private static final String TAG = "XmlListLoaderLogs";
    XmlListLoader(String message, AssetManager assetManager) {
        FileName = message;
        try {
           // AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(FileName + ".xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);


            NodeList Properties = doc.getElementsByTagName("properties");
            Node node = Properties.item(0);
            Element el = (Element) node;
            String it = el.getAttribute("it");


            NodeList nList = doc.getElementsByTagName("item");

            Names = new String[nList.getLength()];
            Files = new String[nList.getLength()];
            Test = new String[nList.getLength()];
            //Test[0] = it;

            for(int i = 0; i< nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Names[i] = nNode.getTextContent();
                    Element element = (Element) nNode;

                     if(it.equals("subjects")) {
                        Files[i] = element.getAttribute("next");
                      }
                      else {
                          Test[i] = element.getAttribute("test");
                      }

                }
            }
        }
        catch (Exception e){
            Log.v(TAG, "Error while loading...");
        }
    }

    public String[] getNames() {
        return Names;
    }
    public String[] getFiles() {return Files; }
    public String[] getTest() {return Test; }
}

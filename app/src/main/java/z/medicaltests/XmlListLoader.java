package z.medicaltests;

import android.content.res.AssetManager;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


 class XmlListLoader {
    private String Names[],Files[], Test[];
    //public String Example;
    private static final String TAG = "XmlListLoaderLogs";

    XmlListLoader(String message, AssetManager assetManager) {
        try {
           // AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(message + ".xml");

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
            Log.v(TAG, "Here");
            for(int i = 0; i< nList.getLength(); i++) {
                Node nNode = nList.item(i);
                Log.v(TAG, Integer.toString(i));
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Names[i] = nNode.getTextContent();
                    Element element = (Element) nNode;
                    Log.v(TAG, Names[i]);

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

     String[] getNames() {return Names;}
     String[] getFiles() {return Files; }
    public String[] getTest() {return Test; }
}

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

/**
 * Created by Жаров on 14.09.2016.
 */
public class XmlTestLoader {
    private String FileName;
    private int iSize;
    private String size;
    private static final String TAG = "TestLoader";
    XmlTestLoader(String fileName, AssetManager assetManager) {
        FileName = fileName;
        try{
            InputStream is = assetManager.open(FileName + ".xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);

            NodeList Properties = doc.getElementsByTagName("quize");
            Node node = Properties.item(0);
            Element el = (Element) node;
            size = el.getAttribute("size");

            iSize = Integer.parseInt(size);

        }
        catch (Exception e){
            Log.v(TAG, "Error while loading...");}
    }

    public int getSize() {return iSize;}
    public String getS() {return size;}
}

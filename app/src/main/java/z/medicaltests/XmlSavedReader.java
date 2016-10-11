package z.medicaltests;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Жаров on 11.10.2016.
 */

class SavedBundle {
    private String Path;
    private int Number;
    private boolean Show;
    private double RightAnswers;
    private int maxSize;
    private String Name;


    public void setPath(String Path) {this.Path = Path;}
    public void setNumber(int Number) {this.Number = Number;}
    public void setShow(boolean Show) {this.Show = Show;}
    public void setRightAnswers(double RightAnswers) {this.RightAnswers = RightAnswers;}
    public void setMaxSize(int maxSize) {this.maxSize = maxSize;}
    public void setName(String Name) {this.Name = Name;}

    public String getPath() {return Path;}
    public int getNumber() {return Number;}
    public boolean getShow() {return Show;}
    public double getRightAnswers() {return RightAnswers;}
    public int getMaxSize() {return maxSize;}
    public String getName() {return Name;}

}

 class XmlSavedReader {

     private SavedBundle bundle[];
    private  final String filePath = "Save_massive.xml";
    private static final String TAG = "XmlSavedReader";

    XmlSavedReader(Context context) {

        try {

            InputStream is = context.openFileInput(filePath);
            int Iterator = 0;

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            Log.v(TAG, "First");
            Node node = doc.getFirstChild();
            String allString = ((Element) node).getAttribute("all");
            int allInt = Integer.parseInt(allString);
            Log.v(TAG, "Second" + " " + Integer.toString(allInt));
            bundle = new SavedBundle[allInt];
            for(int class_size = 0; class_size < bundle.length; class_size++) {
                bundle[class_size] = new SavedBundle();
            }

            NodeList nList = doc.getElementsByTagName("unit");
            Log.v(TAG, "Third" + " " + Integer.toString(nList.getLength()));
            for(int i = 0; i < nList.getLength(); i++) {


                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    NodeList nList_Second = nNode.getChildNodes();

                    for(int j = 0; j<nList_Second.getLength(); j++) {

                        Node nNode_Second = nList_Second.item(j);

                        if(nNode_Second.getNodeType() == Node.ELEMENT_NODE) {

                            if (j == 0) {
                                bundle[Iterator].setPath(nNode_Second.getTextContent());
                                Log.v(TAG, "Path " + nNode_Second.getTextContent());
                            }
                            if (j == 1) {
                                bundle[Iterator].setNumber(Integer.parseInt(nNode_Second.getTextContent()));
                                Log.v(TAG, "Number " + nNode_Second.getTextContent());
                                //continue;
                            }
                            if (j == 2) {
                                bundle[Iterator].setShow(Boolean.parseBoolean(nNode_Second.getTextContent()));
                                Log.v(TAG, "Show " + nNode_Second.getTextContent());
                                //continue;
                            }
                            if (j == 3) {
                                bundle[Iterator].setRightAnswers(Double.parseDouble(nNode_Second.getTextContent()));
                                Log.v(TAG, "Right " + nNode_Second.getTextContent());
                                //continue;
                            }
                            if (j == 4) {
                                bundle[Iterator].setMaxSize(Integer.parseInt(nNode_Second.getTextContent()));
                                Log.v(TAG, "Max " + nNode_Second.getTextContent());
                            }
                            if (j == 5) {
                                bundle[Iterator].setName(nNode_Second.getTextContent());
                                Log.v(TAG, "Name " + nNode_Second.getTextContent());
                            }
                        }
                    }

                }

                Iterator++;
            }


        }
        catch (Exception e) {
            Log.v(TAG, e.getMessage());
        }
    }

    public SavedBundle[] getBundle() {return bundle;}
}

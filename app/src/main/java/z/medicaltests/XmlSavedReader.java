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

class SavedBundle {
    private String Path;
    private int Number;
    private boolean Show;
    private double RightAnswers;
    private int maxSize;
    private String Name;
    private int ID;
    private int[] Massive;
    private int[] Mistakes;


    void setPath(String Path) {this.Path = Path;}
    void setNumber(int Number) {this.Number = Number;}
    void setShow(boolean Show) {this.Show = Show;}
    void setRightAnswers(double RightAnswers) {this.RightAnswers = RightAnswers;}
    void setMaxSize(int maxSize) {this.maxSize = maxSize;}
    public void setName(String Name) {this.Name = Name;}
    void setID(int ID){this.ID=ID;}
    void setMassive(int Massive[]){this.Massive=Massive;}
    void setMistakes(int Mistakes[]){this.Mistakes=Mistakes;}

    String getPath() {return Path;}
    int getNumber() {return Number;}
    boolean getShow() {return Show;}
    double getRightAnswers() {return RightAnswers;}
    int getMaxSize() {return maxSize;}
    public String getName() {return Name;}
    int getID(){return ID;}
    int[] getMassive() {return Massive;}
    int[] getMistakes() {return Mistakes;}
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
            /*
            Log.v(TAG, "First");
            Node node = doc.getFirstChild();
            String allString = ((Element) node).getAttribute("all");
            Log.v(TAG, "COUNT " + (allString));
            int allInt = Integer.parseInt(allString);

            bundle = new SavedBundle[allInt];
            for(int class_size = 0; class_size < bundle.length; class_size++) {
                bundle[class_size] = new SavedBundle();
            }*/

            NodeList nList = doc.getElementsByTagName("unit");

            int allInt = nList.getLength();

            bundle = new SavedBundle[allInt];
            for(int class_size = 0; class_size < bundle.length; class_size++) {
                bundle[class_size] = new SavedBundle();
            }


            Log.v(TAG, "Third" + " " + Integer.toString(nList.getLength()));
            for(int i = 0; i < nList.getLength(); i++) {


                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    int ID = Integer.parseInt(((Element) nNode).getAttribute("id"));
                    bundle[Iterator].setID(ID);

                    NodeList nList_Second = nNode.getChildNodes();

                    for(int j = 0; j<nList_Second.getLength(); j++) {

                        Node nNode_Second = nList_Second.item(j);

                        Log.v(TAG, "Nodes " + nList_Second.getLength());

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
                            if(j==6) {
                                Element element = (Element) nNode_Second;
                                String SIZE = element.getAttribute("size");
                                Log.v(TAG, SIZE);

                                int[] Massive = new int[Integer.parseInt(SIZE)];
                                NodeList nList_Massive = element.getElementsByTagName("element");

                                Log.v(TAG, Integer.toString(nList_Massive.getLength()));

                                int iter = 0;
                                for (int q = 0; q < nList_Massive.getLength(); q++) {
                                    Node nNode_Massive = nList_Massive.item(q);

                                    if (nNode_Second.getNodeType() == Node.ELEMENT_NODE) {

                                        Element element_Massive = (Element) nNode_Massive;
                                        Log.v(TAG, "Massive ");
                                        Log.v(TAG, element_Massive.getTextContent());
                                        Massive[iter] = Integer.parseInt(element_Massive.getTextContent());
                                        bundle[Iterator].setMassive(Massive);
                                        iter++;
                                    }

                                }

                            }
                            if(j==7) {

                                Element element = (Element) nNode_Second;
                                String SIZE = element.getAttribute("size");
                                Log.v(TAG+"Nodes", "Start");
                                Log.v(TAG+"Nodes", SIZE);

                                int[] Massive = new int[Integer.parseInt(SIZE)];

                                if(Integer.parseInt(SIZE) != 0) {

                                    NodeList nList_Massive = element.getElementsByTagName("element");
                                    int iter = 0;
                                    for (int q = 0; q < nList_Massive.getLength(); q++) {
                                        Node nNode_Massive = nList_Massive.item(q);

                                        if (nNode_Second.getNodeType() == Node.ELEMENT_NODE) {

                                            Element element_Massive = (Element) nNode_Massive;
                                            Log.v(TAG+"Nodes", element_Massive.getTextContent());
                                            Massive[iter] = Integer.parseInt(element_Massive.getTextContent());
                                            bundle[Iterator].setMistakes(Massive);
                                            iter++;
                                        }
                                    }
                                }
                                else {
                                    Log.v(TAG+"Nodes", "Empty");
                                    bundle[Iterator].setMistakes(null);
                                }
                            }
                        }
                    }

                }

                Iterator++;
            }


        }
        catch (Exception e) {
            Log.v(TAG, "Error loading" + " " + e.getMessage() + " " +
            " " + e.getCause());
        }
    }

    public SavedBundle[] getBundle() {return bundle;}
}

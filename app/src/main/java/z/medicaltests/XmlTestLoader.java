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
class XmlTestLoader {
    private String FileName;
    private int iSize;
    private String size;
    private TestStructure Questions[];
    private static final String TAG = "TestLoader";
    private String Path;
    private String Name;
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

    XmlTestLoader(String fileName, AssetManager assetManager, int mass[]) {
        FileName = fileName;
        int AbstractSize = 0;
        TestStructure allQuestions[] = new TestStructure[mass.length];

        try {
            InputStream is = assetManager.open(FileName + ".xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);

            NodeList quize = doc.getElementsByTagName("quize");
            Node node = quize.item(0);
            Element el = (Element) node;
            Path = el.getAttribute("file");
            Name = el.getAttribute("name");


            NodeList nList = doc.getElementsByTagName("question");

            main:
            for (int i = 0; i < nList.getLength(); i++) {

                if (AbstractSize == mass.length) break;
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;

                    String ID = element.getAttribute("id");
                    int id = Integer.parseInt(ID);
                    boolean flag = false;

                    //Log.v(TAG, "ОШИБКА " + Integer.toString(mass.length));
                    for (int j = 0; j < mass.length; j++) {

                        //Log.v(TAG, "ОШИБКА2 " + Integer.toString(id) + " " + Integer.toString(mass[j]));
                        if (mass[j] == id) {
                            flag = true;
                            //Log.v(TAG, "ОШИБКА3 " + Integer.toString(id));
                        }

                        if (flag) break;
                    }

                    if (!flag) continue;


                    String TYPE = element.getAttribute("type");
                    int type = Integer.parseInt(TYPE);

                    String SIZE = element.getAttribute("size");
                    int size = Integer.parseInt(SIZE);

                    //Первый тип вопросов
                    if (type == 1) {
                        String Text = "";
                        String Options[] = new String[size];
                        boolean Flag[] = new boolean[size];

                        TestCheckBox question = new TestCheckBox();

                        NodeList nList_Second = nNode.getChildNodes();

                        Log.v(TAG, SIZE);

                        int mass_size = 0;
                        for (int q = 0; q < nList_Second.getLength(); q++) {
                            Node nNode_Second = nList_Second.item(q);

                            if (nNode_Second.getNodeType() == Node.ELEMENT_NODE) {
                                boolean it = nNode_Second.getNodeName().equals("text");
                                if (it) {
                                    Text = nNode_Second.getTextContent();
                                } else {
                                    Options[mass_size] = nNode_Second.getTextContent();
                                    Element element_Second = (Element) nNode_Second;
                                    String s_Flag = element_Second.getAttribute("flag");

                                    Flag[mass_size] = !(s_Flag.equals("0"));

                                    /*
                                    if (s_Flag.equals("0")) {
                                        Flag[mass_size] = false;
                                    } else {
                                        Flag[mass_size] = true;
                                    }*/


                                    Log.v(TAG, Options[mass_size] + " " + Flag[mass_size]);
                                    mass_size++;
                                }

                            }
                        }

                        Log.v(TAG, "2");

                        question.setType(type);
                        question.setFlags(Flag);
                        question.setOptions(Options);
                        question.setText(Text);

                        allQuestions[AbstractSize] = question;
                        AbstractSize++;
                    }
                }
            }
            Questions = allQuestions;
        } catch (Exception e) {
            Log.v(TAG, "Error while loading...");
        }
    }


    int getSize() {
        return iSize;
    }
    public String getS() {return size;}
    public String getPath() {return Path;}
    public String getName() {return Name;}

    TestStructure[] getTestStructure() {
        return Questions;
    }

}

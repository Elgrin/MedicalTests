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


class XmlTestLoader {
    private String FileName;
    private int iSize;
    //private TestStructure Questions[];
    private TestStructure Questions;
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
            iSize = Integer.parseInt(el.getAttribute("size"));

        }
        catch (Exception e){
            Log.v(TAG, "Error while loading  - 1");}
    }

    XmlTestLoader(String fileName, AssetManager assetManager, int mass) {
        FileName = fileName;

        TestStructure allQuestions = null;

        try {

            int del = 250;
            double src = mass/((double)del);
            int code = (int)(src);
            double ost = src - code;

            if(ost==0) {
                code = code-1;
            }

            String s_code = Integer.toString(code);
            if (code == 0.0) {
                s_code ="";
            }

            Log.v(TAG, FileName + " " + Integer.toString(mass) + " " + s_code);
            Log.v(TAG, "Size " + code + " " + s_code + " " + mass);

            InputStream is = assetManager.open(FileName + s_code+ ".xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);

            NodeList quize = doc.getElementsByTagName("quize");
            Node node = quize.item(0);
            Element el = (Element) node;
            Path = FileName;
            Name = el.getAttribute("name");

            NodeList nList = doc.getElementsByTagName("question");

            Node nNode = nList.item(mass - 1 - code * del);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;

                    String ID = element.getAttribute("id");
                    int id = Integer.parseInt(ID);

                    String TYPE = element.getAttribute("type");
                    int type = Integer.parseInt(TYPE);


                    //Первый тип вопросов
                    if (type == 1) {
                        String SIZE = element.getAttribute("size");
                        int size = Integer.parseInt(SIZE);


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
                        Log.v(TAG, " " + "ID"  + " " + id);
                        question.setID(id);

                        //allQuestions = new TestCheckBox();
                        allQuestions = question;
                    }
                    else {
                        if (type == 2) {

                            Log.v(TAG, " " + "Type"  + " " + type);

                            String SIZE_X = element.getAttribute("size_x");
                            int size_x = Integer.parseInt(SIZE_X);
                            String SIZE_Y = element.getAttribute("size_y");
                            int size_y = Integer.parseInt(SIZE_Y);

                            String Text = "";

                            String Parents[] = new String[size_x];
                            String Children[] = new String[size_y];
                            boolean Relations[] = new boolean[size_x
                                    *size_y];

                            MultipleChoices question = new MultipleChoices();

                            NodeList nList_Second = nNode.getChildNodes();

                            Log.v(TAG, SIZE_X + " " + SIZE_Y);

                            int mass_size_x = 0;
                            int mass_size_y=0;
                            int pairs_counter = 0;

                            for (int q = 0; q < nList_Second.getLength(); q++) {
                                Node nNode_Second = nList_Second.item(q);
                                if (nNode_Second.getNodeType() == Node.ELEMENT_NODE) {
                                    boolean it = nNode_Second.getNodeName().equals("text");
                                    if (it) {
                                        Text = nNode_Second.getTextContent();
                                    } else {

                                        if (nNode_Second.getNodeName().equals("item")) {

                                            Log.v(TAG, " " + "Item"  + " ");
                                            //Parents[mass_size_x] = nNode_Second.getTextContent();

                                            Element element_Second = (Element) nNode_Second;

                                            NodeList nList_Third = nNode_Second.getChildNodes();

                                            for (int x = 0; x < nList_Third.getLength(); x++) {

                                                Node nNode_Third = nList_Third.item(x);
                                                //Log.v(TAG, " " + "ThirdNode"  + " " + x);

                                                if (nNode_Third.getNodeType() == Node.ELEMENT_NODE) {

                                                    boolean flager = nNode_Third.getNodeName().equals("text");
                                                    Log.v(TAG, " " + "ThirdNodeInside"  + " " + x + " " + Boolean.toString(flager));

                                                    if (flager) {
                                                        Parents[mass_size_x] = nNode_Third.getTextContent();
                                                        Log.v(TAG, " " + "Parent"  + " " + Parents[mass_size_x]);
                                                    } else {
                                                        String s_Pairs = nNode_Third.getTextContent();
                                                        Log.v(TAG, " " + s_Pairs);
                                                        if(s_Pairs.equals("1")) {
                                                            Relations[pairs_counter] = true;
                                                        }
                                                        else {
                                                            Relations[pairs_counter] = false;
                                                        }
                                                        Log.v(TAG, " " + pairs_counter  + " " + Relations[pairs_counter]);
                                                        pairs_counter++;
                                                    }


                                                }

                                            }

                                            Log.v(TAG, Parents[mass_size_x]);
                                            mass_size_x++;
                                        }
                                        if (nNode_Second.getNodeName().equals("child")) {

                                            Children[mass_size_y] = nNode_Second.getTextContent();
                                            mass_size_y++;

                                        }
                                    }

                                }
                            }

                            Log.v(TAG, "2");

                            question.setType(type);
                            question.setText(Text);
                            question.setParents(Parents);
                            question.setChildren(Children);
                            question.setRelations(Relations);
                            question.setID(id);

                            //allQuestions = new MultipleChoices();
                            allQuestions = question;
                        }
                    }

                }

            Questions = allQuestions;
        } catch (Exception e) {
            Log.v(TAG, "Error while loading2...");
        }
    }


    int getSize() {
        return iSize;
    }
    String getPath() {return Path;}
    String getName() {return Name;}

    TestStructure getTestStructure() {
        return Questions;
    }

}

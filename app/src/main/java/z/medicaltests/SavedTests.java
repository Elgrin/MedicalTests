package z.medicaltests;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


/**
 * A simple {@link Fragment} subclass.
 */
public class SavedTests extends Fragment implements View.OnClickListener, MyDialogFragment.YesNoListener{


    private SavedBundle savedBundle[];
    private String Strokes[];
    private boolean condition;
    private boolean isChecked[];

    private static final String TAG = "XmlSavedReader_Error";



    @Override
    public void onClick(View view) {

        Log.v(TAG, "Click");
        //toastMessage(getResources().getString(R.string.alert_dialogue_button), 3);

        MyDialogFragment dialog = new MyDialogFragment();
        dialog.setTitle(getResources().
                getString(R.string.alert_header));
        dialog.setMessage(getResources().
                getString(R.string.alert_dialogue_button));
        dialog.setMode(3);
        dialog.show(getFragmentManager(), "tag");
    }

    private void DeleteAll() {
        final String filePath = "Save_massive.xml";
        getActivity().deleteFile(filePath);

        View view = getView();

        if(view!=null) {
            ListView listView = view.findViewById(R.id.list_saved);

            savedBundle = null;
            Strokes = null;
            listView.setAdapter(null);
            listView.invalidate();

            TextView textView = view.findViewById(R.id.ww);
            textView.setVisibility(View.VISIBLE);
            textView.setText("Нет сохранений");

            for (int i = 0; i < isChecked.length; i++) {
                isChecked[i]=false;
                buttonVisibility();
            }
        }
    }

    @Override
    public void onYes(int mode) {
        if(mode == 0) {
            //barListener.BarDrawer(Name, Path, Questions, Show, Max, Mode);
            DeleteAll();
        }
        else {
            if(mode == 1) {
                //onClickSave();
                deleteChecked();
            }
            else {
                if(mode==3) {
                    int it = 0;
                    for (int  i = 0; i < isChecked.length; i++) {
                        if(isChecked[i]) {
                            it = i;
                            break;
                        }
                    }

                    if(!savedBundle[it].getError()) {

                        TestFragmentCheckBox fragment;
                        fragment = new TestFragmentCheckBox();

                        XmlTestLoader loader = new XmlTestLoader(savedBundle[it].getPath(), getActivity().getAssets(), savedBundle[it].getCurID());
                        TestStructure Question = loader.getTestStructure();

                        fragment.SetMessage(savedBundle[it].getName(),
                                savedBundle[it].getPath(),
                                Question,
                                savedBundle[it].getNumber(),
                                savedBundle[it].getShow(),
                                false,
                                savedBundle[it].getRightAnswers(),
                                savedBundle[it].getMaxSize(),
                                0,
                                savedBundle[it].getMistakes(),
                                savedBundle[it].getAbsoluteSize(), savedBundle[it].getMassive());

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment, "fragment");
                        //ft.disallowAddToBackStack();

                        try {
                            FragmentManager fragMan = getFragmentManager();
                            Fragment frag = fragMan.findFragmentByTag("fragment");

                            if (frag.getClass().toString().equals("class z.medicaltests.TestFragmentCheckBox")) {
                                for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); ++i) {
                                    getFragmentManager().popBackStack();
                                }
                            }
                        }
                        catch (Exception e) {Log.v("Error", "Error in popstack");}

                        ft.addToBackStack(null);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.commit();
                        deleteChecked();

                    }
                    else {

                        MyDialogYes dialog = new MyDialogYes();
                        dialog.setTitle(getResources().
                                getString(R.string.alert_header));
                        dialog.setMessage(getResources().
                                getString(R.string.alert_dialogue_crashed));
                        dialog.show(getFragmentManager(), "tag");
                        deleteChecked();
                    }
                }
            }

        }
    }


    private void deleteChecked()  {

        int iterator =0;
        for (boolean i:isChecked) {
            if(i) {
                iterator++;
            }
        }

        int k =1;
        for (int i = 0; i <isChecked.length; i++, k++) {

            Log.v("TEXT", " " + Integer.toString(i) + " " + Boolean.toString(isChecked[i]) + "\n");

        }

        if(iterator == isChecked.length) {
            DeleteAll();
        }
        else {
            final String filePath = "Save_massive.xml";
            int count = 0;

            try {
                InputStream is = getActivity().openFileInput(filePath);

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(is);

                NodeList nList = doc.getElementsByTagName("unit");

                for(int m =0; m < isChecked.length; m++) {

                    for(int j = 0; j< nList.getLength(); j++) {
                        Node nNode = nList.item(j);

                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            if(isChecked[m]) {
                                Log.v("Text", "Step_1_IN");
                                Element element = (Element) nNode;
                                if(element.getAttribute("id").equals(Integer.toString(savedBundle[m].getID()))) {
                                    element.getParentNode().removeChild(element);
                                    count++;
                                    break;
                                }
                            }
                            Log.v(TAG, "Step_2");
                        }
                    }
                }


                //Node nNodeMain = doc.getFirstChild();
                //((Element) nNodeMain).setAttribute("all", Integer.toString(isChecked.length-count));
                //boolean newChecked[] = new boolean[isChecked.length-count];

                isChecked = new boolean[isChecked.length-count];
                Log.v("TEXT", "____________________________\n");
                for (int i = 0; i <isChecked.length; i++, k++) {
                    Log.v("TEXT"," " + Integer.toString(i) + " " + Boolean.toString(isChecked[i]) + "\n");
                }


                Transformer transformer = TransformerFactory.newInstance()
                        .newTransformer();
                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                transformer.setOutputProperty(OutputKeys.INDENT, "no");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(getActivity().getFilesDir().getAbsolutePath()+"/" + filePath));
                transformer.transform(source, result);
                is.close();


                XmlSavedReader loader = new XmlSavedReader(getActivity());
                savedBundle = loader.getBundle();

                View view = getView();

                if(view!=null) {
                    ListView listView = view.findViewById(R.id.list_saved);

                    try {
                        Strokes = new String[savedBundle.length];
                        isChecked = new boolean[savedBundle.length];
                        for (int i = 0; i < savedBundle.length; i++) {

                            Strokes[i] = savedBundle[i].getName() +
                                    ": " + savedBundle[i].getNumber() + " вопрос из "
                                    + savedBundle[i].getMaxSize();
                            if(savedBundle[i].getShow()) {
                                Strokes[i] += "" + ". Правильные показываются";
                            }
                            else {
                                Strokes[i] += "" + ". Правильные не показываются";
                            }

                        /*
                        Strokes[i] +="" + "Верно отвечено " + savedBundle[i].getRightAnswers()
                                + " из " + (savedBundle[i].getNumber() -1) + ".";
                                */
                            condition = true;
                        }
                    }
                    catch (Exception e) {condition = false;}

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(),
                            //android.R.layout.simple_list_item_multiple_choice,
                            R.layout.list_source,
                            Strokes);

                    listView.setAdapter(adapter);
                    listView.invalidate();

                    buttonVisibility();
                }


            } catch (Exception e) {
                Log.v("TEXT", e.getMessage() + "Error while deleting");
            }
        }

    }

    public void setMessage(SavedBundle savedBundle[]) {
        this.savedBundle = savedBundle;

        try {
            Strokes = new String[savedBundle.length];
            isChecked = new boolean[savedBundle.length];
            for (int i = 0; i < savedBundle.length; i++) {

                Strokes[i] = savedBundle[i].getName() +
                ": " + savedBundle[i].getNumber() + " вопрос из "
                        + savedBundle[i].getMaxSize();
                if(savedBundle[i].getShow()) {
                    Strokes[i] += "" + ". Правильные показываются. ";
                }
                else {
                    Strokes[i] += "" + ". Правильные не показываются. ";
                }
                /*
                Strokes[i] +="" + "Верно отвечено " + savedBundle[i].getRightAnswers()
                        + " из " + (savedBundle[i].getNumber() -1) + ".";*/

                condition = true;
            }
        }
        catch (Exception e) {condition = false;}
    }

    public SavedTests() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();

        if(Strokes == null) {
            Log.v("Strokes", "haha");
            condition = false;
        }
        View view = getView();
        if(view!=null) {


            if(condition) {

                ListView listView = view.findViewById(R.id.list_saved);


                ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(),
                        //android.R.layout.simple_list_item_multiple_choice,
                        R.layout.list_source,
                        Strokes);

                listView.setAdapter(adapter);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


                for(int i =0; i < listView.getCount(); i++) {
                    listView.setItemChecked(i , isChecked[i]);
                }

                AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> listView,
                                            View itemView,
                                            int position,
                                            long id) {
                        isChecked[position] = !(isChecked[position]);
                        buttonVisibility();

                    }
                };
                listView.setOnItemClickListener(itemClickListener);
                TextView textView = view.findViewById(R.id.ww);
                textView.setVisibility(View.GONE);

            }
            else {
                TextView textView = view.findViewById(R.id.ww);
                textView.setVisibility(View.VISIBLE);
                textView.setText("Нет сохранений");
            }
        }
        buttonVisibility();

    }


    private void buttonVisibility() {
        int count = 0;
        View btn_view = getView();

        if(btn_view!=null) {
            Button button = btn_view.findViewById(R.id.load);
            try {
                for (boolean i:isChecked) {
                    if(i) {
                        count++;
                        if(count > 1) {
                            break;
                        }
                    }
                }
                if(count > 1 || count ==0) {
                    button.setVisibility(View.GONE);
                }
                else {
                    if(count == 1) {
                        button.setVisibility(View.VISIBLE);
                    }
                }
            }
            catch (Exception e) {
                button.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (savedInstanceState != null) {

            Strokes = savedInstanceState.getStringArray("strokes");
            condition = savedInstanceState.getBoolean("condition");
            isChecked = savedInstanceState.getBooleanArray("isChecked");

            /*
            SavedTests.SavedTestParcerable list;
            list = savedInstanceState.getParcelable("question");
            savedBundle = list.getSavedBundle();
            */
            //savedBundle = (SavedBundle[]) savedInstanceState.getParcelableArray("question");

            Parcelable[] allParcelables = savedInstanceState.getParcelableArray("question");
            savedBundle = new SavedBundle[allParcelables.length];

            for (int i = 0 ; i < allParcelables.length; i++) {
                savedBundle[i] = (SavedBundle)allParcelables[i];
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.getItem(2).setVisible(true);
        menu.getItem(3).setVisible(true);
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (savedBundle == null) {
            menu.getItem(2).setVisible(false);
            menu.getItem(3).setVisible(false);
        } else {
            menu.getItem(2).setVisible(true);
            int iterator = 0;
            for (boolean i: isChecked) {
                if (i) {
                    iterator++;
                }
                if (iterator == 0) {
                    menu.getItem(3).setVisible(false);
                }
                else {
                    menu.getItem(3).setVisible(true);
                }

            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all: {

                //toastMessage(getResources().getString(R.string.alert_dialogue_delete_all), 0);

                MyDialogFragment dialog = new MyDialogFragment();
                dialog.setTitle(getResources().
                        getString(R.string.alert_header));
                dialog.setMessage(getResources().
                        getString(R.string.alert_dialogue_delete_all));
                dialog.setMode(0);
                dialog.show(getFragmentManager(), "tag");
                break;
            }
            case R.id.action_delete_chosen: {
                //toastMessage(getResources().getString(R.string.alert_dialogue_delete_chosen), 1);

                MyDialogFragment dialog = new MyDialogFragment();
                dialog.setTitle(getResources().
                        getString(R.string.alert_header));
                dialog.setMessage(getResources().
                        getString(R.string.alert_dialogue_delete_chosen));
                dialog.setMode(1);
                dialog.show(getFragmentManager(), "tag");
                break;
            }
        }

        getActivity().invalidateOptionsMenu();

        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View layout = inflater.inflate(R.layout.fragment_saved_tests, container, false);

        Button commit = layout.findViewById(R.id.load);
        commit.setOnClickListener(this);

        return layout;
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putStringArray("strokes", Strokes);
        savedInstanceState.putBoolean("condition", condition);
        savedInstanceState.putBooleanArray("isChecked", isChecked);
        savedInstanceState.putParcelableArray("question", savedBundle);
    }
}

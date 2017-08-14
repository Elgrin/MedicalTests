package z.medicaltests;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static android.content.Context.MODE_APPEND;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragmentCheckBox extends Fragment implements View.OnClickListener{

    protected int Number;
    protected TestStructure Questions[];
    protected int RightAnswers;
    protected boolean Show;
    static protected boolean Colors;
    private Check_Boxes fragment;
    private MultipleChoicesFragment fragmentMultiple;
    private  static  Check_Boxes Frag;
    private  static  MultipleChoicesFragment Frag_Multiple;
    private int Max;
    private String Path;
    private String Name;
    private int Mode;
    private int MistakesIndexesArray[];
    private int AbsoluteSize;

    private static final String TAG = "TestFragmentCheckBox";
    private TestFragmentBarListener barListener;
    private onBackClickListener backClickListener;


    private void toastMessage(String message, final int mode) {

        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        AlertDialog.Builder ad;
        ad = new AlertDialog.Builder(getActivity());

        ad.setTitle(getResources().
                getString(R.string.alert_header));  // заголовок
        ad.setMessage(message); // сообщение
        ad.setCancelable(false);

        ad.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                if(mode == 0) {

                    try {
                        FragmentManager fragMan = getFragmentManager();
                        Fragment frag = fragMan.findFragmentByTag("fragment");

                        if (frag.getClass().toString().equals("class z.medicaltests.TestFragmentCheckBox")) {
                            for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); ++i) {
                                getFragmentManager().popBackStack();
                            }
                            Log.v("Error", "Okie dokie_outside" + getFragmentManager().getBackStackEntryCount());
                        }
                    }
                    catch (Exception e) {Log.v("Error", "Error in popstack");}

                    barListener.BarDrawer(Name, Path, Questions, Show, Max, Mode, MistakesIndexesArray,
                            AbsoluteSize);
                }
                else {
                    if(mode == 1) {
                        onClickSave();
                    }
                    else {
                        if(mode == 2) {

                            Intent intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("mailto:" + getResources().
                                    getString(R.string.email)));
                            intent.putExtra(Intent.EXTRA_SUBJECT,
                                    getResources().
                                            getString(R.string.header));
                            intent.putExtra(Intent.EXTRA_TEXT, "Найдена ошибка" + "\n\nНомер вопроса: " + Number + ". Тема: " + Name + ". Путь: " + Path + "\n\nВаши комментарии:\n");
                            startActivity(intent);

                            /*
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_EMAIL, new String[] {getResources().
                                    getString(R.string.email)});
                            intent.putExtra(Intent.EXTRA_SUBJECT,
                                    getResources().
                                            getString(R.string.header));

                            String chooserTitle = getString(R.string.choser);
                            Intent chosenIntent = Intent.createChooser(intent, chooserTitle);
                            startActivity(chosenIntent);*/
                        }
                        else {
                            if(mode == 3) {
                                try {
                                    FragmentManager fragMan = getFragmentManager();
                                    Fragment frag = fragMan.findFragmentByTag("fragment");

                                    if (frag.getClass().toString().equals("class z.medicaltests.TestFragmentCheckBox")) {
                                        for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); ++i) {
                                            getFragmentManager().popBackStack();
                                        }
                                        Log.v("Error", "Okie dokie_outside" + getFragmentManager().getBackStackEntryCount());
                                    }
                                }
                                catch (Exception e) {Log.v("Error", "Error in popstack");}
                                onClickSave();
                                backClickListener.onBackClick();
                            }
                        }
                    }
                }


            }
        });
        ad.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {

            }
        });

        ad.show();
    }


    interface TestFragmentBarListener {
        public void BarDrawerTrue(boolean MenuFlag);
        public void BarDrawer(String Name, String Path, TestStructure Questions[],boolean Show, int Max, int Mode,
                              int[] MistakesIndexesArray,
                              int AbsoluteSize);
    }

    interface onBackClickListener {
        public void onBackClick();
    }

    public static boolean getColor() {
        return Colors;
    }


    public class TestFragmentPacerable implements Parcelable {
        private Check_Boxes fragment;
        private MultipleChoicesFragment fragmentMultiple;
        private TestStructure Questions[];
        private int mData;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(mData);
        }

        public final Parcelable.Creator<TestFragmentPacerable> CREATOR
                = new Parcelable.Creator<TestFragmentPacerable>() {
            public TestFragmentPacerable createFromParcel(Parcel in) {
                return new TestFragmentPacerable(in);
            }

            public TestFragmentPacerable[] newArray(int size) {
                return new TestFragmentPacerable[size];
            }
        };

        private TestFragmentPacerable(Parcel in) {
            mData = in.readInt();
        }

        private TestFragmentPacerable() {
        }

        void setTestStruscture(TestStructure Questions[]) {
            this.Questions = Questions;
        }

        void setCheckBoxes(Check_Boxes fragment) {
            this.fragment = fragment;
        }

        void setMultipleChoicesFragment(MultipleChoicesFragment fragmentMultiple) {this.fragmentMultiple = fragmentMultiple;}

        TestStructure[] getTestStruscture() {
            return Questions;
        }

        Check_Boxes getCheckBoxes() {
            return fragment;
        }

        MultipleChoicesFragment getFragmentMultiple() {return fragmentMultiple;}
    }

    private TestFragmentCheckBox.TestFragmentCheckBoxListener listener;


    static interface TestFragmentCheckBoxListener {
        void onButtonCheckBoxCommitListener(String Name, String Path,
                                            boolean Show,
                                            TestStructure Questions[],
                                            int Number,
                                            int RighAnswers,
                                            int Max,
                                            int Mode,
                                            int[] MistakesIndexesArray,
                                            int AbsoluteSize);
    }


    public static void Save(Check_Boxes fragment) {
        Frag = fragment;
    }

    public static void SaveMultiple(MultipleChoicesFragment fragment) {Frag_Multiple = fragment;}

    public TestFragmentCheckBox() {
        // Required empty public constructor
    }

    public void SetMessage(String Name,
                           String Path,
                           TestStructure Questions[],
                           int Number,
                           boolean Show,
                           boolean Colors,
                           int RightAnswers,
                           int Max,
                           int Mode,
                           int[] MistakesIndexesArray,
                           int AbsoluteSize) {
        this.Path = Path;
        this.Questions = Questions;
        this.Number = Number;
        this.Show = Show;
        this.Colors = Colors;
        this.RightAnswers = RightAnswers;
        this.Max = Max;
        this.Name = Name;
        this.Mode = Mode;
        this.MistakesIndexesArray = MistakesIndexesArray;
        this.AbsoluteSize = AbsoluteSize;

        if(MistakesIndexesArray == null)
            this.MistakesIndexesArray = new int[0];
        else {
            this.MistakesIndexesArray = MistakesIndexesArray;
        }
    }


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (savedInstanceState != null) {

            Number = savedInstanceState.getInt("number");
            RightAnswers = savedInstanceState.getInt("rightAnswer");
            Show = savedInstanceState.getBoolean("show");
            Colors = savedInstanceState.getBoolean("color");
            Max = savedInstanceState.getInt("max");
            Path = savedInstanceState.getString("path");
            Name = savedInstanceState.getString("name");
            Mode = savedInstanceState.getInt("mode");
            MistakesIndexesArray = savedInstanceState.getIntArray("mistakesIndexesArray");
            AbsoluteSize = savedInstanceState.getInt("AbsoluteSize");

            TestFragmentPacerable question;
            question = savedInstanceState.getParcelable("questions");
            Questions = question.getTestStruscture();

            TestFragmentPacerable fragment_box;
            fragment_box = savedInstanceState.getParcelable("fragment");
            fragment = fragment_box.getCheckBoxes();

            TestFragmentPacerable fragment_m;
            fragment_m = savedInstanceState.getParcelable("fragmentMultiple");
            fragmentMultiple = fragment_m.getFragmentMultiple();

            /*
            if (Questions[Number - 1].getType() == 1) {
                //View view = getView();

                CheckBox[] Boxes = fragment.getCheckBoxes();
                int BackGroundColor[] = fragment.getBackGroundColor();
                boolean Checked[] = fragment.getChecked();

                fragment.SetMessage(Questions[Number - 1], Boxes, BackGroundColor, Checked);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_frame, fragment, "fragment");
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }*/

        } else {
            //Check_Boxes fragment;


            if (Questions[Number - 1].getType() == 1) {
                CheckBox Boxes[] = new CheckBox[Questions[Number - 1].getOptions().length];
                int BackGroundColor[] = new int[Questions[Number - 1].getOptions().length];
                boolean Checked[] = new boolean[Questions[Number - 1].getOptions().length];

                fragment = new Check_Boxes();
                fragment.SetMessage(Questions[Number - 1], Boxes, BackGroundColor, Checked);

                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_frame, fragment, "fragment");
                //ft.disallowAddToBackStack();
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
            else {
                if (Questions[Number - 1].getType() == 2) {
                /*
                CheckBox Boxes[] = new CheckBox[Questions[Number - 1].getOptions().length];
                int BackGroundColor[] = new int[Questions[Number - 1].getOptions().length];
                boolean Checked[] = new boolean[Questions[Number - 1].getOptions().length];
                */

                    fragmentMultiple = new MultipleChoicesFragment();
                    fragmentMultiple.SetMessage(Questions[Number - 1]);

                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_frame, fragmentMultiple, "fragment");
                    //ft.disallowAddToBackStack();
                    ft.addToBackStack(null);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }
            }

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.getItem(0).setVisible(true);
        menu.getItem(1).setVisible(true);
        menu.getItem(4).setVisible(true);
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (Colors)
            menu.getItem(1).setVisible(false);
        //если надо отключить отображение сохранения в режиме экзамена
        else
            menu.getItem(1).setVisible(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                //barListener.BarDrawer(Name, Path, Questions, Show, Max, Mode);
                toastMessage(getResources().
                        getString(R.string.alert_dialogue_again_header), 0);
                break;
            }
            case R.id.action_save: {
                    //onClickSave();
                toastMessage(getResources().
                        getString(R.string.alert_dialogue_save_header), 1);
                break;
            }

            case R.id.action_bug_report: {
                //onClickSave();
                toastMessage(getResources().
                        getString(R.string.alert_dialogue_bug_report), 2);
                break;
            }
        }

        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_test_fragment_check_box, container, false);

        Button Next = (Button) view.findViewById(R.id.button_next);
        Log.v("COUNT", this.getClass().toString());
        Next.setOnClickListener(this);

        Button Save = (Button) view.findViewById(R.id.button_save);
        Save.setOnClickListener(this);

        view.setFocusableInTouchMode(true);
        view.requestFocus();

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("TAG", "keyCode: " + keyCode);
                if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)  {
                    Log.i("TAG", "onKey Back listener is working!!!");
                    Log.v("COUNT", Integer.toString(getFragmentManager().getBackStackEntryCount()));

                    DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                    else {
                        toastMessage(getResources().
                                getString(R.string.alert_dialogue_save_out), 3);
                        Log.v("COUNT", Integer.toString(getFragmentManager().getBackStackEntryCount()));
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        View view = getView();

        Button Next = (Button) view.findViewById(R.id.button_next);
        Button Save = (Button) view.findViewById(R.id.button_save);
        //Next.setText(Integer.toString(MistakesIndexesArray.length));

        if (Show && !Colors)
            Next.setText(getResources().getString(R.string.button_next_colors));

        Save.setVisibility(View.GONE);


        TextView textView = (TextView) view.findViewById(R.id.exercise_text);
        textView.setText(Questions[Number - 1].getText());
        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView textView_2 = (TextView) view.findViewById(R.id.exercise_number);
        textView_2.setText("Вопрос " + Integer.toString(Number) + " из " + Integer.toString(Max));


        barListener.BarDrawerTrue(true);
    }


    public void mistakesArray(int digit) {

        if(digit!= 1) {
            //int i = MistakesIndexesArray.length;

            //MistakesIndexesArray[i-1] = Number-1;

            int t[] = new int[MistakesIndexesArray.length+1];
            for(int j = 0; j < MistakesIndexesArray.length; j++) {
                t[j] = MistakesIndexesArray[j];
            }

            MistakesIndexesArray = t;
            int i = MistakesIndexesArray.length;
            MistakesIndexesArray[i-1] = Number-1;
            Log.v(TAG, Integer.toString(Number-1));
        }

    }
    @Override
    public void onClick(View view) {

        if (!Colors) {
            switch (view.getId()) {

                case R.id.button_next: {
                    if(Show) {
                        View v = getView();
                        Button Next = (Button) v.findViewById(R.id.button_next);
                        Next.setText(getResources().getString(R.string.button_next));
                        onClickNext();
                        break;
                    }
                    else {

                        if (Questions[Number - 1].getType() == 1) {
                            int right = fragment.Count();
                            RightAnswers += fragment.Count();
                            mistakesArray(right);
                        }
                        if (Questions[Number - 1].getType() == 2) {
                            fragmentMultiple = Frag_Multiple;
                            int right = fragmentMultiple.Count();
                            RightAnswers += right;
                            mistakesArray(right);
                        }

                        if (listener != null) {
                            listener.onButtonCheckBoxCommitListener(Name, Path, Show, Questions,
                                    Number + 1, RightAnswers, Max, Mode, MistakesIndexesArray,
                                    AbsoluteSize);
                        }
                        break;
                    }
                }
                case R.id.button_save: {
                    //onClickSave();
                    //Log.v(TAG, "Save");
                    break;
                }
            }
            return;
        }
        else {
            if (Questions[Number - 1].getType() == 1) {
                int right = fragment.Count();
                RightAnswers += right;
                mistakesArray(right);
            }
            if (Questions[Number - 1].getType() == 2) {
                fragmentMultiple = Frag_Multiple;
                int right = fragmentMultiple.Count();
                RightAnswers += right;
                mistakesArray(right);
            }
        }

        switch (view.getId()) {

            case R.id.button_next: {

                try {
                    FragmentManager fragMan = getFragmentManager();
                    Fragment frag = fragMan.findFragmentByTag("fragment");

                    if (frag.getClass().toString().equals("class z.medicaltests.TestFragmentCheckBox")) {
                        for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); ++i) {
                            getFragmentManager().popBackStack();
                        }
                    }
                    Log.v("Error", "Okie dokie_outside" + getFragmentManager().getBackStackEntryCount());
                }
                catch (Exception e) {Log.v("Error", "Error in popstack");}

                if (listener != null) {
                    listener.onButtonCheckBoxCommitListener(Name, Path, Show, Questions,
                            Number + 1, RightAnswers, Max, Mode, MistakesIndexesArray,
                            AbsoluteSize);
                }
                break;
            }
        }

    }

    public void onClickNext() {

        if (Questions[Number - 1].getType() == 1) {
            Colors = true;

            View view = getView();
            fragment = Frag;

            Button Save = (Button) view.findViewById(R.id.button_save);
            Save.setVisibility(View.GONE);

            getActivity().invalidateOptionsMenu();

            fragment.Paint();
            Log.v(TAG, "Paint");
        }
        if (Questions[Number - 1].getType() == 2) {
            Colors = true;

            fragmentMultiple = Frag_Multiple;
            getActivity().invalidateOptionsMenu();
            fragmentMultiple.Paint();
            Log.v(TAG, "Paint");
        }
    }

    public void onClickSave() {

        Log.v(TAG, "Save");
        final String filePath = "Save_massive.xml";
        boolean exist = false;

         //FileInputStream fin;
        //getActivity().deleteFile(filePath);

        View view = getView();
        Button Save = (Button) view.findViewById(R.id.button_save);

        String massive[] = getActivity().fileList();
        for(int i = 0; i < massive.length; i++) {
            if(massive[i].equals(filePath)) {
                exist = true;
                break;
            }
        }

        if(!exist) {
            //.setText("Net");

            FileOutputStream fos;

            try {
                String newXmlFileName = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?><save all=\"0\"></save>";
                fos = getActivity().
                        openFileOutput(filePath, MODE_APPEND);
                fos.write(newXmlFileName.getBytes());
                fos.close();
            }
            catch (Exception e) {
                Log.v(TAG, "Error");
            }

            exist = true;
        }

        //getActivity().deleteFile(filePath);
        if(exist) {
            try {
                InputStream is = getActivity().openFileInput(filePath);


                //Save.setText(getActivity().getFilesDir().getAbsolutePath()+"/" + filePath);

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(is);

                Node nNode = doc.getFirstChild();
                String allString = ((Element) nNode).getAttribute("all");
                int allInt = Integer.parseInt(allString);
                //Save.setText(allString);
                ((Element) nNode).setAttribute("all", Integer.toString(allInt+1));

                Element NameElementTitle=doc.createElement("unit");
                //NameElementTitle.appendChild(doc.createTextNode("true")); //Внутренний текст
                NameElementTitle.setAttribute("id", Integer.toString(allInt));
                Log.v(TAG, "0");

                Element NameElementPath = doc.createElement("path");
                Log.v(TAG, Path);
                NameElementPath.appendChild(doc.createTextNode(Path));
                Log.v(TAG, "1");
                Element NameElementNumber = doc.createElement("number");
                NameElementNumber.appendChild(doc.createTextNode(Integer.toString(Number)));
                Log.v(TAG, "2");
                Element NameElementShow = doc.createElement("show");
                NameElementShow.appendChild(doc.createTextNode(Boolean.toString(Show)));
                Log.v(TAG, "3");
                Element NameElementRightAnswers = doc.createElement("answers");
                NameElementRightAnswers.appendChild(doc.createTextNode(Integer.toString(RightAnswers)));
                Log.v(TAG, "4");
                Element NameElementMaxSize = doc.createElement("max");
                NameElementMaxSize.appendChild(doc.createTextNode(Integer.toString(Max)));
                Element NameElementName = doc.createElement("name");
                Log.v(TAG, Name);
                NameElementName.appendChild(doc.createTextNode(Name));

                Log.v(TAG, "Massive");
                Element NameElementMassive = doc.createElement("massive");
                NameElementMassive.setAttribute("size", Integer.toString(Questions.length));

                Log.v(TAG, "Mistakes");
                Element NameElementMistakes = doc.createElement("mistakes");
                NameElementMistakes.setAttribute("size", Integer.toString(MistakesIndexesArray.length));

                Log.v(TAG, "Massive");
                for(int i=0; i < Questions.length; i++) {
                    Element element = doc.createElement("element");
                    Log.v(TAG, Integer.toString(Questions[i].getID()));
                    element.appendChild(doc.createTextNode(Integer.toString(Questions[i].getID())));
                    NameElementMassive.appendChild(element);
                }

                Log.v(TAG+"A", "Mistakes");
                Log.v(TAG+"A", Integer.toString(MistakesIndexesArray.length));
                for(int i=0; i < MistakesIndexesArray.length; i++) {

                    Log.v(TAG+"A", Integer.toString(MistakesIndexesArray[i]+1));
                    Element element = doc.createElement("element");
                    element.appendChild(doc.createTextNode(Integer.toString(MistakesIndexesArray[i])));
                    NameElementMistakes.appendChild(element);
                }

                Element NameElementAbsoluteSize = doc.createElement("absolute");
                Log.v(TAG, Name);
                NameElementAbsoluteSize.appendChild(doc.createTextNode(Integer.toString(AbsoluteSize)));


                NameElementTitle.appendChild(NameElementPath);
                NameElementTitle.appendChild(NameElementNumber);
                NameElementTitle.appendChild(NameElementShow);
                NameElementTitle.appendChild(NameElementRightAnswers);
                NameElementTitle.appendChild(NameElementMaxSize);
                NameElementTitle.appendChild(NameElementName);
                NameElementTitle.appendChild(NameElementMassive);
                NameElementTitle.appendChild(NameElementMistakes);
                NameElementTitle.appendChild(NameElementAbsoluteSize);

                nNode.appendChild(NameElementTitle);

                Transformer transformer = TransformerFactory.newInstance()
                        .newTransformer();
                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                transformer.setOutputProperty(OutputKeys.INDENT, "no");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(getActivity().getFilesDir().getAbsolutePath()+"/" + filePath));
                transformer.transform(source, result);
                is.close();

                /*
                FileOutputStream fos;
                fos = getActivity().openFileOutput(filePath, MODE_APPEND);
                String Massive = "<unit id = \"" + allInt + "\">" +
                        "<path>" + Path + "</path>" +
                        "<number>" + Integer.toString(Number)+"</number>" +
                        "<show>" + Boolean.toString(Show)+"</show>" +
                        "<answers>" + Double.toString(RightAnswers)+"</answers>" +
                        "<max>" + Integer.toString(Max)+"</max>" +
                        "</unit>";
                fos.write(Massive.getBytes());*/

            }
            catch (Exception e) {
                Log.v(TAG, "Error writing");
            }
        }
        else {
        }



        /*
         try {

             /**
              * Проверить, существует ли файл, затем, если нет -
              * fos = getActivity().
              openFileOutput(filePath, MODE_APPEND); - чтобы он создался.
              Записать туду шапку и первичный тег
              Затем уже работать с XmlReader
              */
             //InputStream is = getActivity().openFileInput(FileName + ".xml");

             //InputStream is = getActivity().openFileInput(filePath);
        /*
            fin = getActivity().openFileInput(filePath);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            Log.v(TAG, text);


        }
    /*
        catch(IOException ex) {

             Log.v(TAG, "Error");
        }*/


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        //setRetainInstance(true);

        savedInstanceState.putInt("number", Number);
        savedInstanceState.putInt("rightAnswer", RightAnswers);
        savedInstanceState.putBoolean("show", Show);
        savedInstanceState.putBoolean("color", Colors);
        savedInstanceState.putInt("max", Max);
        savedInstanceState.putString("path", Path);
        savedInstanceState.putString("name", Name);
        savedInstanceState.putInt("mode", Mode);
        savedInstanceState.putIntArray("mistakesIndexesArray", MistakesIndexesArray);
        savedInstanceState.putInt("AbsoluteSize", AbsoluteSize);


        TestFragmentPacerable question = new TestFragmentPacerable();
        question.setTestStruscture(Questions);
        savedInstanceState.putParcelable("questions", question);


        TestFragmentPacerable fragment_box = new TestFragmentPacerable();
        fragment_box.setCheckBoxes(fragment);
        savedInstanceState.putParcelable("fragment", fragment_box);

        TestFragmentPacerable fragment_m = new TestFragmentPacerable();
        fragment_m.setMultipleChoicesFragment(fragmentMultiple);
        savedInstanceState.putParcelable("fragmentMultiple", fragment_m);
    }



    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.listener = (TestFragmentCheckBox.TestFragmentCheckBoxListener) context;
        this.barListener = (TestFragmentBarListener) context;
        this.backClickListener = (onBackClickListener) context;
        //Log.v(TAG, "Activity");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (TestFragmentCheckBox.TestFragmentCheckBoxListener) context;
        this.barListener = (TestFragmentBarListener) context;
        //Log.v(TAG, "Context");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        barListener.BarDrawerTrue(false);
        barListener = null;
        backClickListener = null;
        Log.v("WAWA", "Detach");
    }


    @Override
    public void onStop() {
        // clear back stack

        //getFragmentManager().popBackStack();

        super.onStop();
        Log.v("WAWA", "Stop");
    }

    @Override
    public void onDestroy() {
        // clear back stack
        super.onDestroy();
        Log.v("WAWA", "Destroy");
        //getFragmentManager().popBackStack();
        /*
        int backStackCount = getFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            // Get the back stack fragment id.
            int backStackId = getFragmentManager().getBackStackEntryAt(i).getId();
            // clear the fragment from back stack
            getFragmentManager().popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }*/

    }
}

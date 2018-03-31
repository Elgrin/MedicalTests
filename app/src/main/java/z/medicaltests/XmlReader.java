package z.medicaltests;


import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */


public class XmlReader extends Fragment{


    interface XmlReaderListener {
        void itemClicked(long id, String[] Files, String[] Test);
    }

    private XmlReaderListener listener;
    private String message = "";
    private String[] Names;
    private String[] Files;
    private String[] Test;
    private String Text ="";


    private static final String TAG = "XmlReader";

    public XmlReader() {
        // Required empty public constructor
    }

    /**
     * @param msg          Имя файла для чтения
     * @param assetManager Контекст
     */
    public  void SetMessage(String msg, AssetManager assetManager) {
        message = msg;
        Log.v(TAG, "This");
        XmlListLoader loader = new XmlListLoader(message, assetManager);
        Files = loader.getFiles();
        Names = loader.getNames();
        Test = loader.getTest();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(savedInstanceState != null) {
            Files = savedInstanceState.getStringArray("files");
            Test = savedInstanceState.getStringArray("test");
            Names = savedInstanceState.getStringArray("names");
            message = savedInstanceState.getString("message");
            Text = savedInstanceState.getString("text");
        }

        View view = inflater.inflate(R.layout.fragment_xml_reader, container, false);

        if(view != null) {
            TextView textView = view.findViewById(R.id.text_frag);
            textView.setText(Text);
            //Адаптер
            ListView listView = view.findViewById(R.id.list_frag);


            try {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(),
                        android.R.layout.simple_list_item_1, Names);
                Log.v("TITLES", Integer.toString(Names.length));


                AdapterView.OnItemClickListener itemClickListener =
                        new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> listView,
                                                    View v,
                                                    int position,
                                                    long id) {
                                if (listener != null) {
                                    listener.itemClicked(id, Files, Test);
                                }
                            }
                        };
                Log.v(TAG, "This_2");
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(itemClickListener);
            }
            catch (Exception e) {Log.v("Error", "Filed to load list");}
        }
        return view;
        //inflater.inflate(R.layout.fragment_xml_reader, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (XmlReaderListener) context;
        Log.v(TAG, "NoThisDepreceted");

    }
    @Override
    public  void onDetach() {
        super.onDetach();
        listener = null;
    }


    //public String[] GetFileName() {return Files;}

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArray("names", Names);
        savedInstanceState.putStringArray("test", Test);
        savedInstanceState.putStringArray("files", Files);
        savedInstanceState.putString("message", message);
        savedInstanceState.putString("text", Text);
    }

}

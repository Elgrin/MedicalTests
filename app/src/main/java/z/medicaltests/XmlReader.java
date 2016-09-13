package z.medicaltests;


import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class XmlReader extends Fragment{

    static interface XmlReaderListener {
        void itemClicked(long id, String[] F);
    }

    private XmlReaderListener listener;
    protected  String message = "";
    protected String Names[];
    public  String Files[];
    protected String Text ="";


    private static final String TAG = "myLogs";

    public XmlReader() {
        // Required empty public constructor
    }

    public  void SetMessage(String msg, String Text, AssetManager assetManager) {
        message = msg;
        this.Text = Text;
        Log.v(TAG, "This");
        XmlListLoader loader = new XmlListLoader(message, assetManager);
        Files = loader.getFiles();
        Names = loader.getNames();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(savedInstanceState != null) {
            Files = savedInstanceState.getStringArray("files");
            Names = savedInstanceState.getStringArray("names");
            message = savedInstanceState.getString("message");
            Text = savedInstanceState.getString("text");
        }
        return inflater.inflate(R.layout.fragment_xml_reader, container, false);
    }

    @Override
    public  void onStart(){
        super.onStart();



        View view = getView();
        if(view != null) {
            TextView textView = (TextView) view.findViewById(R.id.text_frag);

            textView.setText(Text);

            //Адаптер
            ListView listView = (ListView)view.findViewById(R.id.list_frag);


            ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(),
                    android.R.layout.simple_list_item_1, Names);


            AdapterView.OnItemClickListener itemClickListener =
                    new AdapterView.OnItemClickListener(){
                        public void onItemClick(AdapterView<?> listView,
                                                View v,
                                                int position,
                                                long id) {
                            if (listener != null) {
                                listener.itemClicked(id, Files);
                            }
                        }
                    };
            Log.v(TAG, "This_2");
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(itemClickListener);
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.listener = (XmlReaderListener) context;

        Log.v(TAG, "This");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (XmlReaderListener) context;

    }


    public String[] GetFileName() {return Files;}

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putStringArray("names", Names);
        savedInstanceState.putStringArray("files", Files);
        savedInstanceState.putString("message", message);
        savedInstanceState.putString("text", Text);
    }

}

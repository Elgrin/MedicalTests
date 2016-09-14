package z.medicaltests;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;

import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.widget.TextView;

import java.io.File;


public class MainWindow extends Activity implements XmlReader.XmlReaderListener,
        ButtonMenu.ButtonMenuListener {

    //Переменные для заполнения выдвижной панели

    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;

    //
    private String[] titles;
    private int currentPosition = 0;

    @Override
    public void onButtonClick(int Size, String size) {
        TextView text = (TextView) findViewById(R.id.it);

        if(Size==283)
        text.setText(size);
    }
    @Override
    public void itemClicked(long id, String[] Files, String[] Test) {

        if(Files[0] != null) {
            if (!(Files[(int) id].equals(""))) {
                XmlReader fragment;
                fragment = new XmlReader();
                fragment.SetMessage(Files[(int) id],
                        getResources().getString(R.string.themes_text), getAssets());

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        }

        if(Test[0] !=null){
            if(!(Test[(int)id].equals(""))){
                ButtonMenu fragment;
                fragment = new ButtonMenu();
                fragment.setMessage((Test[(int) id]),
                        getResources().getString(R.string.mode_text), getAssets());

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        }


        TextView text = (TextView) findViewById(R.id.it);
        try {
            text.setText(Test[0]);}
        catch (Exception e){};

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //Заполнение панели строковыми знаечниями
        titles = getResources().getStringArray(R.array.menu);

        drawerList = (ListView) findViewById(R.id.drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1,
                titles));

        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        //При первом запуске выбор первого фрагмента
        if (savedInstanceState == null) {
            selectedItem(0);
        }
        else {
            currentPosition = savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition);
        }

        //Создание ActionBarDrawerToggle
        drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer) {
            //Вызывается при переходе выдвижной панели в полностью закрытое состояние.
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            //Вызывается при переходе выдвижной панели в полностью открытое состояние.
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        //Прослушка нажатий кнопок
        drawerList.setOnItemClickListener(new DrawerItemClickListener());


        //Обеспечение корректного отображения
        // заголовка меню при нажатии кнопки возврата
        getFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        FragmentManager fragMan = getFragmentManager();
                        Fragment fragment = fragMan.findFragmentByTag("fragments");
                        if (fragment instanceof XmlReader) {
                            currentPosition = 0;
                        }

                        setActionBarTitle(currentPosition);
                        drawerList.setItemChecked(currentPosition, true);
                    }
                }
        );
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Если выдвижная панель открыта, скрыть элементы, связанные с контентом
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        // menu.findItem(R.id.action_share).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Код для обработки нажатия
            selectedItem(position);
        }
    }

    private void selectedItem(int position) {
        XmlReader fragment;
        currentPosition = position;

        switch (position) {

            case 1: {
                fragment = new XmlReader();
                //fragment.SetMessage(getResources().getString(R.string.subjects));
                break;
            }
            case 2: {
                //О приложении
                fragment = new XmlReader();
                break;
            }
            default: {
                fragment = new XmlReader();

                fragment.SetMessage(getResources().getString(R.string.subjects),
                        getResources().getString(R.string.subjects_text), getAssets());

                ///Временно вынесено сюда
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment, "fragments");
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

            }


        }
        setActionBarTitle(position);
        drawerList.setItemChecked(currentPosition, true);
        drawerLayout.closeDrawer(drawerList);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentPosition);
    }


    private void setActionBarTitle(int position) {
        String title;
        title = titles[position];
        getActionBar().setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

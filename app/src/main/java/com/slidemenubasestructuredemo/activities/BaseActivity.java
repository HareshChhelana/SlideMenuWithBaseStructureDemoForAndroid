package com.slidemenubasestructuredemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.slidemenubasestructuredemo.R;
import com.slidemenubasestructuredemo.Utilities.AppConstant;
import com.slidemenubasestructuredemo.adapters.NavigationDrawerListAdapter;
import com.slidemenubasestructuredemo.crashhandler.CrashReportHandler;


public abstract class BaseActivity extends AppCompatActivity{

    private View view;
    private LinearLayout lnrContentMain;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private FloatingActionButton floatingActionButton;
    private TextView textViewTitle;
    private ImageView imageViewLeftMenuIcon;
    private ImageView imageViewLeftBackIcon;
    private ImageView imageViewRightIcon;
    private ImageView imageViewUser;
    private TextView textViewUserName;
    private TextView textViewUserEmail;
    private RecyclerView recyclerViewNavigationDrawer;
    private LinearLayoutManager mLayoutManagerNavigationDrawerList;
    private NavigationDrawerListAdapter mNavigationDrawerListAdapter;

    public final int TOOLBAR_MENU_ICON_ID = R.id.image_view_toolbar_left_menu_icon;
    public final int TOOLBAR_BACK_ICON_ID = R.id.image_view_toolbar_left_back_icon;
    public final int TOOLBAR_RIGHT_ICON_ID = R.id.image_view_toolbar_right_menu_icon;
    public final int FLOATING_ACTION_BUTTON_ID = R.id.floating_action_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(AppConstant.CRASH_LOG_ENABLE){
            setCrashLogHandler();
        }
        setContentView(R.layout.activity_base);
        lnrContentMain = (LinearLayout)findViewById(R.id.content_main);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textViewTitle = (TextView) findViewById(R.id.text_view_toolbar_title);
        imageViewLeftMenuIcon = (ImageView) findViewById(TOOLBAR_MENU_ICON_ID);
        imageViewLeftBackIcon = (ImageView) findViewById(TOOLBAR_BACK_ICON_ID);
        imageViewRightIcon = (ImageView) findViewById(TOOLBAR_RIGHT_ICON_ID);
        floatingActionButton = (FloatingActionButton) findViewById(FLOATING_ACTION_BUTTON_ID);
        imageViewUser= (ImageView) findViewById(R.id.image_view_user);
        textViewUserName= (TextView) findViewById(R.id.text_view_user_name);
        textViewUserEmail= (TextView) findViewById(R.id.text_view_user_email);
        recyclerViewNavigationDrawer= (RecyclerView) findViewById(R.id.recycle_view_nav_drawer);

        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeAsUpIndicator(null);

        toolbar.setPadding(0,0,0,0);//for tab otherwise give space in tab
        toolbar.setContentInsetsAbsolute(0,0);

        mLayoutManagerNavigationDrawerList = new LinearLayoutManager(this);
        recyclerViewNavigationDrawer.setLayoutManager(mLayoutManagerNavigationDrawerList);
        recyclerViewNavigationDrawer.setHasFixedSize(true);
        recyclerViewNavigationDrawer.setItemAnimator(new DefaultItemAnimator());

        setupNavigationDrawer();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this,FloatingActivity.class));
                onClickToolBarOption(v.getId());
            }
        });
        imageViewLeftMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
                onClickToolBarOption(v.getId());
            }
        });
        imageViewLeftBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToolBarOption(v.getId());
            }
        });
        imageViewRightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToolBarOption(v.getId());
            }
        });


        view = LayoutInflater.from(this).inflate(setLayout(), lnrContentMain);

        findViewById(view);
        initialization();
        setupData();
        setListeners();

    }


    /**
     * set crash log
     */
    public void setCrashLogHandler() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                CrashReportHandler.attach(getApplicationContext());
            }
        }).start();
    }



    /**
     * This method use to set slide menu header and list data
     */
    private void setupNavigationDrawer(){
        imageViewUser.setImageResource(R.drawable.ic_launcher);
        textViewUserName.setText("Android Studio");
        textViewUserEmail.setText("android.studio@android.com");
        mNavigationDrawerListAdapter = new NavigationDrawerListAdapter(this, getResources().getStringArray(R.array.navigation_drawer_list));
        recyclerViewNavigationDrawer.setAdapter(mNavigationDrawerListAdapter);
    }

    /**
     * This method use to show/hide slide menu
     */
    private void toggleDrawer(){
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(Gravity.LEFT); //CLOSE Nav Drawer!
        }else{
            drawer.openDrawer(Gravity.LEFT); //OPEN Nav Drawer!
        }
    }

    /**
     * This method used to set toolbar tile for each screen
     * @param title screen title
     */
    public void setToolbarTitle(String title){
        textViewTitle.setText(title);
    }


    /**
     * This method used to disable/not shown slide menu on particular screen.
     */
    public void disableDrawer(){
        setDrawerEnabled(false);
    }

    /**
     * This method used to hide slide menu icon (tool bar left icon)
     */
    public void hideSlideMenu(){
        imageViewLeftMenuIcon.setVisibility(View.GONE);
        floatingActionButton.setVisibility(View.GONE);
    }

    /**
     * This method used to shown Back icon at (tool bar left icon)
     * @param icon optional back icon or directly define here if back icon same for all screen
     */
    public void showBackMenu(int icon){
        imageViewLeftBackIcon.setVisibility(View.VISIBLE);
        if(icon!=0) {
            imageViewLeftBackIcon.setImageResource(icon);
        }else {
            imageViewLeftBackIcon.setImageResource(R.drawable.ic_launcher);
        }
    }

    /**
     * This method used to show tool bar right icon
     */
    public void showToolbarRightMenuIcon(){
        imageViewRightIcon.setVisibility(View.VISIBLE);
    }

    /**
     * This method used disable slide menu
     * @param enabled
     */
    private void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED:DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawer.setDrawerLockMode(lockMode);
        toggle.setDrawerIndicatorEnabled(enabled);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * This method used to handle slide menu item click
     * @param index list clicked item position
     */
    public void onNavigationDrawerItemClick(int index){
        switch (index){
            case 0:
                startActivity(new Intent(this,FirstActivity.class));
                break;
            case 1:
                startActivity(new Intent(this,SecondActivity.class));
                break;
            case 2:
                startActivity(new Intent(this,ThirdActivity.class));
                break;
            case 3:
                startActivity(new Intent(this,FourthActivity.class));
                break;
            case 4:
                startActivity(new Intent(this,FifthActivity.class));
                break;
        }
        finish();
        drawer.closeDrawer(GravityCompat.START);
    }

    /**
     * This method used to set activity layout
     * @return activity custom layout
     */
    public abstract int setLayout();

    /**
     * This method used to find activity custom layout all views
     * @param view activity custom layout reference for find custom layout views
     */
    public abstract void findViewById(View view);

    /**
     * This method used to initialize all activity variables,classes,interfaces etc.
     */
    public abstract void initialization();

    /**
     * This method used to setup or bind data with activity views
     */
    public abstract void setupData();

    /**
     * This method used to set all listener
     */
    public abstract void setListeners();

    /**
     * This method used to get BaseActivity views click listener callback
     * @param viewId clicked view id
     */
    public abstract void onClickToolBarOption(int viewId);
}

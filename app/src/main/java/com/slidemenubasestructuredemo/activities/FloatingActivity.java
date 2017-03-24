package com.slidemenubasestructuredemo.activities;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.slidemenubasestructuredemo.R;


public class FloatingActivity extends BaseActivity implements View.OnClickListener  {

    private TextView textView;
    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void findViewById(View view) {
        textView = (TextView) view.findViewById(R.id.text_view);
    }

    @Override
    public void initialization() {

    }

    @Override
    public void setupData() {
        setToolbarTitle("Floating Action");
        textView.setText("Click Me!!!");
        disableDrawer();
        hideSlideMenu();
        showBackMenu(R.drawable.ic_launcher);
    }

    @Override
    public void setListeners() {
        textView.setOnClickListener(this);
    }

    @Override
    public void onClickToolBarOption(int viewId) {
        switch (viewId){
            case TOOLBAR_BACK_ICON_ID:
                Toast.makeText(this,"Back icon click",Toast.LENGTH_SHORT).show();
                finish();
                break;
            case TOOLBAR_MENU_ICON_ID:
                Toast.makeText(this,"Menu icon click",Toast.LENGTH_SHORT).show();
                break;
            case TOOLBAR_RIGHT_ICON_ID:
                Toast.makeText(this,"Right icon click",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_view:
                Toast.makeText(this,"Ok",Toast.LENGTH_SHORT).show();
                break;
        }
    }

}

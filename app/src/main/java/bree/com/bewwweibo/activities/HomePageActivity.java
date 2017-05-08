package bree.com.bewwweibo.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

import bree.com.bewwweibo.R;
import bree.com.bewwweibo.fragment.HomeFragment;
import bree.com.bewwweibo.fragment.MessageFragment;
import bree.com.bewwweibo.fragment.ProfileFragment;


public class HomePageActivity extends BaseActivity {
    private FrameLayout flContainer;
    private FragmentTabHost tabHost;
    private RadioButton rbHome;
    private RadioButton rbMessage;
    private RadioButton rbProfile;
    private RadioGroup rgTab;

    private int menuId=R.menu.mune_home;

    private Class fragment[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToorBarX().setDispalyHoneAsUpEnabled(false).setTitle(R.string.app_name);
        fragment=new Class[]{HomeFragment.class, MessageFragment.class, ProfileFragment.class};
        initialize();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page;
    }

    private void initialize() {

        flContainer = (FrameLayout) findViewById(R.id.flContainer);
        tabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        rbHome = (RadioButton) findViewById(R.id.rbHome);
        rbMessage = (RadioButton) findViewById(R.id.rbMessage);
        rbProfile = (RadioButton) findViewById(R.id.rbProfile);
        rgTab = (RadioGroup) findViewById(R.id.rgTab);
        tabHost.setup(getApplicationContext(), getSupportFragmentManager(), R.id.flContainer);
        tabHost.setup(getApplicationContext(),getSupportFragmentManager(),R.id.flContainer);
        for (int i=0;i<fragment.length;i++){
            TabHost.TabSpec tabSpec=tabHost.newTabSpec(String.valueOf(i)).setIndicator(String.valueOf(i));
            tabHost.addTab(tabSpec,fragment[i],null);
        }
        tabHost.setCurrentTab(0);

        rgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbHome:
                        tabHost.setCurrentTab(0);
                        menuId=R.menu.mune_home;
                        break;
                    case R.id.rbMessage:
                        tabHost.setCurrentTab(1);
                        menuId=-1;
                        break;
                    case R.id.rbProfile:
                        tabHost.setCurrentTab(2);
                        menuId=-1;
                        break;
                }
                //重新架子啊menu
                supportInvalidateOptionsMenu();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuId==-1){
            menu.clear();
        }else {
            getMenuInflater().inflate(R.menu.mune_home,menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}

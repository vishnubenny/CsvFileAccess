package lv.com.csvfileaccess.view.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import lv.com.csvfileaccess.R;
import lv.com.csvfileaccess.view.ui.fragment.ListCSVfromStorageFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListCSVfromStorageFragment listCSVfromStorageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findResources();
        initializeResources();
        setupFragmentView();
    }

    private void setupFragmentView() {
        if (listCSVfromStorageFragment == null) {
            listCSVfromStorageFragment = new ListCSVfromStorageFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, listCSVfromStorageFragment).commit();
    }

    private void initializeResources() {
        setSupportActionBar(toolbar);
    }

    private void findResources() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

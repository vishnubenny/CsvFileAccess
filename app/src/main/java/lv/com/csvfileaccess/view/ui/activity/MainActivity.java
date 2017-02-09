package lv.com.csvfileaccess.view.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import lv.com.csvfileaccess.R;
import lv.com.csvfileaccess.model.utils.AppConstants;
import lv.com.csvfileaccess.view.ui.fragment.ExpandedCSVFragment;
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
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            return;
        }
        this.finish();
    }

    public void launchExpandedCSVFragment(String absolutePath, String fileName) {
        ExpandedCSVFragment expandedCSVFragment = new ExpandedCSVFragment();
        Bundle extraBundle = new Bundle();
        extraBundle.putString(AppConstants.SELECTED_FILE_ABSOLUTE_PATH, absolutePath);
        expandedCSVFragment.setArguments(extraBundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, expandedCSVFragment).
                addToBackStack(AppConstants.SELECTED_FILE_OPEN).commit();
        toolbar.setTitle(fileName);
    }
}

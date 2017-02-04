package lv.com.csvfileaccess.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import lv.com.csvfileaccess.R;
import lv.com.csvfileaccess.model.csv.CSVFile;
import lv.com.csvfileaccess.model.pojo.RowData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        readCSVSampleFromRaw();
    }

    private void readCSVSampleFromRaw() {
        InputStream inputStream = getResources().openRawResource(R.raw.sample);
        CSVFile csvFile = new CSVFile(inputStream);
//        List scoreList = csvFile.read();

        List<RowData> rowDatas = csvFile.getDataList();
        Log.e("test Row data", String.valueOf(rowDatas.size()));
        Log.e("test", rowDatas.get(0).getProd());

//        String[] rowData = (String[]) scoreList.get(1);
//        Log.e("test", rowData[2]);
//        Log.e("testCount", String.valueOf(scoreList.size()));
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

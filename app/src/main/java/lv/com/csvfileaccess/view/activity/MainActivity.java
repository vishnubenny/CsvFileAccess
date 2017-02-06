package lv.com.csvfileaccess.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.InputStream;
import java.util.List;

import lv.com.csvfileaccess.R;
import lv.com.csvfileaccess.model.csv.CSVFile;
import lv.com.csvfileaccess.model.pojo.RowData;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findResources();
        initializeResources();
    }

    private void initializeResources() {
        setSupportActionBar(toolbar);
    }

    private void findResources() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        readCSVSampleFromRaw();
    }

    private void readCSVSampleFromRaw() {
        InputStream inputStream = getResources().openRawResource(R.raw.sample);
        CSVFile csvFile = new CSVFile(inputStream);

        List<RowData> rowDatas = csvFile.getDataList();
        Log.e("test Row data", String.valueOf(rowDatas.size()));
        int i = 0;
        for (RowData rowData : rowDatas) {
            i++;
            if (rowData.getProd().isEmpty()) {
                Log.e("test " + i, "empty field");
                continue;
            }
            Log.e("test " + i, rowData.getProd());
        }
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

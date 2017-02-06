package lv.com.csvfileaccess.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import lv.com.csvfileaccess.R;
import lv.com.csvfileaccess.model.csv.CSVFile;
import lv.com.csvfileaccess.model.pojo.RowData;

public class ReadFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_read, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        readCSVSampleFromRaw();

        test();
    }

    private void test() {
        File f=new File("/sdcard");

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
}

package lv.com.csvfileaccess.view.ui.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import lv.com.csvfileaccess.R;
import lv.com.csvfileaccess.model.adapter.CSVRowDataListAdapter;
import lv.com.csvfileaccess.model.csv.CSVFile;
import lv.com.csvfileaccess.model.utils.AppConstants;

public class ExpandedCSVFragment extends Fragment {
    private static final String TAG = ExpandedCSVFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private String selectedFileAbsolutePath;
    private LinearLayoutManager mLayoutManager;
    private Bundle extraBundle;
    private CSVRowDataListAdapter csvRowDataListAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expanded_csv_selected, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        setRetainInstance(true);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        handleResume();
    }

    private void handleResume() {
        if (csvRowDataListAdapter != null) {
            mRecyclerView.setAdapter(csvRowDataListAdapter);
            return;
        }
        if (getArguments() != null)
            extraBundle = getArguments();
        if (extraBundle.containsKey(AppConstants.SELECTED_FILE_ABSOLUTE_PATH))
            selectedFileAbsolutePath = extraBundle.getString(AppConstants.SELECTED_FILE_ABSOLUTE_PATH);
        if (selectedFileAbsolutePath != null && !selectedFileAbsolutePath.isEmpty()) {
            new FetchCSVfileDataList(selectedFileAbsolutePath).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    private class FetchCSVfileDataList extends AsyncTask<Object, Object, List<String>> {
        private final String selectedFileAbsolutePath;
        private ProgressDialog progressDialog;

        FetchCSVfileDataList(String selectedFileAbsolutePath) {
            this.selectedFileAbsolutePath = selectedFileAbsolutePath;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Fetching Data");
            progressDialog.setMessage("Please wait while loading your data");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected List<String> doInBackground(Object... voids) {
            return getCsvRowDataList(selectedFileAbsolutePath);
        }

        @Override
        protected void onPostExecute(List<String> csvRowDataList) {
            progressDialog.dismiss();
            if (!csvRowDataList.isEmpty()) {
                csvRowDataListAdapter = new CSVRowDataListAdapter(csvRowDataList);
                mRecyclerView.setAdapter(csvRowDataListAdapter);
            }
        }

        private List<String> getCsvRowDataList(String selectedFileAbsolutePath) {
            List<String> rowDataList = new ArrayList<>();
            try {
                CSVFile csvFile = new CSVFile(new FileInputStream(selectedFileAbsolutePath));
                rowDataList = csvFile.getDataList();
            } catch (FileNotFoundException e) {
                Log.e(TAG, e.toString(), e);
            }
            return rowDataList;
        }
    }
}

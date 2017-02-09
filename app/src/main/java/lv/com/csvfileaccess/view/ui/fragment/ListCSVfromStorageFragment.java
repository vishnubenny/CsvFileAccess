package lv.com.csvfileaccess.view.ui.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import lv.com.csvfileaccess.R;
import lv.com.csvfileaccess.model.adapter.CSVlistAdapter;
import lv.com.csvfileaccess.view.ui.activity.MainActivity;

public class ListCSVfromStorageFragment extends Fragment {
    private static final String TAG = ListCSVfromStorageFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CSVlistAdapter csVlistAdapter;

    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_csv_from_storage, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        setRetainInstance(true);
        setHasOptionsMenu(true);
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
        handleOnResume();
    }

    private void handleOnResume() {
        handleOnResumeActionBar();
        if (csVlistAdapter == null)
            new LookForCSVAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            mRecyclerView.setAdapter(csVlistAdapter);
    }

    private void handleOnResumeActionBar() {
        toolbar.setTitle(getContext().getString(R.string.app_name));
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list_csv_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh_now) {
            new LookForCSVAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            return true;
        }
        return false;
    }

    private class LookForCSVAsync extends AsyncTask<Object, Object, List<File>> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Fetching CSV");
            progressDialog.setMessage("Please wait while fetching files");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected List<File> doInBackground(Object... voids) {
            List<File> csvFiles = new ArrayList<>();
            csvFiles.addAll(getCSVFilesFromExternalStorage());
            csvFiles.addAll(getCSVFilesFromInternalStorage());
            return csvFiles;
        }

        private List<File> getCSVFilesFromInternalStorage() {
            List<File> list = new ArrayList<>();
            try {
                list = getCSVListFiles(new File(String.valueOf(Environment.getExternalStorageDirectory())));
            } catch (Exception e) {
                Log.e(TAG, e.toString(), e);
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<File> csvFiles) {
            progressDialog.dismiss();
            if (!csvFiles.isEmpty()) {
                csVlistAdapter = new CSVlistAdapter(getContext(), csvFiles);
                mRecyclerView.setAdapter(csVlistAdapter);
            }

        }

        private List<File> getCSVFilesFromExternalStorage() {
            List<File> list = new ArrayList<>();
            String externalpath = new String();
            Runtime runtime = Runtime.getRuntime();
            try {
                Process proc = runtime.exec("mount");
                InputStream is = proc.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                String line;

                BufferedReader br = new BufferedReader(isr);
                while ((line = br.readLine()) != null) {
                    if (line.contains("secure")) continue;
                    if (line.contains("asec")) continue;

                    if (line.contains("fat")) {//external card
                        String columns[] = line.split(" ");
                        if (columns != null && columns.length > 1) {
                            externalpath = columns[1];
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString(), e);
            }
            try {
                list = getCSVListFiles(new File(externalpath));
            } catch (Exception e) {
                Log.e(TAG, e.toString(), e);
            }
            return list;
        }

        private ArrayList<File> getCSVListFiles(File parentDir) throws Exception {
            ArrayList<File> inFiles = new ArrayList<>();
            File[] files = parentDir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    inFiles.addAll(getCSVListFiles(file));
                } else {
                    if (file.getAbsoluteFile().getName().endsWith(".csv") || file.getAbsoluteFile().getName().endsWith(".CSV")) {
                        inFiles.add(file);
                    }
                }
            }
            return inFiles;
        }
    }
}

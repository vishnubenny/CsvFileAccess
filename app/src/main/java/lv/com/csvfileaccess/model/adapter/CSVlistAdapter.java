package lv.com.csvfileaccess.model.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import lv.com.csvfileaccess.R;
import lv.com.csvfileaccess.model.csv.CSVFile;
import lv.com.csvfileaccess.model.pojo.RowData;
import lv.com.csvfileaccess.view.ui.activity.MainActivity;

public class CSVlistAdapter extends RecyclerView.Adapter<CSVlistAdapter.VHolder> {
    private final Context mContext;
    private final List<File> csvFiles;

    public CSVlistAdapter(Context context, List<File> csvFiles) {
        this.mContext = context;
        this.csvFiles = csvFiles;
    }

    @Override
    public CSVlistAdapter.VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_csv_file, parent, false);
        return new CSVlistAdapter.VHolder(rootView);
    }

    @Override
    public void onBindViewHolder(CSVlistAdapter.VHolder holder, int position) {
        final File fileItem = csvFiles.get(position);
        holder.csvFileNameTextView.setText(fileItem.getName());
        holder.csvFilePathTextView.setText(fileItem.getAbsolutePath());
        holder.topParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                test(fileItem.getAbsolutePath());
                ((MainActivity) mContext).launchExpandedCSVFragment(fileItem.getAbsolutePath());
            }
        });
    }

    private void test(String fileItemAbsolutePath) {
        try {
            CSVFile csvFile = new CSVFile(new FileInputStream(fileItemAbsolutePath));
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
        } catch (FileNotFoundException e) {
            Log.e("tag", e.toString(), e);
        }
    }

    @Override
    public int getItemCount() {
        return csvFiles.size();
    }

    class VHolder extends RecyclerView.ViewHolder {
        private LinearLayout topParentLayout;
        private TextView csvFileNameTextView;
        private TextView csvFilePathTextView;

        VHolder(View itemView) {
            super(itemView);
            topParentLayout = (LinearLayout) itemView.findViewById(R.id.top_parent_layout);
            csvFileNameTextView = (TextView) topParentLayout.findViewById(R.id.csv_file_name_text_view);
            csvFilePathTextView = (TextView) topParentLayout.findViewById(R.id.csv_file_path_text_view);
        }
    }
}

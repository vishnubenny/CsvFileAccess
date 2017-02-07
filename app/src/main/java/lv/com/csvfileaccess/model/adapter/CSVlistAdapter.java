package lv.com.csvfileaccess.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import lv.com.csvfileaccess.R;

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
        File fileItem = csvFiles.get(position);
        holder.csvFileNameTextView.setText(fileItem.getName());
        holder.csvFilePathTextView.setText(fileItem.getAbsolutePath());
    }

    @Override
    public int getItemCount() {
        return csvFiles.size();
    }

    class VHolder extends RecyclerView.ViewHolder {
        private TextView csvFileNameTextView;
        private TextView csvFilePathTextView;

        VHolder(View itemView) {
            super(itemView);
            csvFileNameTextView = (TextView) itemView.findViewById(R.id.csv_file_name_text_view);
            csvFilePathTextView = (TextView) itemView.findViewById(R.id.csv_file_path_text_view);
        }
    }
}

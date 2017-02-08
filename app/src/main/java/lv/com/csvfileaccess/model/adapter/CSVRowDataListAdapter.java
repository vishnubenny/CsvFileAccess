package lv.com.csvfileaccess.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import lv.com.csvfileaccess.R;

public class CSVRowDataListAdapter extends RecyclerView.Adapter<CSVRowDataListAdapter.VHolder> {
    private final List<String> csvRowDataList;

    public CSVRowDataListAdapter(List<String> csvRowDataList) {
        this.csvRowDataList = csvRowDataList;
    }

    @Override
    public CSVRowDataListAdapter.VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_csv_data, parent, false);
        return new CSVRowDataListAdapter.VHolder(rootView);
    }

    @Override
    public void onBindViewHolder(CSVRowDataListAdapter.VHolder holder, int position) {
        String stringData = csvRowDataList.get(position);
        holder.rowDataListItemTextView.setText(csvRowDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return csvRowDataList.size();
    }

    class VHolder extends RecyclerView.ViewHolder {
        private TextView rowDataListItemTextView;
        private LinearLayout topParentLayout;

        VHolder(View itemView) {
            super(itemView);
            topParentLayout = (LinearLayout) itemView.findViewById(R.id.top_parent_layout);
            rowDataListItemTextView = (TextView) topParentLayout.findViewById(R.id.row_data_list_item_text_view);
        }
    }
}

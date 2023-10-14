package com.example.classroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<Map<String, Object>> dataList;
    private List<Map<String, Object>> selectedItems;


    public MyAdapter(maintenancepage maintenancepage, List<Map<String, Object>> dataList) {
        this.dataList = dataList;
        this.selectedItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemView = inflater.inflate(R.layout.item_layout, parent, false);

        // Return a new holder instance
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        Map<String, Object> item = dataList.get(position);

        // Set the text for the TextViews
        holder.nameTextView.setText((String) item.get("name"));
        holder.sectionTextView.setText((String) item.get("section"));
        holder.emailTextView.setText((String) item.get("email"));
        holder.classroom.setText((String) item.get("classroomNumber"));
        // Set other TextViews with appropriate data
        List<String> selectedIssues = (List<String>) item.get("selectedIssues");

        // Convert the List<String> to a single string representation
        StringBuilder issuesText = new StringBuilder();
        if (selectedIssues != null) {
            for (String issue : selectedIssues) {
                issuesText.append(issue).append(", ");
            }
            // Remove the trailing comma and space
            if (issuesText.length() > 2) {
                issuesText.setLength(issuesText.length() - 2);
            }
        }

        holder.issues.setText(issuesText.toString());
        // Set the item's position as a tag for the checkbox
        holder.checkbox.setTag(position);

        // Set the checkbox state based on item selection
        holder.checkbox.setChecked(selectedItems.contains(item));

        // Set checkbox click listener to handle item selection
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkbox = (CheckBox) v;
                int itemPosition = (int) checkbox.getTag();

                if (checkbox.isChecked()) {
                    selectedItems.add(dataList.get(itemPosition));
                } else {
                    selectedItems.remove(dataList.get(itemPosition));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public List<Map<String, Object>> getSelectedItems() {
        return selectedItems;
    }

    public void clearSelectedItems() {
        selectedItems.clear();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView sectionTextView;
        public TextView emailTextView;
        public TextView issues;
        public TextView classroom;
        public CheckBox checkbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find the views within the item layout
            nameTextView = itemView.findViewById(R.id.nameTextView);
            sectionTextView = itemView.findViewById(R.id.sectionTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
           issues = itemView.findViewById(R.id.issues);
            classroom = itemView.findViewById(R.id.classroom1);
            checkbox = itemView.findViewById(R.id.checkbox);
        }
    }
}


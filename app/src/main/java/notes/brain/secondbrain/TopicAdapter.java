package notes.brain.secondbrain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    private List<String> topicList;

    public TopicAdapter(List<String> topicList) {
        this.topicList = topicList;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        String topicName = topicList.get(position);
        holder.bind(topicName);
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    public void filterList(List<String> filteredList) {
        topicList = filteredList;
        notifyDataSetChanged();
    }

    static class TopicViewHolder extends RecyclerView.ViewHolder {
        private TextView topicNameTextView;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            topicNameTextView = itemView.findViewById(R.id.topicNameTextView);
        }

        public void bind(String topicName) {
            topicNameTextView.setText(topicName);
        }
    }
}

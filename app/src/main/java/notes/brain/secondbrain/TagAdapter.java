package notes.brain.secondbrain;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import notes.brain.secondbrain.DBHelper.DatabaseContract;
public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {

    private List<String> tagList;
    private SQLiteDatabase db;
    private Context context;

    public TagAdapter(List<String> tagList, SQLiteDatabase db, Context context) {
        this.tagList = tagList;
        this.db = db;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String tagName = tagList.get(position);
        holder.tagNameTextView.setText(tagName);
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tagNameTextView;
        TextView deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            tagNameTextView = itemView.findViewById(R.id.tagNameTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String selectedTag = tagList.get(position);
                        deleteTag(selectedTag);
                    }
                }
            });
        }
    }

    private void deleteTag(String tagName) {
        String selection = DatabaseContract.Tags.COLUMN_NAME_TAG_NAME + "=?";
        String[] selectionArgs = {tagName};
        int rowsDeleted = db.delete(DatabaseContract.Tags.TABLE_NAME, selection, selectionArgs);

        if (rowsDeleted > 0) {
            tagList.remove(tagName);
            notifyDataSetChanged();
            Toast.makeText(context, "Tag deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to delete tag", Toast.LENGTH_SHORT).show();
        }
    }
}

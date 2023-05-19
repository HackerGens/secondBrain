package notes.brain.secondbrain;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import notes.brain.secondbrain.DBHelper.DatabaseContract;
import notes.brain.secondbrain.DBHelper.DatabaseHelper;

public class TagActivity extends AppCompatActivity {

    private EditText tagEditText;
    private Button saveTagButton;
    private ListView tagListView;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    private List<String> tagList;
    private ArrayAdapter<String> tagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        tagEditText = findViewById(R.id.tagEditText);
        saveTagButton = findViewById(R.id.saveTagButton);
        tagListView = findViewById(R.id.tagListView);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        // Set up tag list and adapter
        tagList = new ArrayList<>();
        tagAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tagList);
        tagListView.setAdapter(tagAdapter);

        // Set up click listener for save tag button
        saveTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tagName = tagEditText.getText().toString().trim();
                if (!tagName.isEmpty()) {
                    saveTag(tagName);
                    tagEditText.setText("");
                }
            }
        });

        // Load tags from database
        loadTags();
    }

    private void saveTag(String tagName) {
        ContentValues tagValues = new ContentValues();
        tagValues.put(DatabaseContract.Tags.COLUMN_NAME_TAG_NAME, tagName);
        long tagId = db.insert(DatabaseContract.Tags.TABLE_NAME, null, tagValues);

        if (tagId != -1) {
            tagList.add(tagName);
            tagAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Tag saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save tag", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadTags() {
        Cursor cursor = db.query(DatabaseContract.Tags.TABLE_NAME, null, null, null, null, null, null);
        tagList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String tagName = cursor.getString(cursor.getColumnIndex(DatabaseContract
                        .Tags.COLUMN_NAME_TAG_NAME));
                tagList.add(tagName);
            } while (cursor.moveToNext());
        }

        cursor.close();

        tagAdapter.clear();
        tagAdapter.addAll(tagList);
        tagAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
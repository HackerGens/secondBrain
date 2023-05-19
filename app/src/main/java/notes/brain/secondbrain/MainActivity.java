package notes.brain.secondbrain;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import notes.brain.secondbrain.DBHelper.DatabaseContract;
import notes.brain.secondbrain.DBHelper.DatabaseHelper;
import notes.brain.secondbrain.TopicAdapter;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    private RecyclerView recyclerView;
    private TopicAdapter adapter;
    private List<String> dataList;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataList = new ArrayList<>();
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TopicAdapter(dataList);
        recyclerView.setAdapter(adapter);

        searchEditText = findViewById(R.id.searchEditText);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        checkAndRequestPermission();
    }

    private void checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        } else {
            fetchRecords();
        }
    }

    private void fetchRecords() {
        dataList.clear();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseContract.Topics.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String topicName = cursor.getString(cursor.getColumnIndex(DatabaseContract.Topics.COLUMN_NAME_TOPIC_NAME));
                dataList.add(topicName);
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter.notifyDataSetChanged();
    }

    private void filterData(String query) {
        List<String> filteredList = new ArrayList<>();

        for (String data : dataList) {
            if (data.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(data);
            }
        }

        adapter.filterList(filteredList);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchRecords();
            } else {
                Toast.makeText(this, "Permission denied. Cannot access the database.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

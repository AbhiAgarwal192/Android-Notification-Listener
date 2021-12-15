package com.abhiagarwal.readwhatsappnotifications;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abhiagarwal.readwhatsappnotifications.prefstore.PrefStore;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView textView;
    PrefStore prefStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefStore = new PrefStore();
        setAdapter();

        FloatingActionButton delete = findViewById(R.id.delete);
        FloatingActionButton refresh = findViewById(R.id.refresh);

        delete.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                clear();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        View empty = findViewById(R.id.emptyView);
        if(listView == null){
            listView = (ListView)findViewById(R.id.listView);
        }

        if(empty!=null){
            listView.setEmptyView(empty);
        }
    }

    public void setAdapter(){
        prefStore.initialize(getApplicationContext());

        listView = (ListView)findViewById(R.id.listView);
        textView = (TextView)findViewById(R.id.textView);
        List<String> messageList = new ArrayList<String>();
        messageList.addAll(prefStore.read(getApplicationContext()));

        final ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.list_item,messageList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public void refresh(){
        List<String> messageList = new ArrayList<String>();
        messageList.addAll(prefStore.read(getApplicationContext()));
        final ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.list_item,messageList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void clear(){
        prefStore.clearAll(getApplicationContext());
        refresh();
    }
}
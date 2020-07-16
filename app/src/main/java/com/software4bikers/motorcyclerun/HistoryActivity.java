package com.software4bikers.motorcyclerun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.software4bikers.motorcyclerun.adapters.SessionsDataAdapter;
import com.software4bikers.motorcyclerun.models.data.SessionsData;
import com.software4bikers.motorcyclerun.repositories.SessionRepository;
import com.software4bikers.motorcyclerun.sqlite.RunSessionModel;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listView = findViewById(R.id.session_listView);
        RunSessionModel runSessionModel = new RunSessionModel(this);
        Cursor res = runSessionModel.getAllSessions();
        if(res.getCount() == 0){
            Toast.makeText(this, "No history data", Toast.LENGTH_LONG).show();
        }else{
            ArrayList<SessionsData> list = SessionRepository.getSessionsData(res);
            SessionsDataAdapter sessionsDataAdapter = new SessionsDataAdapter(getApplicationContext(), R.layout.list_item_sessions, this);

            for(SessionsData s : list){
                sessionsDataAdapter.add(s);
            }

            listView.setAdapter(sessionsDataAdapter);

        }

    }

    public void startGpxActivity(String sessionId){
        Intent intent = new Intent(this, GpxActivity.class);
        intent.putExtra("sessionId", sessionId);
        startActivity(intent);
    }
}
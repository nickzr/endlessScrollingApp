package com.example.nikolaj.loadsyllabusapp;

import android.app.ListActivity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.nikolaj.loadsyllabusapp.DALC.BESyllabus;
import com.example.nikolaj.loadsyllabusapp.DALC.Syllabuses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String TAG = "SYLLABUS";
    ListView lvSyllabus;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeTask t = new InitializeTask(this);
        t.execute(new Syllabuses());

        lvSyllabus = (ListView) findViewById(R.id.lvSyllabus);
        lvSyllabus.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                //triggered only when new data needs to be appended to the list
                customLoadMoreDataFromApi(page);
                //or customLoadMoreDataFromApi(totalItemsCount);
                return true;
            }
        });
    }

    public void customLoadMoreDataFromApi(int offset){
        //
    }

    private List<Map<String, String>> asListMap(List<BESyllabus> src)
    {
        final List<Map<String, String>> res = new ArrayList<Map<String, String>>();

        for (BESyllabus s : src)
        {
            Map<String, String> e = new HashMap<String, String>();
            e.put("id", s.getId());
            e.put("year", "" + s.getYear());
            e.put("title", s.getTitle());
            e.put("lecturer", s.getLecturer());
            res.add(e);
        }
        return res;
    }

    public SimpleAdapter createAdapter(ArrayList<BESyllabus> ms) {
        SimpleAdapter adapter = new MyAdapter(this,
                asListMap(ms),
                R.layout.cell,
                new String[] { "id", "year", "title", "lecturer" },
                new int[]    {  R.id.txtId, R.id.txtYear, R.id.txtTitle, R.id.txtLecturer });
        return adapter;
    }

    public void initializeData(final ArrayList<BESyllabus> s)
    {
        adapter = createAdapter(s);
        lvSyllabus.setAdapter(adapter);

        Log.d(TAG, "Adapter attached");

        lvSyllabus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                BESyllabus m_s = s.get(position);

                String msg = m_s.getTitle();

                Toast.makeText(getApplicationContext(),
                        msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

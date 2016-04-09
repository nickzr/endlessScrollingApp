package com.example.nikolaj.loadsyllabusapp;

import android.os.AsyncTask;

import com.example.nikolaj.loadsyllabusapp.DALC.BESyllabus;
import com.example.nikolaj.loadsyllabusapp.DALC.Syllabuses;

import java.util.ArrayList;

/**
 * Created by Nikolaj on 08/04/2016.
 */
public class InitializeTask extends AsyncTask<
        Syllabuses,
        Void,
        ArrayList<BESyllabus>>
{
    MainActivity m_context;

    public InitializeTask(MainActivity context)
    {
        m_context = context;
    }
    @Override
    protected ArrayList<BESyllabus> doInBackground(Syllabuses... ms) {
        // params comes from the execute()

        //ms[0].loadAll();
        ms[0].loadPage(10, 1);
        return ms[0].getAll();
    }
    // onPostExecute displays the results of the AsyncTask.doInBackground().
    // this method is invoked by the GUI thread
    @Override
    protected void onPostExecute(final ArrayList<BESyllabus> syllabuses) {
        m_context.initializeData(syllabuses);

    }
}

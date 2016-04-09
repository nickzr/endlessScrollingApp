package com.example.nikolaj.loadsyllabusapp.DALC;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Nikolaj on 08/04/2016.
 */
public class Syllabuses {
    private final String URL = "http://courseplanner-lbilde.rhcloud.com/api/syllabuses";

    private final String TAG = "SYLLABUS";

    ArrayList<BESyllabus> m_syllabuses;

    public Syllabuses(){
        m_syllabuses = new ArrayList<BESyllabus>();
    }

    public void loadPage(int limit, int page){
        try{
            String result = getContent(URL + "?limit=" + limit + "&page=" + page);
            if(result == null) return;

            JSONObject object = new JSONObject(result);
            JSONArray array = object.getJSONArray("docs");

            for (int i = 0; i < array.length(); i++) {
                JSONObject d = array.getJSONObject(i);
                int year = Integer.parseInt(d.getString("year"));
                String lecturer = "";

                if(d.has("lecturer")){
                    lecturer = d.getString("lecturer");
                }else{
                    lecturer = "No lecturer set";
                }

                BESyllabus s = new BESyllabus(d.getString("_id"), year, d.getString("title"), lecturer);
                m_syllabuses.add(s);
            }
        }
        catch(JSONException e){
            Log.e(TAG, "There was an error parsing the JSON", e);
        }catch (Exception e)
        {  Log.d(TAG, "General exception in loadAll " + e.getMessage());
        }
    }

    public void loadAll()
    {
        try {
            String result = getContent(URL + "?limit=10&page=1");

            if (result == null) return;

            JSONObject object = new JSONObject(result);
            JSONArray array = object.getJSONArray("docs");

            for (int i = 0; i < array.length(); i++) {
                JSONObject d = array.getJSONObject(i);
                int year = Integer.parseInt(d.getString("year"));
                String lecturer = "";

                if(d.has("lecturer")){
                    lecturer = d.getString("lecturer");
                }else{
                    lecturer = "No lecturer set";
                }

                BESyllabus s = new BESyllabus(d.getString("_id"), year, d.getString("title"), lecturer);
                m_syllabuses.add(s);
            }

        } catch (JSONException e) {
            Log.e(TAG,
                    "There was an error parsing the JSON", e);
        } catch (Exception e)
        {  Log.d(TAG, "General exception in loadAll " + e.getMessage());
        }
    }

    public ArrayList<BESyllabus> getAll()
    { return m_syllabuses; }

    /**
     * Get the content of the url as a string. Based on using a scanner.
     * @param urlString - the url must return data typical in either json, xml, csv etc..
     * @return the content as a string. Null is something goes wrong.
     */
    private String getContent(String urlString)
    {
        StringBuilder sb = new StringBuilder();
        try {
            java.net.URL url = new URL(urlString);
            Scanner s = new Scanner(url.openStream());

            while (s.hasNextLine()) {
                String line = s.nextLine();
                sb.append(line);
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }
}

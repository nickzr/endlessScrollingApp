package com.example.nikolaj.loadsyllabusapp.DALC;

/**
 * Created by Nikolaj on 08/04/2016.
 */
public class BESyllabus {
    private String m_id;
    private int m_year;
    private String m_title;
    private String m_lecturer;

    public BESyllabus (String id, int year, String title, String lecturer){
        this.m_id = id;
        this.m_year = year;
        this.m_title = title;
        this.m_lecturer = lecturer;
    }

    public String getId(){
        return m_id;
    }

    public void setYear(int year){
        this.m_year = year;
    }

    public int getYear(){
        return m_year;
    }

    public void setTitle(String title){
        this.m_title = title;
    }

    public String getTitle(){
        return m_title;
    }

    public void setLecturer(String lecturer){
        this.m_lecturer = lecturer;
    }

    public String getLecturer(){
        return m_lecturer;
    }
}

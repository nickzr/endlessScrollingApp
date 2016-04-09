package com.example.nikolaj.loadsyllabusapp;

import android.widget.AbsListView;

/**
 * Created by Nikolaj on 09/04/2016.
 */
public abstract class EndlessScrollListener implements AbsListView.OnScrollListener{
    private int visibleThreshold = 5; //minimum amount of items below current scroll position
    private int currentPage = 0; //offset index of data loaded
    private int previousTotalItemCount = 0; //total amount of items after last load
    private boolean loading = true; //true while waiting for last set of data to load
    private int startingPageIndex = 0; //set starting page index

    public EndlessScrollListener(){}

    public EndlessScrollListener(int visibleThreshold){
        this.visibleThreshold = visibleThreshold;
    }

    public EndlessScrollListener(int visibleThreshold, int startPage){
        this.visibleThreshold = visibleThreshold;
        this.startingPageIndex = startPage;
        this.currentPage = startPage;
    }

    //onScroll is called several times per second while scrolling
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //if the total item count is zero and the previous isn't,
        //assume the list is invalidated and should reset back to initial state
        if(totalItemCount < previousTotalItemCount){
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if(totalItemCount == 0){this.loading = true;}
        }

        //if it's still loading, we check to see if the dataset count has changed
        //in this case we assume it is done loading and update the page number and total item count
        if(loading && (totalItemCount > previousTotalItemCount)){
            loading = false;
            previousTotalItemCount = totalItemCount;
            currentPage++;
        }

        //if it's not current loading, check if we have breached the visible threshold
        //and need to reload more data
        //if we need to reload more, perform the onLoadMore method to fetch data
        if(!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)){
            loading = onLoadMore(currentPage +1, totalItemCount);
        }
    }

    //Defines the process for loading more data based on page
    //returns true if more data is being loaded, false if no more data to load
    public abstract boolean onLoadMore(int page, int totalItemsCount);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //dont perform any actions on changed
    }
}


package com.example.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.activity.R;
import com.example.adapter.MainListAdapter;
import com.example.beans.SyndFeed;
import com.example.utils.RssUtil;

public class PlaceholderFragment extends Fragment {

    private ListView mList;
    //private Context mContext;
    private MainListAdapter mListAdatper;
    private final static int REFRESH_CONTENT = 0;
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_CONTENT:
                    if(mListAdatper == null) {
                        mListAdatper = new MainListAdapter(getActivity(), ((SyndFeed) msg.obj).getEntries());
                        mList.setAdapter(mListAdatper);
                    }
                    break;

                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //mContext = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mList = (ListView) rootView.findViewById(R.id.main_lv);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SyndFeed feed = RssUtil.getRssItems("http://www.toodaylab.com/feed");
                Message.obtain(myHandler, REFRESH_CONTENT, feed).sendToTarget();
            }
        }).start();
        return rootView;
    }
}

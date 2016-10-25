package com.leejangyoun.interestview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.greenfrvr.hashtagview.HashtagView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context mContext;

    LinearLayout mLinear;
    LayoutInflater mInflater;

    TextView mTxtCount;

    ArrayList<Interest> mChosenList;

    RequestQueue mQueue;

    // =======================================================================
    // METHOD : onCreate
    // =======================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mTxtCount = (TextView) findViewById(R.id.txt_count);

        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLinear   = (LinearLayout) findViewById(R.id.linear);

        mChosenList = new ArrayList<>();

        //set http queue
        mQueue = Volley.newRequestQueue(mContext);
        String url = "http://leejangyoun.com/android/dummy/InterestView.json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("TEST", response);
                        JsonObject root = new JsonParser().parse(response).getAsJsonObject();

                        for (JsonElement jEleMain : root.get("interests").getAsJsonArray()) {
                            JsonObject jMainObj = jEleMain.getAsJsonObject();
                            String typeName = jMainObj.get("TypeName").getAsString();

                            List<Interest> interestArr = new ArrayList<>();
                            for (JsonElement jEleSub : jMainObj.get("interest").getAsJsonArray()) {
                                JsonObject jSubObj = jEleSub.getAsJsonObject();
                                int     no     = jSubObj.get("no").getAsInt();
                                String  name   = jSubObj.get("name").getAsString();
                                boolean chosen = jSubObj.get("chosen").getAsBoolean();

                                Interest interest = new Interest(no, name);
                                interestArr.add(interest);
                                if (chosen) mChosenList.add(interest);
                            }

                            View view = mInflater.inflate(R.layout.cell_in_interest, null);

                            ((TextView)view.findViewById(R.id.txt_title)).setText(typeName);

                            HashtagView hashtagView = (HashtagView)view.findViewById(R.id.interest_tag);
                            //hashtagView.setTypeface(FontCache.get(Common.FONT, mContext));
                            hashtagView.setRowMode(HashtagView.MODE_WRAP);
                            hashtagView.addOnTagSelectListener(new CustomTagsSelectListener());
                            hashtagView.setData(interestArr, new CustomDataSelector());
                            mLinear.addView(view);
                        }

                        mTxtCount.setText(mChosenList.size()+"");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        mQueue.add(stringRequest);
    }

    // =======================================================================
    // METHOD : onClickConfirm
    // =======================================================================
    public void onClickConfirm(View view) {
        Toast.makeText(mContext, "ChosenList : " + mChosenList, Toast.LENGTH_SHORT).show();
    }

    // =======================================================================
    // METHOD : CustomTagsSelectListener
    // =======================================================================
    class CustomTagsSelectListener implements HashtagView.TagsSelectListener {
        @Override
        public void onItemSelected(Object item, boolean selected) {
            Interest interest = (Interest) item;

            if (selected) {
                if( ! mChosenList.contains(interest))
                    mChosenList.add(interest);

            } else {
                if(mChosenList.contains(interest))
                    mChosenList.remove(interest);

            }
            mTxtCount.setText(mChosenList.size()+"");
        }
    }

    // =======================================================================
    // METHOD : CustomDataSelector
    // =======================================================================
    class CustomDataSelector implements HashtagView.DataSelector<Interest> {
        @Override
        public boolean preselect(Interest item) {
            return mChosenList.contains(item) ? true : false;
        }
    }

}

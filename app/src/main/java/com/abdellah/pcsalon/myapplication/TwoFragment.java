package com.abdellah.pcsalon.myapplication;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class TwoFragment extends Fragment{


    private static final String TAG ="TwoFragement" ;
    static RelativeLayout relativeLayout;

    public TwoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

      /*  relativeLayout=new RelativeLayout(getActivity());
        //relativeLayout.addChildrenForAccessibility(new ArrayList<View>(2).add(0,new ImageView(R.mipmap.cible)));
        ImageView iCible=new ImageView(getActivity());
        ImageView iReticule=new ImageView(getActivity());

        iCible.setImageResource(R.mipmap.client_cible);
        iReticule.setImageResource(R.mipmap.reticule);

        iCible.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        iReticule.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));



        relativeLayout.addView(iCible);
        relativeLayout.addView(iReticule);


        */
       // relativeLayout.addChildrenForAccessibility((((new ArrayList<View>(2)).add(0,iCible)).add(1,iReticule)));
        return inflater.inflate(R.layout.fragment_3, container, false);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Destroying...");
        super.onDestroy();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "Stopping...");
        super.onStop();
    }

}

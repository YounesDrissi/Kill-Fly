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

    private static float xZero;
    private static float yZero;
    private static float un=30;
    private static float deux=83;
    private static float trois=136;
    private static float quatre=189;
    private static float cinq=quatre+53;
    private static float six=cinq+53;
    private static float sept=six+53;
    private static float huite=sept+53;
    private static float other=huite+53;



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
=======
        relativeLayout=new RelativeLayout(getActivity());
>>>>>>> eae607d0857ac7e65ebb382062d3167bf80d3b96
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
<<<<<<< HEAD


        */
       // relativeLayout.addChildrenForAccessibility((((new ArrayList<View>(2)).add(0,iCible)).add(1,iReticule)));
        return inflater.inflate(R.layout.cible, container, false);

    }

    public static float getxZero() {
        return xZero;
    }

    public static float getUn() {
        return un;
    }

    public static float getDeux() {
        return deux;
    }

    public static float getTrois() {
        return trois;
    }

    public static float getQuatre() {
        return quatre;
    }

    public static float getCinq() {
        return cinq;
    }

    public static float getSix() {
        return six;
    }

    public static float getSept() {
        return sept;
    }

    public static float getHuite() {
        return huite;
    }

    public static float getOther() {
        return other;
    }

    public static void setxZero(float xZero) {
        TwoFragment.xZero = xZero;
    }

    public static float getyZero() {
        return yZero;
    }

    public static void setyZero(float yZero) {
        TwoFragment.yZero = yZero;
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

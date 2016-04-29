package com.abdellah.pcsalon.myapplication.dynamicGraph;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdellah.pcsalon.myapplication.Fragments;

import org.achartengine.GraphicalView;

public class DynamicGraphActivity extends Fragment {

	private static GraphicalView view;
	private LineGraph line = new LineGraph();
	private static Thread thread;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


//        view.repaint();
		thread = new Thread() {
			public void run()
			{
				for (int i = 0; i < 100; i++)
				{
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Point p = MockData.getDataFromReceiver(i); // We got new data!
			//		line.addNewPoints(p); // Add it to our graph
					view.repaint();
				}
			}
		};
		//thread.start();
	}

	public LineGraph getLine() {
		return line;
	}


	@Nullable
	@Override
	public GraphicalView getView() {
		return view;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		super.onStart();
		view = line.getView(Fragments.contex);

		return view;
	}

}
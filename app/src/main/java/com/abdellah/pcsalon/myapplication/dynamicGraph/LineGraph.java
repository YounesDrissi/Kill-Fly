package com.abdellah.pcsalon.myapplication.dynamicGraph;

import android.content.Context;
import android.graphics.Color;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class LineGraph {

	private GraphicalView view;
	
	private XYSeriesExtends dataset = new XYSeriesExtends("Rain Fall");
	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	
	private XYSeriesRenderer renderer = new XYSeriesRenderer(); // This will be used to customize line 1
	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // Holds a collection of XYSeriesRenderer and customizes the graph
	
	public LineGraph()
	{
		// Add single dataset to multiple dataset
		mDataset.addSeries(dataset);

        //
        //mRenderer.setZoomLimits(new double[]{00, 200, 00, 30});
        // Customization time for line 1!
        renderer.setColor(Color.BLACK);
		renderer.setPointStyle(PointStyle.X);

        renderer.setChartValuesTextSize(30);


		renderer.setFillPoints(true);

        renderer.setDisplayChartValues(true);
        //couleur margin
        mRenderer.setMarginsColor(Color.BLACK);

        // Enable Zoom
		mRenderer.setZoomButtonsVisible(true);

       // mRenderer.setGridColor(Color.RED);

        //rendu des valeurs
        mRenderer.setYAxisMin(0);
        mRenderer.setXAxisMin(0);
        mRenderer.setXAxisMax(20);
        mRenderer.setYAxisMax(15);

        //
        mRenderer.setXLabelsColor(Color.RED);
        mRenderer.setLabelsTextSize(30);
        mRenderer.setPointSize(10);
        mRenderer.setGridColor(Color.RED);

        mRenderer.setAxisTitleTextSize(30);
        mRenderer.setXTitle("Temps");
        mRenderer.setYTitle("Intensité du vents");
        mRenderer.setYLabelsColor(0,Color.RED);


		// Add single renderer to multiple renderer
		mRenderer.addSeriesRenderer(renderer);	
	}
	
	public GraphicalView getView(Context context) 
	{
		view =  ChartFactory.getLineChartView(context, mDataset, mRenderer);
		return view;
	}
	
	public void addNewPoints(Point p)
	{
		dataset.add(p.getX(), p.getY(),p.getX()+10);

	}
	
}

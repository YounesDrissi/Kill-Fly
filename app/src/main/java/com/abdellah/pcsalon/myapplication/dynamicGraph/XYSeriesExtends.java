package com.abdellah.pcsalon.myapplication.dynamicGraph;

import org.achartengine.model.XYSeries;
import org.achartengine.util.IndexXYMap;

/**
 * Created by PC SALON on 26/02/2016.
 */
public class XYSeriesExtends extends XYSeries {

    private final IndexXYMap<Double, Double> mXYy=new IndexXYMap<Double,Double>();

    public XYSeriesExtends(String title) {
        super(title);
    }


    public synchronized void add(int  x, double y,double z) {
        super.add((double) x, y);
        this.mXYy.put((double) x, z);
    }

    @Override
    public synchronized double getY(int index) {
        return ((Double)this.mXYy.getYByIndex(index)).doubleValue();
    }
}

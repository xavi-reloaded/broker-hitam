package org.apiumtech.brokerhitam;

import cern.colt.list.DoubleArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: xavi
 * Date: 6/9/13
 * Time: 8:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class HelperTests {

    public static double sampleStock;


    public static DoubleArrayList getDoubleArrayList() {
        DoubleArrayList data = new DoubleArrayList();
        data.add(1.2);
        data.add(1.3);
        data.add(1.4);
        data.add(1.5);
        return data;
    }

}

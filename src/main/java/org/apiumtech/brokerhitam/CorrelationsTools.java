package org.apiumtech.brokerhitam;

import cern.colt.list.DoubleArrayList;
import cern.jet.stat.Descriptive;

/**
 * Created with IntelliJ IDEA.
 * User: xavi
 * Date: 6/9/13
 * Time: 8:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class CorrelationsTools {


    /*
        Implementation of Pearson Correlation

        http://en.wikipedia.org/wiki/Pearson_product-moment_correlation_coefficient

     */
    public static double getPearsonCorrelation(DoubleArrayList data, DoubleArrayList data1) {

        double[] scores1 = data.elements();
        double[] scores2 = data1.elements();

        double result = 0;
        double sum_sq_x = 0;
        double sum_sq_y = 0;
        double sum_coproduct = 0;
        double mean_x = scores1[0];
        double mean_y = scores2[0];
        for(int i=2;i<scores1.length+1;i+=1){
            double sweep =Double.valueOf(i-1)/i;
            double delta_x = scores1[i-1]-mean_x;
            double delta_y = scores2[i-1]-mean_y;
            sum_sq_x += delta_x * delta_x * sweep;
            sum_sq_y += delta_y * delta_y * sweep;
            sum_coproduct += delta_x * delta_y * sweep;
            mean_x += delta_x / i;
            mean_y += delta_y / i;
        }
        double pop_sd_x = (double) Math.sqrt(sum_sq_x/scores1.length);
        double pop_sd_y = (double) Math.sqrt(sum_sq_y/scores1.length);
        double cov_x_y = sum_coproduct / scores1.length;
        result = cov_x_y / (pop_sd_x*pop_sd_y);
        return result;
    }


    public static double getTrendByMinimumSquare(DoubleArrayList data) {

        DoubleArrayList list = new DoubleArrayList();

        double xy = 0.0;
        int n = data.size();

        for (int x=0;x<=n-1;x++)
        {
            list.add(x+1);
            xy = (x+1) * data.get(x) + xy;
        }

        double x  = Descriptive.sum(list);
        double y  = Descriptive.sum(data);
        double x2 = Descriptive.sumOfSquares(list);
        double y2 = Descriptive.sumOfSquares(data);

        double a01 = ((y * x2) - (x * xy));
        double a02 = (n*x2) - ( x * x );
        double a03 = (n*xy) - ( x * y );

        double a0 = a01 / a02;
        double a1 = a03 / a02;

        double trend = a0 + (a1*(n+1));
        return trend ;
    }
}

package org.apiumtech.brokerhitam.trade.pso;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SimpleParticleSwarmOptimizerTest{

    @Test
    public void testLearn() {
        final FitnessFunction fitness = new FitnessFunction() {
            public double evaluate(double[] x) {
                return x[0] * x[0]; // 1d sphere function
            }
        };

        SimpleParticleSwarmOptimizer pso = new SimpleParticleSwarmOptimizer(fitness, new double[]{-5.0}, new double[]{5.0}, 50);

        Assert.assertEquals(fitness.evaluate(pso.learn()), 0.0, 0.01);
    }
}

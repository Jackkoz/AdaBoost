package com.company.Examples;

import com.company.Utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class Main
{
    static ArrayList<Double> normalize(ArrayList<Double> f)
    {
        double sum = 0;
        for (double x : f)
            sum += x;

        double factor = 1d / sum;

        ArrayList<Double> ret = new ArrayList<>();
        for (int i = 0; i < f.size(); i++)
            ret.add(i, f.get(i) * factor);

        return ret;
    }

    public static void main(String[] args)
    {
        final int ITERATIONS = 2;

        Pair data[] = {new Pair(0, -1), new Pair(1, 1), new Pair(1, 1)};
        int[] h = {1, -1};

        // Initialized properly below
        @SuppressWarnings("unchecked")
        ArrayList<Double>[] D = new ArrayList[ITERATIONS + 1];
        for (int i = 0; i < D.length; i++)
            D[i] = new ArrayList<>();

        for (Pair ignored : data)
            D[0].add(1d / data.length);

        double[] eps = new double[ITERATIONS];
        Arrays.fill(eps, 0);
        double[] alpha = new double[ITERATIONS];

        for (int i = 0; i < ITERATIONS; i++)
        {
            for (int j = 0; j < data.length; j++)
            {
                if (h[i] != data[j].second)
                    eps[i] += D[i].get(j);
            }

            alpha[i] = 0.5 * Math.log((1 - eps[i]) / eps[i]);

            for (int j = 0; j < data.length; j++)
            {
                double val = D[i].get(j);
                if (h[i] == data[j].second)
                    val *= Math.exp(-alpha[i]);
                else
                    val *= Math.exp(alpha[i]);
                D[i + 1].add(j, val);
            }

            D[i + 1] = normalize(D[i + 1]);
        }

        for (int i = 0; i < ITERATIONS; i++)
        {
            System.out.println("Alfa: " + alpha[i]);
            System.out.println("Epsilon: " + eps[i]);
            System.out.println("H: " + h[i]);
            System.out.println("Rozkład: " + D[i]);
            System.out.println();
        }
    }
}

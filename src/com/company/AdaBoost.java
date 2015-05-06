package com.company;

import com.company.Classifiers.Classifier;
import com.company.Utils.BadInputException;
import com.company.Utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class AdaBoost<Type>
{
    // Dane
    private ArrayList<Pair<Type>> data;
    // Klasyfikatory
    private ArrayList<Classifier<Type>> H;
    // Liczba iteracji algorytmu
    private int iterations;
    // Rozkłady
    private ArrayList<ArrayList<Double>> D;
    // Błędy klasyfikatorów
    private double[] eps;
    // Wagi klasyfikatorów
    private double[] alpha;

    public AdaBoost(ArrayList<Pair<Type>> data, ArrayList<Classifier<Type>> classifiers) throws BadInputException
    {
        if (data == null || classifiers == null || data.size() == 0 || classifiers.size() == 0)
            throw new BadInputException();

        this.data = data;
        H = classifiers;
        iterations = H.size();

        D = new ArrayList<>(iterations + 1);

        for (int i = 0; i < iterations + 1; i++)
            D.add(new ArrayList<Double>());

        for (Pair ignored : data)
            D.get(0).add(1d / data.size());

        eps = new double[iterations];
        Arrays.fill(eps, 0);
        alpha = new double[iterations];
    }

    private Classifier<Type> chooseBest(ArrayList<Double> D)
    {
        Classifier<Type> best = H.get(0);
        double bestEpsilon = 2;

        for (Classifier<Type> aH : H)
        {
            double eps = 0;
            for (int j = 0; j < data.size(); j++)
                if (aH.classify(data.get(j).first) != data.get(j).second)
                    eps += D.get(j);
            if (Math.abs(1/2 - eps) < bestEpsilon)
            {
                bestEpsilon = Math.abs(1/2 - eps);
                best = aH;
            }
        }

        return best;
    }

    public Classifier createClassifier()
    {
        final ArrayList<Classifier<Type>> best = new ArrayList<>(iterations);
        for (int i = 0; i < iterations; i++)
        {
            best.add(i, chooseBest(D.get(i)));
            for (int j = 0; j < data.size(); j++)
                if (best.get(i).classify(data.get(j).first) != data.get(j).second)
                    eps[i] += D.get(i).get(j);

            if (eps[i] > 0.5)
            {
                eps[i] = 1 - eps[i];
                final int temp = i;
                best.set(i, new Classifier<Type>()
                {
                    Classifier<Type> old = H.get(temp);

                    @Override
                    public int classify(Type x)
                    {
                        return -1 * old.classify(x);
                    }
                });
            }

            alpha[i] = 0.5 * Math.log((1 - eps[i]) / eps[i]);

            System.out.println(i + " " + eps[i] + " " + alpha[i]);

            for (int j = 0; j < data.size(); j++)
            {
                double val = D.get(i).get(j);
                val *= Math.exp(alpha[i] * (best.get(i).classify(data.get(j).first) == data.get(j).second ? -1 : 1));
                D.get(i + 1).add(j, val);
            }

            normalize(D.get(i + 1));
        }

        System.out.println(D.get(D.size() - 1));

        return new Classifier<Type>()
        {
            private ArrayList<Classifier<Type>> H1 = best;
            private double[] alpha1 = alpha;

            @Override
            public int classify(Type x)
            {
                double sum = 0;
                for (int i = 0; i < H1.size(); i++)
                {
                    sum += H1.get(i).classify(x) * alpha1[i];
                }
                System.out.println(sum);
                return sum >= 0 ? 1 : -1;
            }
        };
    }

    private static void normalize(ArrayList<Double> f)
    {
        double sum = 0;
        for (double x : f)
            sum += x;

        double factor = 1d / sum;

        for (int i = 0; i < f.size(); i++)
            f.set(i, f.get(i) * factor);
    }
}

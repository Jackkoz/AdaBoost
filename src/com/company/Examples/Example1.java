package com.company.Examples;

import com.company.AdaBoost;
import com.company.Classifiers.Classifier;
import com.company.Classifiers.ConstantClassifier;
import com.company.Utils.Pair;

import java.util.ArrayList;

public class Example1
{
    public static void main(String[] args)
    {
        ArrayList<Pair<Integer>> data = new ArrayList<>(3);
            data.add(new Pair<>(0, -1));
            data.add(new Pair<>(1, 1));
            data.add(new Pair<>(1, 1));
        ArrayList<Classifier<Integer>> H = new ArrayList<>(2);
            H.add(new ConstantClassifier<Integer>(1));
            H.add(new ConstantClassifier<Integer>(-1));

        System.out.println(new AdaBoost<>(data, H).createClassifier().classify(5));
    }
}

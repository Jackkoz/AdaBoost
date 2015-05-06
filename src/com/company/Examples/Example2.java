package com.company.Examples;

import com.company.AdaBoost;
import com.company.Classifiers.Classifier;
import com.company.Classifiers.LinearClassifier;
import com.company.Utils.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Example2
{
    public static void main(String[] args)
    {
        ArrayList<Pair<Integer>> data = new ArrayList<>(27);
        Set<Integer> plus = new HashSet<>();
        Set<Integer> minus = new HashSet<>();
        plus.add(0);
        plus.add(2);
        plus.add(6);
        plus.add(8);
        plus.add(18);
        plus.add(20);
        plus.add(24);
        plus.add(26);
        for (int i = 0; i < 27; i++)
            minus.add(i);
        for (Integer i : plus)
            minus.remove(i);

        for (Integer i : plus)
            data.add(new Pair<>(i, 1));
        for (Integer i : minus)
            data.add(new Pair<>(i, -1));

        ArrayList<Classifier<Integer>> classifiers = new ArrayList<>();
        for (int i = 0; i < 27; i++)
        {
            classifiers.add(new LinearClassifier<Integer>(i, false));
        }

        for (int i = 0; i < 27; i++)
        {
            classifiers.add(new LinearClassifier<Integer>(i, true));
        }

        Classifier<Integer> c2 = new AdaBoost<>(data, classifiers).createClassifier();
        System.out.println("\n*****\n*****\n*****\n");
        for (int i = 0; i < 27; i++)
        {
            System.out.print(i + " ");
            c2.classify(i);
        }
    }
}

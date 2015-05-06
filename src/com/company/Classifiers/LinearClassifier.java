package com.company.Classifiers;

public class LinearClassifier<Type extends Comparable<Type>> extends Classifier<Type>
{
    private Type v;
    private boolean greater;

    public LinearClassifier(Type t, boolean b)
    {
        v = t;
        greater = b;
    }

    @Override
    public int classify(Type x)
    {
        return (x.compareTo(v) == 1) == greater ? -1 : 1;
    }

}

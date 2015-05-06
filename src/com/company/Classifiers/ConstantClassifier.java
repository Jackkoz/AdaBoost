package com.company.Classifiers;

/*
    Regardless of input classifies always to the same class.
 */
public class ConstantClassifier<Type> extends Classifier<Type>
{
    private int classifier;

    public ConstantClassifier(int x)
    {
        classifier = x;
    }

    @Override
    public int classify(Type x)
    {
        return classifier;
    }
}

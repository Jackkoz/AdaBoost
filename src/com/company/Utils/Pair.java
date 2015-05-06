package com.company.Utils;/*
 * Designed & Implemented by:
 * Jacek Koziński
 * Date: 2015-04-16
 */

public class Pair<Type>
{
    public Type first;
    public int second;

        public Pair(Type x, int y)
        {
            first = x;
            second = y;
        }

    @Override
    public String toString()
    {
        return "<" +
                first +
                ", " + second +
                '>';
    }
}

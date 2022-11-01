package com.manasseh.ljsa.utils;

import java.util.ArrayList;
import java.util.Calendar;

public class AnneeLists {
    public static ArrayList<String> getYearList(int years) {
        ArrayList<String> yearList = new ArrayList<>(years);
        int startYear = Calendar.getInstance().get(Calendar.YEAR) - 30;
        if (startYear<1990){
            startYear = 1990;
        }
        for (int i = 0; i < years; i++)
            yearList.add(Integer.toString(startYear + i));
        return yearList;
    }
}

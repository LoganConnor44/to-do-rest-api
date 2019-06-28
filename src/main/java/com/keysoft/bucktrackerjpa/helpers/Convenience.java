package com.keysoft.bucktrackerjpa.helpers;

import java.util.List;

public class Convenience {
    public static boolean isListOfNulls(List myList){
        for(Object o: myList)
            if(o != null)
                return false;
        return true;
    }
}
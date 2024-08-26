package com.poly.petfoster.ultils;

import java.util.List;

public class ListUltils {
    public static Boolean includes(List<String> array, String itemCondition) {

        Integer size = array.stream().filter(item -> {
            return itemCondition.equals(item);
        }).toList().size();
        return size > 0;
    }
}

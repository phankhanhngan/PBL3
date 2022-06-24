package com.example.pbl3.DTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListTopProduct {
    public List<TopProduct> topProductList = new ArrayList<>();
    public void add(TopProduct tp)
    {
        for (int i=0; i<topProductList.size(); i++)
            if(topProductList.get(i).getProductName().equals(tp.getProductName()))
            {
                topProductList.get(i).setQuantity(topProductList.get(i).getQuantity() + tp.getQuantity());
                return;
            }
        topProductList.add(tp);
    }
    public ListTopProduct GetTop5Product()
    {
        ListTopProduct top5 = new ListTopProduct();
        Collections.sort(topProductList);
        for(int i=0; i<topProductList.size() && i<5 ; i++)
        {
            topProductList.get(i).setNo(i+1);
            top5.add(topProductList.get(i));
        }
        return top5;
    }
}

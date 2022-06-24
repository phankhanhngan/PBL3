package com.example.pbl3.DTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListTopSeller {
    public List<TopSeller> topSellerList = new ArrayList<>();
    public void add(TopSeller ts)
    {
        for (int i=0; i<topSellerList.size(); i++)
            if(topSellerList.get(i).getSeller_Name().equals(ts.getSeller_Name()))
            {
                topSellerList.get(i).setRevenue(topSellerList.get(i).getRevenue() + ts.getRevenue());
                return;
            }
        topSellerList.add(ts);
    }
    public ListTopSeller GetTop5Product()
    {
        ListTopSeller top5 = new ListTopSeller();
        Collections.sort(topSellerList);
        for(int i=0; i< topSellerList.size() && i<5 ; i++)
        {
            topSellerList.get(i).setNo(i+1);
            top5.add(topSellerList.get(i));
        }
        return top5;
    }
}

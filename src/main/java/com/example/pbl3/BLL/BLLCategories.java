package com.example.pbl3.BLL;

import com.example.pbl3.DAL.DatabaseHelper;
import com.example.pbl3.DTO.Category;

import java.util.ArrayList;
import java.util.List;

public class BLLCategories {
    public static List<Category> getListCategory()
    {
        return DatabaseHelper.GetAllCategory();
    }

    public static boolean AddCategory(Category c)
    {
        return DatabaseHelper.InsertCategory(c);
    }

    public static boolean UpdateCategory(Category c)
    {
        return DatabaseHelper.UpdateCategory(c);
    }

    public static boolean DeleteCategory(int id)
    {
        return DatabaseHelper.DeleteCategory(id);
    }

    public static List<Category> SearchCategory(String txt)
    {
        List<Category> list = new ArrayList<>();
        List<Category> categoryList = DatabaseHelper.GetAllCategory();
        for(int i=0; i<categoryList.size(); i++)
            if(categoryList.get(i).getCate_Name().contains(txt))
                list.add(categoryList.get(i));
        return list;
    }

    public static Category getCategoryByNameCategory(String nameCategory)
    {
        List<Category> categoryList = BLLCategories.getListCategory();
        for(int i=0; i<categoryList.size(); i++)
            if(categoryList.get(i).getCate_Name().equals(nameCategory))
                return categoryList.get(i);
        return null;
    }
}

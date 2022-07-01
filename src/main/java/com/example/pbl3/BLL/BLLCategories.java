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
        for (Category category : categoryList)
            if (category.getCate_Name().contains(txt))
                list.add(category);
        return list;
    }
}
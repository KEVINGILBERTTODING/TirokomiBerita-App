package com.example.tiroberita.ui.fragments.post;

import com.example.tiroberita.model.SavePostModel;

public interface ItemSavePostListener <T>{
    void itemSavePostListener(String type, int position, T model);
}

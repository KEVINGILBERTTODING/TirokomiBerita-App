package com.example.tiroberita.data.repository.post;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tiroberita.model.SavePostModel;
import com.example.tiroberita.util.Constans;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SavePostRepository {


    @Inject
    public SavePostRepository () {

    }

    public LiveData<List<SavePostModel>> savePost(String userId) {
        MutableLiveData<List<SavePostModel>> listMutableLiveData = new MutableLiveData<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child(Constans.FIREBASE_CHILD_SAVE_POST).orderByChild(Constans.USER_ID).equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<SavePostModel> savePostModelList = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    SavePostModel savePostModel = snapshot1.getValue(SavePostModel.class);
                    savePostModelList.add(savePostModel);
                }

                listMutableLiveData.setValue(savePostModelList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (error != null) {
                    Log.e("Firebase gagal", "Error: " + error.getMessage());
                }

            }
        });

        return listMutableLiveData;
    }
}

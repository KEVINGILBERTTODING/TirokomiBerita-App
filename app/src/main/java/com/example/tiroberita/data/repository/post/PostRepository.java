package com.example.tiroberita.data.repository.post;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tiroberita.model.FirebaseResponseModel;
import com.example.tiroberita.model.ResponseModel;
import com.example.tiroberita.model.SaveModel;
import com.example.tiroberita.model.SavePostModel;
import com.example.tiroberita.util.Constans;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PostRepository {



    @Inject
    public PostRepository() {

    }

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public LiveData<FirebaseResponseModel<List<SavePostModel>>> getDatasavePost(String userId) {
        MutableLiveData<FirebaseResponseModel<List<SavePostModel>>> listMutableLiveData = new MutableLiveData<>();

        Query query = databaseReference.child(Constans.FIREBASE_CHILD_SAVE_POST).orderByChild(Constans.USER_ID).equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<SavePostModel> savePostModelList = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    SavePostModel savePostModel = snapshot1.getValue(SavePostModel.class);
                    savePostModelList.add(savePostModel);
                }

                listMutableLiveData.setValue(new FirebaseResponseModel<>(true, Constans.RESPONSE_SUCCESS, savePostModelList));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listMutableLiveData.setValue(new FirebaseResponseModel<>(false, Constans.ERR_MESSAGE, null));

            }
        });

        return listMutableLiveData;
    }


    public LiveData<FirebaseResponseModel> insertSavePost(SaveModel model) {
        MutableLiveData<FirebaseResponseModel> responseModelMutableLiveData = new MutableLiveData<>();
        databaseReference.child(Constans.FIREBASE_CHILD_SAVE_POST).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                responseModelMutableLiveData.setValue(new FirebaseResponseModel(true, "Berhasil menyimpan berita", null));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                responseModelMutableLiveData.setValue(new FirebaseResponseModel(false, Constans.ERR_MESSAGE, null));
            }
        });

        return responseModelMutableLiveData;
    }
}

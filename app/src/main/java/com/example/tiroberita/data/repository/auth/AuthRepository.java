package com.example.tiroberita.data.repository.auth;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tiroberita.model.FirebaseResponseModel;
import com.example.tiroberita.util.constans.Constans;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;

public class AuthRepository {

    @Inject
    public AuthRepository() {

    }

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public LiveData<FirebaseResponseModel> updateUsername(String username, String userId) {
        MutableLiveData<FirebaseResponseModel> responseModelMutableLiveData = new MutableLiveData<>();

        Query query = databaseReference.child(Constans.FIREBASE_CHILD_USER).orderByChild("userId").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot  dataSnapshot : snapshot.getChildren()){
                    dataSnapshot.getRef().child(Constans.USERNAME).setValue(username)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    responseModelMutableLiveData.setValue(new FirebaseResponseModel(true, "Berhasil mengubah username", null));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    responseModelMutableLiveData.setValue(new FirebaseResponseModel(false, Constans.ERR_MESSAGE, null));

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                responseModelMutableLiveData.setValue(new FirebaseResponseModel(false, Constans.ERR_MESSAGE, null));

            }
        });

        return responseModelMutableLiveData;

    }

    public LiveData<FirebaseResponseModel> updateMediaFavorite(String userId, String mediaName) {
        MutableLiveData<FirebaseResponseModel> responseModelMutableLiveData = new MutableLiveData<>();
        Query query = databaseReference.child(Constans.FIREBASE_CHILD_USER).orderByChild("userId").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        dataSnapshot.getRef().child("favorite").setValue(mediaName)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        responseModelMutableLiveData.setValue(new FirebaseResponseModel(true, "Berhasil mengubah media favorit", null));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        responseModelMutableLiveData.setValue(new FirebaseResponseModel(false, Constans.ERR_MESSAGE, null));
                                    }
                                });
                    }
                }else {
                    responseModelMutableLiveData.setValue(new FirebaseResponseModel(false, Constans.ERR_MESSAGE, null));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                responseModelMutableLiveData.setValue(new FirebaseResponseModel(false, Constans.ERR_MESSAGE, null));

            }
        });

        return  responseModelMutableLiveData;
    }



}

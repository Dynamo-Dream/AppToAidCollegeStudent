package com.example.shayadfinaldatabase.FirebaseRealtimeDB.repository

import com.example.apptoaidcollegestudent.model.MedicalData
import com.example.apptoaidcollegestudent.model.Message
import com.example.apptoaidcollegestudent.repository.RealtimeModelResponse
import com.example.apptoaidcollegestudent.repository.RealtimeModelResponse2
import com.example.shayadfinaldatabase.utils.ResultState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RealtimeRepositoryImplementation @Inject constructor(
    private val db: DatabaseReference
) : RealtimeRepository {
    override fun insertMessage(item: Message): Flow<ResultState<String>> =
    callbackFlow {
        trySend(ResultState.Loading)
        db.child("message").push().setValue(
            item
        ).addOnCompleteListener {
            if (it.isSuccessful)
                trySend(ResultState.Success("Data Inserted Successfully"))
        }.addOnFailureListener {
            trySend(ResultState.Failure(it))
        }
        awaitClose {
            close()
        }
    }
    override fun insertMedicalData(item: MedicalData): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            db.child("medical").push().setValue(
                item
            ).addOnCompleteListener {
                if (it.isSuccessful)
                    trySend(ResultState.Success("Data Inserted Successfully"))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
            awaitClose {
                close()
            }
        }

    override fun getUtilData(): Flow<ResultState<List<RealtimeModelResponse2>>> = callbackFlow {
        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.child("medical").children.map {
                    RealtimeModelResponse2(
                        it.getValue(RealtimeModelResponse2.MedicalData::class.java),
                        key = it.key
                    )
                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Failure(error.toException()))
            }

        }
        db.addValueEventListener(valueEvent)

        awaitClose {
            db.removeEventListener(valueEvent)
            close()
        }


    }

    override fun getItems(): Flow<ResultState<List<RealtimeModelResponse>>> = callbackFlow {
        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.child("user").children.map {
                    RealtimeModelResponse(
                        it.getValue(RealtimeModelResponse.RealtimeItems::class.java),
                        key = it.key
                    )
                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Failure(error.toException()))
            }

        }
        db.addValueEventListener(valueEvent)

        awaitClose {
            db.removeEventListener(valueEvent)
            close()
        }


    }
    override fun delete(key: String): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        db.child(key).removeValue()
            .addOnCompleteListener {
                trySend(ResultState.Success("Data Item Deleted"))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }

        awaitClose {
            close()
        }
    }


}
package com.essensift.mandirihack.database

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class DBEngine {

    private val settings = FirebaseFirestoreSettings.Builder()
        .setPersistenceEnabled(true)
        .setSslEnabled(true)
        .build()
    private val dbRoot = FirebaseFirestore.getInstance()

    fun getFirestore(): FirebaseFirestore {
        dbRoot.firestoreSettings = settings
        return dbRoot
    }
}
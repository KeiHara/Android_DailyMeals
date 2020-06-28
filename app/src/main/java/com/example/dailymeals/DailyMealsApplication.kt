package com.example.dailymeals

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class DailyMealsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val builder = RealmConfiguration.Builder()
        val config = builder.deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(config)
    }
}
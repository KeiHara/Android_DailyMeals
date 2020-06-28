package com.example.dailymeals

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Meals: RealmObject() {
    @PrimaryKey
    var date: String = "" // 主キー
    var breakfat: String = "" // 朝食
    var lunch: String = "" // 昼食
    var dinner: String = "" // 夕食
}
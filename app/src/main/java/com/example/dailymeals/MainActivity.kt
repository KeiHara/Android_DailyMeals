package com.example.dailymeals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    // Realmクラスのプロパティを準備
    // onCreate内で初期化をするためにlateinit修飾子をつけた
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //日付のフォーマット
        val format = SimpleDateFormat("yyyy/MM/dd")
        val today = calendarView.date
        // format.format(today)

        // Realクラスのインスタンスを取得
        realm = Realm.getDefaultInstance()
        val meals = realm.where<Meals>().findAll()

        // 日付選択イベントの受け取り
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val date = "${year}/${month+1}/${dayOfMonth}"
            startActivity<EditActivity>("meals_id" to date) // 選択した日付のIDを渡す
        }
    }

    // アクティビティの終了処理
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

}
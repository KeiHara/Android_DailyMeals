package com.example.dailymeals

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.text.SimpleDateFormat

class EditActivity : AppCompatActivity() {

    // Realmクラスのプロパティを準備
    // onCreate内で初期化をするためにlateinit修飾子をつけた
    private lateinit var realm: Realm

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        //日付のフォーマット
        val format = SimpleDateFormat("yyyy/MM/dd")

        // MainActivityでリストのセルを選択してEditActivityに遷移した時の処理
        val date = intent.getStringExtra("meals_id")

        // Realクラスのインスタンスを取得
        realm = Realm.getDefaultInstance()
        // 登録済みでデータか確認するフラッグ
        var flag = true
        val meal = realm.where<Meals>().equalTo("date", date).findFirst()
        if (meal != null) {
            flag = true
        } else {
            flag = false
        }

        // 入力欄の準備
        val breakfastText = findViewById<EditText>(R.id.breakfastText)
        val lunchText = findViewById<EditText>(R.id.lunchText)
        val dinnerText = findViewById<EditText>(R.id.dinnerText)


        // 登録済みのデータである場合、入力済みの朝食、昼食、夕食の内容を代入
        if (flag) {
            breakfastText.setText(meal?.breakfat ?: "")
            lunchText.setText(meal?.lunch ?: "")
            dinnerText.setText(meal?.dinner ?: "")
        }


        // 日付を入力する
        dateView.text = "${date}の食事"

        save.setOnClickListener {
                when (flag) {
                    false -> {
                        realm.executeTransaction {
                            val meal = realm.createObject<Meals>(date)
                            meal.breakfat = breakfastText.text.toString()
                            meal.lunch = lunchText.text.toString()
                            meal.dinner = dinnerText.text.toString()
                        }
                        alert("保存しました") {
                            yesButton { finish() }
                        }.show()
                    }
                    else -> {
                        realm.executeTransaction {
                            val meal = realm.where<Meals>().equalTo("date", date).findFirst()
                            meal?.breakfat = breakfastText.text.toString()
                            meal?.lunch = lunchText.text.toString()
                            meal?.dinner = dinnerText.text.toString()
                        }
                        alert("保存しました") {
                            yesButton { finish() }
                        }.show()
                    }
                }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}

package com.bennyhuo.tieguanyinsimple.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goToRepositoryActivity.setOnClickListener {
            //startRepositoryActivity("Kotlin", "JetBrains", 1329125398000L, "https://github.com/jetbrains/kotlin")
        }
        goToUserActivity.setOnClickListener {
            UserActivityBuilder.start(this, 30, "bennyhuo", "Kotliner", "打杂的", "北京")
        }
    }
}

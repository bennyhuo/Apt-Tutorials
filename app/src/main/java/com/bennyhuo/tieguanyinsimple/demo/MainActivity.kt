package com.bennyhuo.tieguanyinsimple.demo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goToRepositoryActivity.setOnClickListener {
            //startRepositoryActivity("Kotlin", "JetBrains", 1329125398000L, "https://github.com/jetbrains/kotlin")
            startActivity(Intent(this, RepositoryActivity::class.java)
                    .putExtra(RepositoryActivityBuilder.REQUIRED_NAME, "Kotlin")
                    .putExtra(RepositoryActivityBuilder.REQUIRED_OWNER, "JetBrains")
                    .putExtra(RepositoryActivityBuilder.OPTIONAL_CREATE_AT, 1329125398000L)
                    .putExtra(RepositoryActivityBuilder.OPTIONAL_URL, "https://github.com/jetbrains/kotlin")
            )
        }
        goToUserActivity.setOnClickListener {
            //UserActivityBuilder.start(this, 30, "bennyhuo", "Kotliner", "打杂的")
        }
    }
}

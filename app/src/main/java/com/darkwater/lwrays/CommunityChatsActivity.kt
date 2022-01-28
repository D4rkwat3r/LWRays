package com.darkwater.lwrays

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darkwater.lwrays.adapters.CommunityChatsAdapter
import com.darkwater.lwrays.network.APIClient

class CommunityChatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras!!
        val circleName = extras["circleName"] as String
        title = "Чаты сообщества $circleName"
        val circleId = extras["circleId"] as Long
        setContentView(R.layout.activity_community_chats)
        val client = APIClient(this)
        val recyclerView = findViewById<RecyclerView>(R.id.communityChatsRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 4)
        client.loadChatList(circleId) { response ->
            if (response.list.isEmpty()) {
                Toast.makeText(this, "Чатов нет", Toast.LENGTH_SHORT).show()
            }
            val adapter = CommunityChatsAdapter(response.list)
            adapter.addClickListener { position ->
                intent.putExtra("threadId", adapter.chats[position].threadId)
                setResult(RESULT_OK, intent)
                finish()
                overridePendingTransition(R.anim.enter, R.anim.exit)
            }
            recyclerView.adapter = adapter
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.enter, R.anim.exit)
    }
}
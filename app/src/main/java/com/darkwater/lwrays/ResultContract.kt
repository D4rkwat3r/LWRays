package com.darkwater.lwrays

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.darkwater.lwrays.models.Circle

class ResultContract : ActivityResultContract<Circle, Long?>() {
    override fun createIntent(context: Context, input: Circle): Intent {
        return Intent(context, CommunityChatsActivity::class.java)
            .putExtra("circleName", input.name)
            .putExtra("circleId", input.circleId)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Long? {
        if (resultCode != Activity.RESULT_OK) return null
        return intent!!.getLongExtra("threadId", 0)
    }

}
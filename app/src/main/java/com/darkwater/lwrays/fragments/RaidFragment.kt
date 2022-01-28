package com.darkwater.lwrays.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.darkwater.lwrays.MainScreenActivity
import com.darkwater.lwrays.R
import com.darkwater.lwrays.WorkerThread

class RaidFragment : Fragment() {

    private var threadId: Long? = null
    private var isStarted: Boolean = false
    private val workers: MutableList<WorkerThread> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_raid, container, false)
        return setup(view)
    }
    private fun setup(view: View): View {
        val client = (requireActivity() as MainScreenActivity).client
        val idHint = view.findViewById<TextView>(R.id.chatIdHint)
        val messageInput = view.findViewById<EditText>(R.id.messageEditText)
        val startButton = view.findViewById<Button>(R.id.raidStartButton)
        val messageCounter = view.findViewById<TextView>(R.id.totalMessageSent)
        requireActivity()
            .supportFragmentManager
            .setFragmentResultListener("thread", viewLifecycleOwner) { key, data ->
                threadId = data.getLong("id")
                idHint.text = "ID выбранного чата: $threadId"
            }
        startButton.setOnClickListener {
            if (isStarted) {
                startButton.text = "Запустить"
                workers.forEach { it.kill() }
                workers.clear()
                isStarted = false
                return@setOnClickListener
            }
            if (threadId == null) {
                Toast.makeText(requireActivity(), "Вы не выбрали чат", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (messageInput.text.isEmpty()) {
                Toast.makeText(requireContext(), "Введите сообщение!", Toast.LENGTH_SHORT).show()
            }
            client.joinChat(threadId!!) {
                repeat(90) {
                    val worker = WorkerThread(
                        client.createWebSocket { if (it.contains("serverAck") && !it.contains("apiMsg")) incrementCounter(messageCounter) },
                        threadId!!,
                        messageInput.text.toString(),
                        client.mapper
                    )
                    worker.start()
                    workers += worker
                }
            }
            isStarted = true
            startButton.text = "Остановить"
        }
        return view
    }

    private fun incrementCounter(counter: TextView) {
        val oldValue = counter.text.split(": ")[1].toInt()
        counter.post {
            counter.text = "Отправлено сообщений: ${oldValue + 1}"
        }
    }

}
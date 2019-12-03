package com.darthvader11.bandlink.adaptors

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darthvader11.bandlink.data.model.DummmySelfProfile
import com.darthvader11.bandlink.data.model.UserMessage
import kotlinx.android.synthetic.main.item_message_received.view.*
import kotlinx.android.synthetic.main.item_message_received.view.text_message_body
import kotlinx.android.synthetic.main.item_message_received.view.text_message_time
import kotlinx.android.synthetic.main.item_message_sent.view.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.darthvader11.bandlink.R
import com.darthvader11.bandlink.data.model.DummyChatProvider
import com.darthvader11.bandlink.data.model.User
import java.util.*
import kotlin.collections.ArrayList


class MessageListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVED = 2

    var items: List<UserMessage> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_sent, parent, false)
            return SentMessageHolder(view)
        }
        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message_received, parent, false)
        return ReceivedMessageHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val message = items[position]

        when (holder.itemViewType) {

            VIEW_TYPE_MESSAGE_RECEIVED -> {
                (holder as ReceivedMessageHolder).bind(message)
            }

            VIEW_TYPE_MESSAGE_SENT -> {
                (holder as SentMessageHolder).bind(message)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        val userMessage = items[position]
        //TODO: UUID will be changed according to the BackEnd implementation
        if (userMessage.sender.id == (UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c"))) {
            return VIEW_TYPE_MESSAGE_SENT
        }
        return VIEW_TYPE_MESSAGE_RECEIVED

    }

    fun submitList(messageList: List<UserMessage>) {
        items = messageList
    }

    private inner class SentMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val messageText = itemView.received_text_message_body
        val timeText = itemView.received_text_message_time

        fun bind(message: UserMessage) {

            messageText.text = message.message
            timeText.text = message.createdAt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
        }
    }

    private inner class ReceivedMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val messageText = itemView.text_message_body
        val timeText = itemView.text_message_time
        val nameText = itemView.text_message_name
        val profileImage = itemView.image_message_profile


        fun bind(message: UserMessage) {

            messageText.text = message.message
            timeText.text = message.createdAt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
            nameText.text = message.sender.name
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(message.sender.profilePic)
                .into(profileImage)

        }
    }
}
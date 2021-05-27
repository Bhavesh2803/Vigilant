package com.vigilant.CreateReport

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mssinfotech.mycity.Utility.Constants
import com.vigilant.R
import java.io.File
import java.util.*


class PhotoAdapter(private val context: Context, private var IncidentImageArray: ArrayList<String>) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.photo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imgFile = File(IncidentImageArray.get(position))

        if (imgFile.exists()) {
            val myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath())
            holder.iv_image.setImageBitmap(myBitmap)
        }
        holder.iv_remove.setOnClickListener {
            IncidentImageArray.removeAt(position)
            Constants.IncidentImageArray = IncidentImageArray
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return IncidentImageArray.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var iv_image: ImageView
        var iv_remove: ImageView

        init {

            iv_image = itemView.findViewById(R.id.iv_image)
            iv_remove = itemView.findViewById(R.id.iv_remove)

        }
    }

}
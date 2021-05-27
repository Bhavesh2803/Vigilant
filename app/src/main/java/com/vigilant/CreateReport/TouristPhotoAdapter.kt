package com.vigilant.CreateReport

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mssinfotech.mycity.Utility.Constants
import com.squareup.picasso.Picasso
import com.vigilant.R
import java.io.File
import java.util.ArrayList

class TouristPhotoAdapter(private val context: Context,private var TouristImageArray: ArrayList<String>) : RecyclerView.Adapter<TouristPhotoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.photo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Picasso.with(context).load(TouristImageArray.get(position)).into(holder.iv_image)
        val imgFile = File(TouristImageArray.get(position))

        if (imgFile.exists()) {
            val myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath())
            holder.iv_image.setImageBitmap(myBitmap)
        }
        holder.iv_remove.setOnClickListener {
            TouristImageArray.removeAt(position)
            Constants.TouristImageArray = TouristImageArray
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return TouristImageArray.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var iv_image: ImageView
        var iv_remove: ImageView

        // antivirusPlanClickListener.onPlanBuyNowClicked(antivirusPlanArrayLists.get(getAdapterPosition()).getMembershipId(),antivirusPlanArrayLists.get(getAdapterPosition()).getSellingPrice());
        init {

            iv_image = itemView.findViewById(R.id.iv_image)
            iv_remove = itemView.findViewById(R.id.iv_remove)

        }
    }


}
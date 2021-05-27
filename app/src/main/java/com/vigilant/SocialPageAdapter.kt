package com.vigilant

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.vigilant.R

class SocialPageAdapter(private val context: Context) : RecyclerView.Adapter<SocialPageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.social_page_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //   holder.txtAntiPlanTitle.setText(antivirusPlanArrayLists.get(position).getTitle());

    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

      //  var iv_image: ImageView

        init {

           // iv_image = itemView.findViewById(R.id.iv_image)

        }
    }

}
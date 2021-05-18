package com.vigilant

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vigilant.Model.GetAllReportesDTO
import retrofit2.Response

class HomeListAdapter(private val context: Context, private val getAllReportesDTO: GetAllReportesDTO) : RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.home_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //   holder.txtAntiPlanTitle.setText(antivirusPlanArrayLists.get(position).getTitle());
        holder.tv_name?.text = "Report ID: "+ getAllReportesDTO.data.get(position).id
    }

    override fun getItemCount(): Int {
        return getAllReportesDTO.data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_name: TextView? = null

        // antivirusPlanClickListener.onPlanBuyNowClicked(antivirusPlanArrayLists.get(getAdapterPosition()).getMembershipId(),antivirusPlanArrayLists.get(getAdapterPosition()).getSellingPrice());
        init {
            itemView.findViewById<TextView>(R.id.tv_name).also { tv_name = it }

        }
    }
}
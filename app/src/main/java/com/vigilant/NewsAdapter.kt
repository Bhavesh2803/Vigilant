package com.vigilant

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vigilant.Model.GetNewsListDTO.NewsListDTO

class NewsAdapter(private val context: Context,private val newsListDTO:NewsListDTO) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    lateinit var rl_main_view: RelativeLayout

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        val view: View = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false)
        rl_main_view = view.findViewById(R.id.rl_main_view)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     holder.tv_title!!.text = newsListDTO.data.get(position).title
     holder.tv_description!!.text = newsListDTO.data.get(position).description
     holder.tv_created_at!!.text = newsListDTO.data.get(position).createdAt
        rl_main_view.setOnClickListener {


            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("title", "News Details")
            intent.putExtra("url", "https://www.ndtv.com/india-news/coronavirus-record-4-187-covid-deaths-in-india-in-24-hours-4-01-lakh-new-cases-2437538")
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return newsListDTO.data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
var tv_title:TextView? = null
var tv_description:TextView? = null
var tv_created_at:TextView? = null


        // antivirusPlanClickListener.onPlanBuyNowClicked(antivirusPlanArrayLists.get(getAdapterPosition()).getMembershipId(),antivirusPlanArrayLists.get(getAdapterPosition()).getSellingPrice());
        init {
itemView.findViewById<TextView>(R.id.tv_title).also { tv_title = it }
itemView.findViewById<TextView>(R.id.tv_description).also { tv_description = it }
itemView.findViewById<TextView>(R.id.tv_created_at).also { tv_created_at = it }


        }
    }


}
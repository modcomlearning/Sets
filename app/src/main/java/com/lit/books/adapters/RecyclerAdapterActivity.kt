package com.lit.books.adapters
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textview.MaterialTextView
import com.lit.books.R
import com.lit.books.SingleActivity
import com.lit.books.VideoPlayering
import com.lit.books.models.BookActivity
import com.lit.books.models.Books

class RecyclerAdapterActivity(var context: Context)://When you want to toast smthg without intent or activities
    RecyclerView.Adapter<RecyclerAdapterActivity.ViewHolder>(){
    //View holder holds the views in single item.xml

    var productList : List<BookActivity> = listOf() // empty product list
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
    //Note below code returns above class and pass the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_item_activity, parent, false)
        return ViewHolder(view)
    }
    //so far item view is same as single item
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ivActivity = holder.itemView.findViewById(R.id.ivActivity) as ImageView
        val tvActivityName = holder.itemView.findViewById(R.id.ActivityName) as MaterialTextView
        //bind
        val item = productList[position]
        tvActivityName.text = item.activity_name
        val typeface = ResourcesCompat.getFont(context, R.font.montserrat)
        tvActivityName.typeface = typeface
        Glide.with(context).load("https://coding.co.ke/piks/"+item.image_url)
            .apply(RequestOptions().centerCrop().placeholder(R.drawable.placeholder))
            .into(ivActivity)

       // Toast.makeText(context, ""+item.image_url, Toast.LENGTH_SHORT).show()
        holder.itemView.setOnClickListener {
            val prefs: SharedPreferences = context.getSharedPreferences(
                "store",
                Context.MODE_PRIVATE
            )

            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString("video_link", item.video_link)
            editor.putString("activity_name", item.activity_name)
            editor.apply()

            val i = Intent(context, VideoPlayering::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
    }
    override fun getItemCount(): Int {
        return productList.size
    }

    //we will call this function on Loopj response
    fun setProductListItems(productList: List<BookActivity>){
        this.productList = productList
        notifyDataSetChanged()
    }
}//end class

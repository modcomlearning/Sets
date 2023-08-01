package com.lit.books.adapters
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textview.MaterialTextView
import com.lit.books.MainActivity
import com.lit.books.PaymentActivity
import com.lit.books.R
import com.lit.books.SingleActivity
import com.lit.books.models.Books
import kotlinx.android.synthetic.main.activity_payment.*

class RecyclerAdapter(var context: Context)://When you want to toast smthg without intent or activities
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){
    //View holder holds the views in single item.xml

    var productList : List<Books> = listOf() // empty product list
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
    //Note below code returns above class and pass the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_item, parent, false)
        return ViewHolder(view)
    }
    //so far item view is same as single item
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ivBooks = holder.itemView.findViewById(R.id.ivBooks) as ImageView
        val tvBooks = holder.itemView.findViewById(R.id.TextBook) as MaterialTextView
        //bind
        val item = productList[position]
        tvBooks.text = item.book_name
        val typeface = ResourcesCompat.getFont(context, R.font.montserrat)
        tvBooks.typeface = typeface
        Glide.with(context).load("https://hudumazetu.com/piks/"+item.book_image)
            .apply(RequestOptions().centerCrop().placeholder(R.drawable.placeholder))
            .into(ivBooks)

        holder.itemView.setOnClickListener {
            val prefs1 = context.getSharedPreferences("storage", Context.MODE_PRIVATE)
            val emailP = prefs1.getString("email", "")
            if(emailP!!.isNotEmpty()){
                val prefs: SharedPreferences = context.getSharedPreferences(
                    "store",
                    Context.MODE_PRIVATE
                )
                val editor: SharedPreferences.Editor = prefs.edit()
                editor.putString("product_name", item.book_name)
                editor.putString("product_desc", item.book_desc)
                editor.putString("product_cost", item.book_publisher)
                editor.putString("image_url", item.book_image)
                editor.putString("book_id", item.book_id)
                editor.apply()

                if(item.book_id == "5"){
                    val i = Intent(context, PaymentActivity::class.java)
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(i)
                }
                else {
                    Toast.makeText(context, "Coming Soon!, Please Click on Father of Nations", Toast.LENGTH_SHORT).show()
                }

            }
            else {
                Toast.makeText(context, "You are Not Logged In, Please Log In First",
                    Toast.LENGTH_LONG).show()
                val i = Intent(context, MainActivity::class.java)
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(i)
            }

        }

    }
    override fun getItemCount(): Int {
        return productList.size
    }

    //we will call this function on Loopj response
    fun setProductListItems(productList: List<Books>){
        this.productList = productList
        notifyDataSetChanged()
    }
}//end class

package pl.edu.pwr.wiz.psmanko

import android.app.ActivityOptions
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.onClick

class RecyclerAdapter(val activity: AppCompatActivity? = null, val items: List<String> = listOf(), val width: Int = 0, val height: Int = 0) : RecyclerView.Adapter<RecyclerAdapter.ImageHolder>() {
    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.imageView.apply {
            transitionName = "image" + position
            onClick {
                activity?.let {
                    val transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(activity, this, transitionName)
                    activity.startActivity(activity.intentFor<DetailActivity>("position" to position), transitionActivityOptions.toBundle())
                }
            }
        }.loadUrl(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        return ImageHolder(ImageView(parent.context).apply {
            isClickable = true
            scaleType = ImageView.ScaleType.CENTER_CROP
            layoutParams = ViewGroup.LayoutParams(this@RecyclerAdapter.width, this@RecyclerAdapter.height)
            imageResource = R.drawable.ic_error_outline_black_24dp
            visibility = View.VISIBLE
        })
    }

    inner class ImageHolder(var imageView: ImageView) : RecyclerView.ViewHolder(imageView)

}
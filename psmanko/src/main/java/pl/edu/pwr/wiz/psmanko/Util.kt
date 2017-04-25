package pl.edu.pwr.wiz.psmanko

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator


fun ImageView.loadUrl(url: String, callback: Callback? = null, function: RequestCreator.() -> Unit = {}) {
    Picasso.with(context.applicationContext)
            .load(url)
            .apply { function() }
            .into(this, callback)
}

fun <T : View> T.onPreDraw(func: T.() -> Unit) = viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
    override fun onGlobalLayout() {
        viewTreeObserver.removeOnGlobalLayoutListener(this)
        func()
    }
})

fun View.resumeSharedElementTransition(activity: AppCompatActivity) = onPreDraw { activity.supportStartPostponedEnterTransition() }
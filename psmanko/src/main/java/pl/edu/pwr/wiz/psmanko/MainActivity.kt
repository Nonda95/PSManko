package pl.edu.pwr.wiz.psmanko

import android.app.SharedElementCallback
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.transition.Explode
import android.view.View
import org.jetbrains.anko.ctx
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.setContentView

class MainActivity : AppCompatActivity() {
    companion object {
        val items = listOf(
                "https://unsplash.it/1280/720/?image=10",
                "https://unsplash.it/1280/720/?image=11",
                "https://unsplash.it/1280/720/?image=12",
                "https://unsplash.it/1280/720/?image=13",
                "https://unsplash.it/1280/720/?image=14",
                "https://unsplash.it/1280/720/?image=15",
                "https://unsplash.it/1280/720/?image=16",
                "https://unsplash.it/1280/720/?image=17",
                "https://unsplash.it/1280/720/?image=18",
                "https://unsplash.it/1280/720/?image=19",
                "https://unsplash.it/1280/720/?image=20",
                "https://unsplash.it/1280/720/?image=21")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val explode = Explode()
        window.enterTransition = explode
        window.exitTransition = explode
        window.enterTransition?.excludeTarget(R.id.action_bar_container, true)
        window.enterTransition?.excludeTarget(android.R.id.statusBarBackground, true)
        window.enterTransition?.excludeTarget(android.R.id.navigationBarBackground, true)
        window.exitTransition?.excludeTarget(R.id.action_bar_container, true)
        window.exitTransition?.excludeTarget(android.R.id.statusBarBackground, true)
        window.exitTransition?.excludeTarget(android.R.id.navigationBarBackground, true)
        val ui = ActivityUI {
            recyclerView {
                layoutManager = GridLayoutManager(ctx, 2)
            }.lparams(height = matchParent, width = matchParent)
                    .onPreDraw {
                        adapter = RecyclerAdapter(this@MainActivity, items, measuredWidth / 2, measuredHeight / 4)
                    }
        }
        ui.setContentView(this)
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
        val startPos = data?.extras?.getInt(EXTRA_STARTING_POSITION)
        val currentPos = data?.extras?.getInt(EXTRA_CURRENT_POSITION)
        setExitSharedElementCallback(object : SharedElementCallback() {

            override fun onMapSharedElements(names: MutableList<String>?, sharedElements: MutableMap<String, View>?) {
                if (startPos != currentPos) {
                    sharedElements?.clear()
                }
                val callback: SharedElementCallback? = null
                setExitSharedElementCallback(callback)
            }
        })
    }
}


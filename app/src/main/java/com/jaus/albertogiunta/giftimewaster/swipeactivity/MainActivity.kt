package com.jaus.albertogiunta.giftimewaster.swipeactivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jaus.albertogiunta.giftimewaster.R
import com.jaus.albertogiunta.giftimewaster.utils.Sources
import kotlinx.android.synthetic.main.activity_main.*
import link.fls.swipestack.SwipeStack

class MainActivity : AppCompatActivity(), SwipeStack.SwipeStackListener, View.OnClickListener {

    lateinit var adapter: SwipeStackAdapter
    lateinit var l: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Sources.initialize()
        l = mutableListOf()
        l.add("")
        l.add("")
        adapter = SwipeStackAdapter(this@MainActivity, l)
        swipeStack.adapter = adapter
        swipeStack.setListener(this)
    }

    override fun onViewSwipedToLeft(position: Int) {
        l.add("")
        l.add("")
        adapter.notifyDataSetChanged()
    }

    override fun onViewSwipedToRight(position: Int) {
        l.add("")
        l.add("")
        adapter.notifyDataSetChanged()
    }

    override fun onClick(v: View) {}

    override fun onStackEmpty() {}
}

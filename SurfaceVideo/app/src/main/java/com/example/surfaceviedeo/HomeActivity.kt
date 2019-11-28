package com.example.surfaceviedeo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exemplo.mediaplayer.DataSource
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), DataSourceDelegate {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recycler_view_data_source.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity, RecyclerView.VERTICAL, false)
            adapter = DataSourceAdapter(this@HomeActivity)
        }

        btn_play_video.setOnClickListener {
            if (url_video.text.toString().trim().isNotEmpty()) {
                dataSource(
                    dataSource = DataSource(
                        sources = url_video.text.toString().trim(),
                        thumb = "",
                        title = ""
                    )
                )
            }
        }
    }

    override fun dataSource(dataSource: DataSource) {
        val intent = Intent(this, MediaPlayerActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(DATA_SOURCE_KEY, dataSource)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}

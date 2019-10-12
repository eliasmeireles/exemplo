package com.exemplo.mediaplayer

import androidx.media2.session.MediaSession

interface DataSourceDelegate {
    fun dataSource(dataSource: DataSource)
}
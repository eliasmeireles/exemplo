package com.exemplo.mediaplayer

fun dataSourceList() : List<DataSource> {
    val dataSources = ArrayList<DataSource>()
    dataSources.add(
        DataSource(
            thumb = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg",
            title = "Big Buck Bunny",
            sources = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    ))
    dataSources.add(
        DataSource(
            thumb = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ElephantsDream.jpg",
            title = "Elephant Dream",
            sources = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
        ))
    dataSources.add(
        DataSource(
            thumb = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerBlazes.jpg",
            title = "For Bigger Blazes",
            sources = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
        ))
    dataSources.add(
        DataSource(
            thumb = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerEscapes.jpg",
            title = "For Bigger Escape",
            sources = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4"
        ))
    dataSources.add(
        DataSource(
            thumb = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerFun.jpg",
            title = "For Bigger Fun",
            sources = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4"
        ))
    dataSources.add(
        DataSource(
            thumb = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerJoyrides.jpg",
            title = "For Bigger Joyrides",
            sources = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4"
        ))

    dataSources.add(
        DataSource(
            thumb = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/Sintel.jpg",
            title = "Sintel",
            sources = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4"
        ))
    return dataSources
}
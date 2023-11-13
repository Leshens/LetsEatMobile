package com.leshen.letseatmobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class Home : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


//@Composable
//fun RestaurantImageCell(restaurant: Restaurant) {
//    KamelImage(
//        asyncPainterResource("https://sebi.io/demo-image-api/${image.path}"),
//        "${image.category} by ${image.author}",
//        contentScale = ContentScale.Crop,
//        modifier = Modifier.fillMaxWidth().aspectRatio(1.0f)
//    )
//}
}
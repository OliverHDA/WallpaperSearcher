package ru.oliverhd.wallpapersearcher.ui.image

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import coil.api.load
import kotlinx.android.synthetic.main.fragment_image.*
import ru.oliverhd.wallpapersearcher.R
import ru.oliverhd.wallpapersearcher.utils.URL_EXTRA

class ImageFragment : Fragment() {

    private lateinit var imageURL: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageURL = it.getString(URL_EXTRA).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wallpaperImage.load(imageURL) {
            lifecycle(this@ImageFragment)
            error(R.drawable.ic_load_error_vector)
            placeholder(R.drawable.ic_no_photo_vector)
        }
        setWallpaperButton.setOnClickListener {
            setWallpaper()
        }
    }

    private fun setWallpaper() {
        try {
            val bmpImg: Bitmap = wallpaperImage.drawable.toBitmap()
            val wallpaperManager = WallpaperManager.getInstance(context)
            wallpaperManager.setBitmap(bmpImg)
            Toast.makeText(context, "Wallpaper set successfully!", Toast.LENGTH_SHORT)
                .show()
        } catch (e: Exception) {
            Toast.makeText(context, "WallPaper set Failed!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        fun newInstance(url: String) =
            ImageFragment().apply {
                arguments = Bundle().apply {
                    putString(URL_EXTRA, url)
                }
            }
    }
}
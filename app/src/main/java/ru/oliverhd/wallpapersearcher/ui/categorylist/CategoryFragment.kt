package ru.oliverhd.wallpapersearcher.ui.categorylist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.category_fragment.*
import ru.oliverhd.wallpapersearcher.*
import ru.oliverhd.wallpapersearcher.model.ResponseImage
import ru.oliverhd.wallpapersearcher.ui.image.ImageFragment
import ru.oliverhd.wallpapersearcher.utils.CATEGORY_EXTRA
import ru.oliverhd.wallpapersearcher.viewmodel.CategoryViewModel

class CategoryFragment : Fragment() {

    private lateinit var category: String
    private lateinit var viewModel: CategoryViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryRecyclerAdapter
    private var categoryData: List<ResponseImage> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString(CATEGORY_EXTRA).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.category_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        viewModel.getPictureOfTheDayByDate(category)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        tryAgainTextView.setOnClickListener {
            mainFragmentConnectionErrorLayout.visibility = View.GONE
            viewModel.getPictureOfTheDayByDate(category)
        }
    }

    private fun renderData(data: AppData) {
        when (data) {
            is AppData.Success -> {
                mainFragmentLoadingLayout.visibility = View.GONE
                category_fragment.visibility = View.VISIBLE
                categoryHeadingTextView.text = category
                categoryData = data.categoryResponseData.images
                initRecycler()
            }
            is AppData.Loading -> {
                mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppData.Error -> {
                category_fragment.visibility = View.VISIBLE
                mainFragmentLoadingLayout.visibility = View.GONE
                mainFragmentConnectionErrorLayout.visibility = View.VISIBLE
                errorTextView.text = data.error.message
            }
        }
    }

    private fun initRecycler() {
        categoryAdapter = CategoryRecyclerAdapter(object : OnItemViewClickListener {
            override fun onItemViewClick(value: String) {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, ImageFragment.newInstance(value))
                    ?.addToBackStack("")
                    ?.commit()
            }
        })
        recyclerView = categoryRecycler
        recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = categoryAdapter
        }
        categoryAdapter.setData(categoryData)
    }

    companion object {
        fun newInstance(category: String) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_EXTRA, category)
                }
            }
    }
}
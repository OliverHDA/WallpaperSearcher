package ru.oliverhd.wallpapersearcher.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.oliverhd.wallpapersearcher.model.Category
import ru.oliverhd.wallpapersearcher.R
import ru.oliverhd.wallpapersearcher.ui.categorylist.CategoryFragment
import ru.oliverhd.wallpapersearcher.ui.categorylist.OnItemViewClickListener

class MainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mainAdapter: MainRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler(view)
    }

    private fun initRecycler(view: View) {
        mainAdapter = MainRecyclerAdapter(object : OnItemViewClickListener {
            override fun onItemViewClick(value: String) {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, CategoryFragment.newInstance(value))
                    ?.addToBackStack("")
                    ?.commit()
            }
        })
        recyclerView = view.findViewById(R.id.main_recycler)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = mainAdapter
        }
        mainAdapter.setData(Category.getCategoryList())
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
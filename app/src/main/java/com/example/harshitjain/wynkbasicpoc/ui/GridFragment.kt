package com.example.harshitjain.wynkbasicpoc.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.harshitjain.wynkbasicpoc.R
import com.example.harshitjain.wynkbasicpoc.WynkApp
import com.example.harshitjain.wynkbasicpoc.db.Item
import com.example.harshitjain.wynkbasicpoc.viewModel.HomeViewModel
import com.example.harshitjain.wynkbasicpoc.viewModel.HomeViewModelFactory
import kotlinx.android.synthetic.main.fragment_grid.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [GridFragment.OnListFragmentInteractionListener] interface.
 */
class GridFragment : Fragment() {

    private val TOP_PLAYLIST_ID = "srch_bsb_1490263494633,srch_bsb_1402666444551"

    // TODO: Customize parameters
    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    private lateinit var adapter: GridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_grid, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                addItemDecoration(SpacingDecoration())
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = GridAdapter(listener)
        recyclerView.adapter = adapter

        val viewModel = ViewModelProviders.of(this, HomeViewModelFactory(WynkApp.instance.itemRepository)).get(HomeViewModel::class.java)

        viewModel.getItem(TOP_PLAYLIST_ID, "package","PLAYLIST")?.observe(this, Observer {
            //            var items = mutableListOf<Item>()

//            it?.data?.let { items.add(it) }
            adapter.setData(it?.data)
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: Item?)
    }

    private class SpacingDecoration : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
//            super.getItemOffsets(outRect, view, parent, state)

            outRect?.bottom = 10
            outRect?.top = 10
            outRect?.left = 10
            outRect?.right = 10
        }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance() =
                GridFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, 2)
                    }
                }
    }
}

package com.example.fragments.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragments.R
import kotlinx.android.synthetic.main.fragment_layout_produtcts.*

class FragmentListProducts private constructor() : Fragment() {

    private lateinit var viewLayout: View
    private lateinit var labelInfor: String

    companion object {
        const val LABEL_INFORMATION  = "LABEL_INFORMATION"

        fun instance(labelInfo: String): FragmentListProducts {
            val fragment = FragmentListProducts()
            val args = Bundle()
            args.putString(LABEL_INFORMATION, labelInfo)
            fragment.arguments = args
            return fragment
        }

        fun instance(): FragmentListProducts {
            return FragmentListProducts()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.viewLayout = inflater.inflate(R.layout.fragment_layout_produtcts, container, false)
        return viewLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments = arguments
        if(arguments != null && arguments.containsKey(LABEL_INFORMATION)) {
            text_view_product.text = arguments.getString(LABEL_INFORMATION)
        }
    }
}
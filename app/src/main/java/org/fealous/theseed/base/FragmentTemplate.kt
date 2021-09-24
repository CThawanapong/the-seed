package org.fealous.theseed.base

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import org.fealous.core.base.delegate.viewBinding
import org.fealous.theseed.R
import org.fealous.theseed.databinding.FragmentEmptyBinding

@AndroidEntryPoint
class FragmentTemplate : BaseFragment(R.layout.fragment_empty) {
    companion object {
        @JvmStatic
        private val TAG = FragmentTemplate::class.java.simpleName

        @JvmStatic
        fun newInstance(): FragmentTemplate {
            val fragment = FragmentTemplate()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    // Injection

    // Data Members
    private val mBinding by viewBinding(FragmentEmptyBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        arguments?.apply { }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance(view, savedInstanceState)

        // Register ViewModel
    }

    private fun initInstance(rootView: View?, savedInstanceState: Bundle?) {
        // Init View instance
        with(mBinding) {
        }
    }

    override val screenName: String
        get() = TAG
}

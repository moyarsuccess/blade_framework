package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.di.functions.inject
import com.example.di.scope.ScopeComponent
import com.example.myapplication.common.getInstanceId
import com.example.myapplication.databinding.Frg2LayoutBinding

class Frg2 : Fragment(), ScopeComponent {

    override val scope = frg2Scope
    private lateinit var binding: Frg2LayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val parentScopeId = arguments?.getString(EXTRA_PARENT_SCOPE_ID) ?: ""
        bind(parentScopeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Frg2LayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        unbind()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitle.text = "Frg 2 -> Test 2 -> ${inject<Test2>().getInstanceId()}"
    }


    companion object {

        private const val EXTRA_PARENT_SCOPE_ID = "EXTRA_PARENT_SCOPE_ID"

        fun newInstance(parentScopeId: String): Frg2 {
            return Frg2().apply {
                val bundle = Bundle()
                bundle.putString(EXTRA_PARENT_SCOPE_ID, parentScopeId)
                arguments = bundle
            }
        }
    }
}
package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.di.common.DI_PARENT_SCOPE_ID_KEY
import com.example.di.functions.inject
import com.example.di.scope.ScopeAwareFragment
import com.example.myapplication.common.getInstanceId
import com.example.myapplication.databinding.Frg2LayoutBinding

class Frg2 : ScopeAwareFragment() {

    override val scope = ::frg2Scope
    private lateinit var binding: Frg2LayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Frg2LayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitle.text = "Frg 2 -> Test 2 -> ${inject<Test2>().getInstanceId()}"
    }


    companion object {

        fun newInstance(parentScopeId: String): Frg2 {
            return Frg2().apply {
                val bundle = Bundle()
                bundle.putString(DI_PARENT_SCOPE_ID_KEY, parentScopeId)
                arguments = bundle
            }
        }
    }
}
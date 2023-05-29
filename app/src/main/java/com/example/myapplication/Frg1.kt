package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.di.functions.inject
import com.example.di.scope.ScopeComponent
import com.example.myapplication.common.getInstanceId
import com.example.myapplication.databinding.Frg1LayoutBinding

class Frg1 : Fragment(), ScopeComponent {

    override val scope = frg1Scope
    private lateinit var binding: Frg1LayoutBinding

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
        binding = Frg1LayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitle.text = "Frg 1 -> Test 2 -> ${inject<Test2>().getInstanceId()}"
        childFragmentManager
            .beginTransaction()
            .replace(R.id.frm_container, Frg2.newInstance(scope.scopeId), "frg2")
            .addToBackStack("frg2")
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbind()
    }

    companion object {

        private const val EXTRA_PARENT_SCOPE_ID = "EXTRA_PARENT_SCOPE_ID"

        fun newInstance(parentScopeId: String): Frg1 {
            return Frg1().apply {
                val bundle = Bundle()
                bundle.putString(EXTRA_PARENT_SCOPE_ID, parentScopeId)
                arguments = bundle
            }
        }
    }
}
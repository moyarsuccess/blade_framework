package dev.moyar.blade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.moyar.blade.common.getInstanceId
import dev.moyar.blade.databinding.Frg1LayoutBinding
import dev.moyar.di.common.DI_PARENT_SCOPE_ID_KEY
import dev.moyar.di.functions.inject
import dev.moyar.di.scope.ScopeAwareFragment

class Frg1 : ScopeAwareFragment() {

    override val scope = frg1Scope
    private lateinit var binding: Frg1LayoutBinding

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

    companion object {

        fun newInstance(parentScopeId: String): Frg1 {
            return Frg1().apply {
                val bundle = Bundle()
                bundle.putString(DI_PARENT_SCOPE_ID_KEY, parentScopeId)
                arguments = bundle
            }
        }
    }
}
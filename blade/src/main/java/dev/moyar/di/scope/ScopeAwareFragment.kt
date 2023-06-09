package dev.moyar.di.scope

import android.os.Bundle
import androidx.fragment.app.Fragment
import dev.moyar.di.common.DI_PARENT_SCOPE_ID_KEY

abstract class ScopeAwareFragment : Fragment(), ScopeComponent {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindDi(arguments?.getString(DI_PARENT_SCOPE_ID_KEY))
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindDi()
    }
}
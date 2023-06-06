package dev.moyar.di.scope

import android.os.Bundle
import androidx.fragment.app.Fragment
import dev.moyar.di.common.DI_PARENT_SCOPE_ID_KEY
import java.util.UUID

abstract class ScopeAwareFragment : Fragment(), ScopeComponent {

    override val scopeComponentId: String = UUID.randomUUID().toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindDi(arguments?.getString(DI_PARENT_SCOPE_ID_KEY))
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindDi()
    }
}
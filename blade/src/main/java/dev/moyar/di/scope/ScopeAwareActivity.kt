package dev.moyar.di.scope

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.moyar.di.common.DI_PARENT_SCOPE_ID_KEY
import java.util.UUID

abstract class ScopeAwareActivity : AppCompatActivity(), ScopeComponent {

    override val scopeComponentId: String = UUID.randomUUID().toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindDi(intent.getStringExtra(DI_PARENT_SCOPE_ID_KEY))
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindDi()
    }
}
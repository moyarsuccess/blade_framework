package dev.moyar.di.scope

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import java.util.UUID

abstract class ScopeAwareView(
    context: Context,
    attributeSet: AttributeSet
) : FrameLayout(context, attributeSet), ScopeComponent {

    override val scopeComponentId: String = UUID.randomUUID().toString()
}
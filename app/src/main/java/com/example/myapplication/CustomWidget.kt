package com.example.myapplication

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.di.functions.inject
import com.example.di.functions.injectOrNull
import com.example.di.scope.ScopeAwareView
import com.example.di.scope.ScopeComponent
import com.example.myapplication.common.getInstanceId
import com.example.myapplication.databinding.CustomWidgetBinding

class CustomWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet
) : ScopeAwareView(context, attrs), ScopeComponent, LifecycleEventObserver {

    override val scope = ::customWidgetScope
    private val li = LayoutInflater.from(context)
    private val binding = CustomWidgetBinding.inflate(li, this, true)

    fun init(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                bindDi()
                val test1 = inject<Test1>()
                val test2 = injectOrNull<Test2>()
                binding.tvTitle.text =
                    "Text of Test 1 -> ${test1.getInstanceId()} - Test 2 -> ${test2?.getInstanceId()}"
            }

            Lifecycle.Event.ON_DESTROY -> unbindDi()
            else -> {
                // Do nothing
            }
        }
    }
}
package dev.moyar.blade

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.moyar.blade.databinding.ActivityMainBinding
import dev.moyar.di.functions.lazyInject
import dev.moyar.di.scope.DiScope
import dev.moyar.di.scope.ScopeComponent

class MainActivity : AppCompatActivity(), ScopeComponent {

    private lateinit var binding: ActivityMainBinding
    private val baseUrl: String by lazyInject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindDi()
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        Toast.makeText(this, baseUrl, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindDi()
    }

    override val scope: DiScope
        get() = mainActivityScope
}

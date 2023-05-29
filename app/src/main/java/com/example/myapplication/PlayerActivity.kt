package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.di.scope.DiScope
import com.example.di.scope.ScopeComponent

class PlayerActivity : AppCompatActivity(), ScopeComponent {

    override val scope: DiScope = playerActivityScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val parentScopeId = intent.getStringExtra(EXTRA_PARENT_SCOPE_ID) ?: ""
        bind(parentScopeId)
        setContentView(R.layout.activity_player)
        applyFragment(Frg1.newInstance(scope.scopeId), "frg1")
    }

    private fun applyFragment(frg: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frm_container, frg, tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbind()
    }

    companion object {

        private const val EXTRA_PARENT_SCOPE_ID = "EXTRA_PARENT_SCOPE_ID"
        fun getIntent(context: Context, parentScopeId: String): Intent {
            return Intent(context, PlayerActivity::class.java).apply {
                putExtra(EXTRA_PARENT_SCOPE_ID, parentScopeId)
            }
        }
    }
}
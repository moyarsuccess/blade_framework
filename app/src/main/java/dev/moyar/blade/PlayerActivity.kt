package dev.moyar.blade

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import dev.moyar.blade.common.getInstanceId
import dev.moyar.blade.databinding.ActivityPlayerBinding
import dev.moyar.di.common.DI_PARENT_SCOPE_ID_KEY
import dev.moyar.di.functions.inject
import dev.moyar.di.scope.ScopeAwareActivity

class PlayerActivity : ScopeAwareActivity() {

    override val scope = ::playerActivityScope
    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.playerActTitle.text =
            "Player activity -> Test 2 -> " + inject<Test2>().getInstanceId()
        applyFragment(Frg1.newInstance(scopeComponentId), R.id.frm_container_1, "frg1")
        applyFragment(Frg1.newInstance(scopeComponentId), R.id.frm_container_2, "frg2")
        applyFragment(Frg1.newInstance(scopeComponentId), R.id.frm_container_3, "frg3")
    }

    private fun applyFragment(frg: Fragment, containerId: Int, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(containerId, frg, tag)
            .commit()
    }

    companion object {

        fun getIntent(context: Context, parentScopeId: String): Intent {
            return Intent(context, PlayerActivity::class.java).apply {
                putExtra(DI_PARENT_SCOPE_ID_KEY, parentScopeId)
            }
        }
    }
}
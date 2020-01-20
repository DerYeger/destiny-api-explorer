package eu.yeger.destiny_api_explorer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import eu.yeger.destiny_api_explorer.ui.xur.XurFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, XurFragment.newInstance())
                .commitNow()
        }
    }
}

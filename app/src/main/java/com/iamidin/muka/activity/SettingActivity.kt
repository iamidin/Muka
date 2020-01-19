package com.iamidin.muka.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.iamidin.muka.R
import kotlinx.android.synthetic.main.activity_setting.*
import java.util.*


class SettingActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val btnChangeLanguage: Button = btn_change_language
        btnChangeLanguage.setOnClickListener(this)

        changeToolbarTitle(resources.getString(R.string.app_name).toUpperCase(Locale.getDefault()) + " " + resources.getString(R.string.title_settings).toUpperCase(
            Locale.getDefault()))

    }

    private fun changeToolbarTitle(title: String?) {
        supportActionBar?.title = title
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_change_language -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

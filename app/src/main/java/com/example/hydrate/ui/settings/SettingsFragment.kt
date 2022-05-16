package com.example.hydrate.ui.settings

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.hydrate.activity.MainActivity


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(com.example.hydrate.R.xml.root_preferences, rootKey)

        var editTextPreference = preferenceManager.findPreference<EditTextPreference>("cup_one")
        editTextPreference?.setOnBindEditTextListener { editText -> editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED }

        editTextPreference = preferenceManager.findPreference<EditTextPreference>("cup_two")
        editTextPreference?.setOnBindEditTextListener { editText -> editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED }

        editTextPreference = preferenceManager.findPreference<EditTextPreference>("cup_three")
        editTextPreference?.setOnBindEditTextListener { editText -> editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Settings"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        var button = preferenceManager.findPreference<Preference>("saveNotificationsButton")
        button?.setOnPreferenceClickListener {
            val signOutIntent = Intent(activity, MainActivity::class.java)
            startActivity(signOutIntent)
            true
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                findNavController().navigate(com.example.hydrate.R.id.action_settingsFragment_to_mainActivity2)
        }
        return true
    }

}
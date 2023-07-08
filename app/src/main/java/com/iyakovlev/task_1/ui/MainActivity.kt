package com.iyakovlev.task_1.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.iyakovlev.task_1.common.Constants.APP_PREFERENCES
import com.iyakovlev.task_1.common.Constants.NAME
import com.iyakovlev.task_1.common.Constants.SURNAME
import com.iyakovlev.task_1.databinding.MainLayoutBinding

class MainActivity : ComponentActivity() {

    private lateinit var binding: MainLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: decompose
        val preferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val name = preferences.getString(NAME, "Name")  //todo string
        val surname = preferences.getString(SURNAME, "Surname") //todo string

        binding.nameText.text = "$name $surname"

    }
}
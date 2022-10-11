package reb.or.kr.app1_memo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import reb.or.kr.app1_memo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
package reb.or.kr.app1_memo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import reb.or.kr.app1_memo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 딜레이 시간을 주어 splash 화면이 좀더 보여지게 한다.
        SystemClock.sleep(1000)

        // Splash 화면 이후로 보여질 화면의 테마를 설정한다.
        setTheme(R.style.Theme_App1_Memo)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
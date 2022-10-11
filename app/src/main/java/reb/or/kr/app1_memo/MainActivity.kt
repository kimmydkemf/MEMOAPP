package reb.or.kr.app1_memo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.Menu
import android.view.MenuItem
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

        // Toolbar를 설정한다.
        setSupportActionBar(binding.mainToolbar)
        title="메모앱"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            // 추가 버튼
            R.id.main_menu_add->{
                val memo_add_intent = Intent(this,MemoAddActivity::class.java)
                startActivity(memo_add_intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
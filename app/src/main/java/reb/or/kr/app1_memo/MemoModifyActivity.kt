package reb.or.kr.app1_memo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import reb.or.kr.app1_memo.databinding.ActivityMemoModifyBinding

class MemoModifyActivity : AppCompatActivity() {

    lateinit var binding:ActivityMemoModifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.memoModifyToolbar)
        title="메모 수정"

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
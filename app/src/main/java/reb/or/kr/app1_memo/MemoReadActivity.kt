package reb.or.kr.app1_memo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import reb.or.kr.app1_memo.databinding.ActivityMemoReadBinding

class MemoReadActivity : AppCompatActivity() {

    lateinit var binding:ActivityMemoReadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMemoReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.memoReadToolbar)
        title="메모 읽기"

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        val helper = DBHelper(this)
        val sql="""
            select memo_subject, memo_date,memo_text
            from MemoTable
            where memo_idx=?
        """.trimIndent()

        // 글 번호 추출
        val memo_idx=intent.getIntExtra("memo_idx",0)

        // 쿼리 실행
        val args= arrayOf(memo_idx.toString())
        val c1=helper.writableDatabase.rawQuery(sql,args)
        c1.moveToNext()

        // 글 데이터를 추출한다.
        val idx1=c1.getColumnIndex("memo_subject")
        val idx2=c1.getColumnIndex("memo_date")
        val idx3=c1.getColumnIndex("memo_text")

        val memo_subject=c1.getString(idx1)
        val memo_date=c1.getString(idx2)
        val memo_text=c1.getString(idx3)

        helper.writableDatabase.close()

//        Log.d("memo_app",memo_subject)
//        Log.d("memo_app",memo_date)
//        Log.d("memo_app",memo_text)

        binding.memoReadSubject.text="제목 : $memo_subject"
        binding.memoReadDate.text= "작성날짜 : $memo_date"
        binding.memoReadText.text=memo_text
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.read_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
            }
            // 메뉴 수정
            R.id.read_modify->{
                var memoModifyIntent = Intent(this,MemoModifyActivity::class.java)

                // 글 번호를 담는다.
                val memo_idx = intent.getIntExtra("memo_idx",0)
                memoModifyIntent.putExtra("memo_idx",memo_idx)

                startActivity(memoModifyIntent)
            }
            // 메뉴 삭제
            R.id.read_delete->{
                var builder=AlertDialog.Builder(this)

                builder.setTitle("메모삭제")
                builder.setMessage("메모를 삭제하겠습니까?")
                builder.setPositiveButton("삭제"){dialogInterface,i->
                    // 데이터 베이스 오픈
                    val helper = DBHelper(this)
                    // 쿼리문
                    val sql="""
                        delete from MemoTable
                        where memo_idx = ?
                    """.trimIndent()

                    // 글번호를 가져온다
                    val memo_idx=intent.getIntExtra("memo_idx",0)

                    // 쿼리문 실행
                    var args= arrayOf(memo_idx.toString())
                    helper.writableDatabase.execSQL(sql,args)
                    helper.writableDatabase.close()
                    finish()
                }
                builder.setNegativeButton("취소",null)

                builder.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
package reb.or.kr.app1_memo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import reb.or.kr.app1_memo.databinding.ActivityMainBinding
import reb.or.kr.app1_memo.databinding.MainRecyclerRowBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    // 제목을 담을 ArrayList
    val subject_list=ArrayList<String>()
    // 작성 날짜를 담을 ArrayList
    val date_list = ArrayList<String>()
    // 메모의 번호를 담을 ArrayList
    val idx_list=ArrayList<Int>()

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

        // RecyclerView 셋팅
        val main_recycler_adapter = MainRecyclerAdapter()
        binding.mainRecycler.adapter=main_recycler_adapter
        binding.mainRecycler.layoutManager=LinearLayoutManager(this)



    }

    override fun onResume() {
        super.onResume()

        // ArrayList를 비워준다.
        subject_list.clear()
        date_list.clear()
        idx_list.clear()

        // 데이터 베이스 오픈
        val helper =DBHelper(this)

        // 쿼리문
        val sql="""
            select memo_subject,memo_date,memo_idx
            from MemoTable
            order by memo_idx desc
        """.trimIndent()

        val c1=helper.writableDatabase.rawQuery(sql,null)

        while(c1.moveToNext()){
            // 컬럼 index를 가져온다.
            val idx1=c1.getColumnIndex("memo_subject")
            val idx2=c1.getColumnIndex("memo_date")
            val idx3=c1.getColumnIndex("memo_idx")

            // 데이터를 가져온다.
            val memo_subject=c1.getString(idx1)
            val memo_date=c1.getString(idx2)
            val memo_idx=c1.getInt(idx3)

//            Log.d("memo_app",memo_subject)
//            Log.d("memo_app",memo_date)
//            Log.d("memo_app","----------")

            // 데이터를 담는다.
            subject_list.add(memo_subject)
            date_list.add(memo_date)
            idx_list.add(memo_idx)

            // RecyclerView에게 갱신하라고 명령한다.
            binding.mainRecycler.adapter?.notifyDataSetChanged()
        }
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

    // RecyclerView의 어댑터
    inner class MainRecyclerAdapter : RecyclerView.Adapter<MainRecyclerAdapter.ViewHolderClass>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val mainRecyclerBinding = MainRecyclerRowBinding.inflate(layoutInflater)
            val holder = ViewHolderClass(mainRecyclerBinding)

            // 생성되는 항목 View의 가로 세로 길이를 설정한다.
            val layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            mainRecyclerBinding.root.layoutParams=layoutParams

            // 항목 View에 이벤트를 설정한다.
            mainRecyclerBinding.root.setOnClickListener(holder)

            return holder
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowMemoSubject.text=subject_list[position]
            holder.rowMemoDate.text=date_list[position]
        }

        override fun getItemCount(): Int {
            return subject_list.size
        }
        // HolderClass
        inner class ViewHolderClass(mainRecyclerBinding: MainRecyclerRowBinding):RecyclerView.ViewHolder(mainRecyclerBinding.root),View.OnClickListener {
            // View의 주소값을 담는다.
            val rowMemoSubject= mainRecyclerBinding.memoSubject
            val rowMemoDate=mainRecyclerBinding.memoDate
            override fun onClick(p0: View?) {
                // Log.d("memo_app","항목 클릭 : $adapterPosition")
                // 글 읽는 Activity를 실행한다.

                // 현재 항목 글의 index를 추출한다.
                val memo_idx=idx_list[adapterPosition]
//                Log.d("memo_app","memo_idx : $memo_idx")

                val memoReadAdapter = Intent(baseContext,MemoReadActivity::class.java)
                memoReadAdapter.putExtra("memo_idx",memo_idx)
                startActivity(memoReadAdapter)
            }
        }
    }
}
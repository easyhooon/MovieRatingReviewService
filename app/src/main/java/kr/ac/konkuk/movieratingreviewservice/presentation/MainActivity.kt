package kr.ac.konkuk.movieratingreviewservice.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kr.ac.konkuk.movieratingreviewservice.R
import kr.ac.konkuk.movieratingreviewservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val navigationController by lazy {
        (supportFragmentManager.findFragmentById(R.id.mainNavigationHostContainer) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
    }

    //Navigation 2.3.5
    //bottomNavigationView 와 연동을 했을때 주의해야할 사항
    //탭 간의 이동을 했을 때 이전의 탭에는 onDestroy 가 호출이 되고 다시 돌아왔을 땐 새로 생성되는 life cycle 을 가짐
    //jetpack 의 viewModel 을 쓸 때 onCleared 가 호출이 되야 함
    //2.4.0-alpha01 부터는 setupWithNavController 를 사용할 때 각 메뉴 항목의 상태가 저장 및 복원됨
    private fun initViews() {
        //stack 이 쌓여 탭 간의 이동을 할 때, back button 이 생기는 것을 방지
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.home_dest, R.id.my_page_dest))
        binding.bottomNavigationView.setupWithNavController(navigationController)
        binding.toolbar.setupWithNavController(navigationController, appBarConfiguration)
    }
}
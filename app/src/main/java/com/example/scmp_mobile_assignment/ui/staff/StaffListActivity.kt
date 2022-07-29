package com.example.scmp_mobile_assignment.ui.staff

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scmp_mobile_assignment.R
import com.example.scmp_mobile_assignment.data.remote.StaffRepository
import com.example.scmp_mobile_assignment.databinding.ActivityStaffListBinding
import com.example.scmp_mobile_assignment.ui.login.LoginViewModel
import com.example.scmp_mobile_assignment.utils.hide
import com.example.scmp_mobile_assignment.utils.show
import com.example.scmp_mobile_assignment.utils.showIf

class StaffListActivity : AppCompatActivity() {
    private lateinit var viewModel: StaffViewModel
    private lateinit var adapter: StaffListAdapter

    companion object {
        const val PARAMS_TOKEN = "params_token"
        fun start(context: Context, token: String) {
            context.startActivity(getStarterIntent(context, token))
        }

        private fun getStarterIntent(
            context: Context, token: String
        ): Intent {
            return Intent(context, StaffListActivity::class.java).putExtra(PARAMS_TOKEN, token)
        }
    }

    private lateinit var binding: ActivityStaffListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStaffListBinding.inflate(layoutInflater)
        viewModel =
            ViewModelProvider(this, StaffViewModel.StaffViewModelFactory(StaffRepository())).get(
                StaffViewModel::class.java
            )
        setContentView(binding.root)
        initView()
        initData()
    }

    private fun initView() {
        adapter = StaffListAdapter {
            viewModel.getStaffList(loadMore = true)
        }
        binding.tvToken.text = getString(R.string.token, intent.getStringExtra(PARAMS_TOKEN))
        binding.rvStaff.adapter = adapter
    }

    private fun initData() {
        viewModel.loading.observe(this) { isLoading ->
            binding.loading.showIf { isLoading }
        }
        viewModel.staffList.observe(this) {
            adapter.setStaffList(it)
        }
        viewModel.getStaffList()
    }

}
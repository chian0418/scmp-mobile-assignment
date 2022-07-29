package com.example.scmp_mobile_assignment.ui.staff

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scmp_mobile_assignment.data.model.StaffData
import com.example.scmp_mobile_assignment.databinding.ViewStaffFooterBinding
import com.example.scmp_mobile_assignment.databinding.ViewStaffItemBinding
import com.example.scmp_mobile_assignment.utils.BaseViewHolder
import com.example.scmp_mobile_assignment.utils.toBitmap
import kotlinx.coroutines.*
import java.net.URL

class StaffListAdapter(private val onLoadMoreClick: () -> Unit = {}) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var staffList = listOf<StaffData>()
    private val TYPE_INFO = 0
    private val TYPE_LOAD_MORE = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_INFO) {
            val itemBinding =
                ViewStaffItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            StaffListViewHolder(itemBinding)
        } else {
            val itemBinding =
                ViewStaffFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            StaffLoadMoreViewHolder(itemBinding, onLoadMoreClick)
        }
    }

    override fun getItemCount(): Int {
        return staffList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (staffList[position]) {
            is StaffData.StaffInfo -> TYPE_INFO
            else -> TYPE_LOAD_MORE
        }
    }

    fun setStaffList(list: List<StaffData>) {
        this.staffList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is StaffLoadMoreViewHolder -> {
                holder.bind(Unit)
            }
            is StaffListViewHolder -> {
                holder.bind(staffList[position] as StaffData.StaffInfo)
            }
        }
    }
}

class StaffListViewHolder(private val itemBinding: ViewStaffItemBinding) :
    BaseViewHolder<StaffData.StaffInfo>(itemBinding.root) {
    override fun bind(item: StaffData.StaffInfo) = with(itemBinding) {
        val result: Deferred<Bitmap?> = GlobalScope.async(Dispatchers.IO) {
            URL(item.avatar).toBitmap()
        }
        GlobalScope.launch(Dispatchers.Main) {
            ivAvatar.setImageBitmap(
                result.await()
            )
        }
        tvEmail.text = item.email
        tvName.text = StringBuilder().append(item.name)
    }
}

class StaffLoadMoreViewHolder(
    private val itemBinding: ViewStaffFooterBinding,
    private val onLoadMoreClick: () -> Unit
) : BaseViewHolder<Unit>(itemBinding.root) {
    override fun bind(item: Unit) {
        itemBinding.btLoadMore.setOnClickListener {
            onLoadMoreClick.invoke()
        }
    }
}
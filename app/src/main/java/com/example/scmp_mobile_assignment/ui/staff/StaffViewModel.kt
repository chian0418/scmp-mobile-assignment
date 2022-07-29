package com.example.scmp_mobile_assignment.ui.staff

import androidx.lifecycle.*
import com.example.scmp_mobile_assignment.data.model.StaffData
import com.example.scmp_mobile_assignment.data.remote.LoginRepository
import com.example.scmp_mobile_assignment.data.remote.NetworkUtils
import com.example.scmp_mobile_assignment.data.remote.StaffRepository
import com.example.scmp_mobile_assignment.ui.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class StaffViewModel(private val repository: StaffRepository) : ViewModel() {
    private val _staffList = MutableLiveData<List<StaffData>>()
    val staffList: LiveData<List<StaffData>> = _staffList

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    private var currentPage = 1

    fun getStaffList(loadMore: Boolean = false) {
        if (loadMore) {
            currentPage++
        }
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            repository.getStaffList(currentPage).map {
                arrayListOf<StaffData>().apply {
                    val original = _staffList.value
                    if (!original.isNullOrEmpty()) {
                        addAll(original.filterIsInstance<StaffData.StaffInfo>())
                    }
                    addAll(it.list.map {
                        StaffData.StaffInfo(
                            it.avatar,
                            it.email,
                            StringBuilder().append(it.firstName).append(" ").append(it.lastName)
                                .toString()
                        )
                    })
                    if (it.page < it.totalPages) {
                        add(StaffData.Footer)
                    }
                }
            }.catch { e ->
                _loading.postValue(false)
                e.printStackTrace()
            }.collect {
                _loading.postValue(false)
                _staffList.postValue(it)
            }
        }
    }
    class StaffViewModelFactory(private val repository: StaffRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return StaffViewModel(repository) as T
        }
    }
}
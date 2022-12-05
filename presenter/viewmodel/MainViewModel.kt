package com.evgeny5454.testapp.presenter.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evgeny5454.testapp.data.entity.CharacterModel
import com.evgeny5454.testapp.domain.usecase.UseCase
import com.evgeny5454.testapp.other.Constants.filterList
import com.evgeny5454.testapp.other.Resource
import com.evgeny5454.testapp.presenter.model.Filter
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    private val _loadState = MutableLiveData<Resource>()
    val loadState: LiveData<Resource> = _loadState

    private val _data = MutableLiveData<List<CharacterModel>>()
    val data: LiveData<List<CharacterModel>> = _data

    private val _filter = MutableLiveData(filterList)
    val filter: LiveData<List<Filter>> = _filter

    private var sortList = mutableListOf<CharacterModel>()

    init {
        getData()
    }

    private fun getData() {
        _loadState.postValue(Resource.loading())
        viewModelScope.launch(Dispatchers.IO) {
            if (useCase.getCharactersFromApiUseCase.execute()) {
                _loadState.postValue(Resource.success())
            } else {
                _loadState.postValue(Resource.error("Включите передачу данных"))
            }
        }
    }

    @SuppressLint("CheckResult")
    fun getDataFromDatabase() {
        useCase.getMarvelCharactersUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                sortList.addAll(it.reversed())
                _data.postValue(sortList)
            }, {

            })
    }

    private fun sortByNameReversed() {
        val sort = sortList.sortedBy { it.name }
        _data.postValue(sort.reversed())
    }

    private fun sortByDateReversed() {
        val sort = sortList.sortedBy { it.modified }
        _data.postValue(sort.reversed())
    }

    private fun sortByDate() {
        _data.postValue(sortList.sortedBy { it.modified })
    }

    private fun sortByName() {
        _data.postValue(sortList.sortedBy { it.name })
    }

    fun setFilter(filterList: List<Filter>) {
        _filter.postValue(filterList)
        filterList.forEach { filter ->
            if (filter.isChecked) {
                if (filter.id == 0) { sortByName() }
                if (filter.id == 1) { sortByDate() }
                if (filter.id == 2) { sortByNameReversed() }
                if (filter.id == 3) { sortByDateReversed() }
            }
        }
    }
}
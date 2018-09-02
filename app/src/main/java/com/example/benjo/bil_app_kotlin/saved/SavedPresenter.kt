package com.example.benjo.bil_app_kotlin.saved


import com.example.benjo.bil_app_kotlin.room.CarData
import com.example.benjo.bil_app_kotlin.room.CarDataBase
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI

class SavedPresenter : SavedContract.Presenter {




    private val TAG = "SavedPresenter"
    private lateinit var view: SavedContract.View


    override fun loadSavedCars() {
        launch(UI) {
            val list = withContext(CommonPool) {
                CarDataBase.getInstance(view.getContext())?.carDataDao()?.getAll()
            }
            if (list != null) {
                val arrayList = copyListToArrayList(list)
                showSavedCars(arrayList)
            }
        }
    }



    override fun showSavedCars(list: ArrayList<CarData>) {
        view.updateView(list)
    }

    override fun getCarFromDB(vin: Int) {
        launch(UI) {
            val car = withContext(CommonPool) {
                CarDataBase.getInstance(view.getContext())?.carDataDao()?.getCar(vin)
            }
            if (car != null) showCar(car)
        }
    }

    override fun deleteCarFromDB(vin: Int): Boolean {
        var car: CarData? = null
        with(CarDataBase.getInstance(view.getContext())!!.carDataDao()) {
            launch(UI) {
                launch { deleteCar(vin) }
                car = async(CommonPool) { getCar(vin) }.await()
            }
        }
        return car == null
    }

    override fun attachView(v: SavedContract.View) {
        view = v
    }

    override fun showCar(car: CarData) {
        view.showCar(car)
    }

    private fun copyListToArrayList(list: List<CarData>): ArrayList<CarData> {
        val arrayList = arrayListOf<CarData>()
        for (item in list) {
            arrayList.add(CarData(null, item.vin, item.json))
        }
        return arrayList
    }

    override fun onMultipleClick(multiList: ArrayList<CarData>,
                                 singleList: ArrayList<CarData>,
                                 position: Int): ArrayList<CarData> {
        if (multiList.contains(singleList[position])) {
            multiList.remove(singleList[position])
        } else
            multiList.add(singleList[position])

        return multiList
    }

}
package com.example.benjo.bil_app_kotlin.saved


import com.example.benjo.bil_app_kotlin.data.repository.CarRepository
import com.example.benjo.bil_app_kotlin.data.room.CarData
import com.example.benjo.bil_app_kotlin.utils.CommonUtils


class SavedPresenter(private val carRepository: CarRepository) : SavedContract.Presenter {
    private val TAG = "SavedPresenter"
    private lateinit var view: SavedContract.View


    override fun loadSavedCars() {
        val list = carRepository.getAllCars()
        if (list != null) showSavedCars(CommonUtils().listToArrayList(list))
    }

    override fun getCarFromDB(vin: Int) {
        val car = carRepository.getCar(vin)
        if (car != null) showCar(car)
    }

    override fun deleteCarFromDB(vin: Int): Boolean {
        carRepository.deleteCar(vin)
        return carRepository.getCar(vin) == null
    }

    override fun showSavedCars(list: ArrayList<CarData>) {
        view.updateView(list)
    }

    override fun showCar(car: CarData) {
        view.showCar(car)
    }

    override fun attachView(v: SavedContract.View) {
        view = v
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
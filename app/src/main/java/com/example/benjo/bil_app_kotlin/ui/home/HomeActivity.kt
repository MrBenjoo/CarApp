package com.example.benjo.bil_app_kotlin.ui.home


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import androidx.navigation.findNavController
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.model.Compare
import com.example.benjo.bil_app_kotlin.data.model.Result
import com.example.benjo.bil_app_kotlin.data.repository.CarRepositoryImpl
import com.example.benjo.bil_app_kotlin.data.room.CarDataBase
import com.example.benjo.bil_app_kotlin.ui.saved.SavedContract
import com.example.benjo.bil_app_kotlin.ui.saved.SavedPresenter
import com.example.benjo.bil_app_kotlin.ui.tab.TabsContract
import com.example.benjo.bil_app_kotlin.ui.basic.BasicPresenter
import com.example.benjo.bil_app_kotlin.ui.tech.TechPresenter
import com.example.benjo.bil_app_kotlin.utils.CommonUtils
import com.example.benjo.bil_app_kotlin.utils.JsonCompare
import com.google.gson.GsonBuilder


class HomeActivity : AppCompatActivity() {
    lateinit var presenter: HomeContract.Presenter
    lateinit var savedPresenter: SavedContract.Presenter
    lateinit var basicPresenter: TabsContract.BasicPresenter
    lateinit var techPresenter: TabsContract.TechPresenter

    var selected: Float = 0F
    var firstJson : String? = null
    var secondJson : String? = null
    lateinit var compare : Compare


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        presenter = HomePresenter()
        basicPresenter = BasicPresenter()
        techPresenter = TechPresenter()
        savedPresenter = SavedPresenter(CarRepositoryImpl(CarDataBase.getInstance(applicationContext)!!))

    }

    override fun onSupportNavigateUp() = findNavController(R.id.mainNavigationFragment).navigateUp()


    fun showText(textID: Int) {
        Snackbar.make(findViewById<View>(android.R.id.content), textID, Snackbar.LENGTH_LONG).show()
    }

    fun showText(text: String?) {
        Snackbar.make(findViewById<View>(android.R.id.content), text.toString(), Snackbar.LENGTH_LONG).show()
    }

    fun saveSecondJson(secondJson: String?) {
        this.secondJson = secondJson
    }

    fun saveFirstJson(firstJson: String?) {
        this.firstJson = firstJson
    }


}

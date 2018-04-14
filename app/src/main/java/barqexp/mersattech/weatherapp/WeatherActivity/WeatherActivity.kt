package barqexp.mersattech.weatherapp.WeatherActivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import barqexp.mersattech.weatherapp.Adapters.WeatherAdapter
import barqexp.mersattech.weatherapp.Model.WeatherResponseModel.APIXUResponse
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import barqexp.mersattech.weatherapp.*
import barqexp.mersattech.weatherapp.Interface.WeatherView
import barqexp.mersattech.weatherapp.Location.Location
import barqexp.mersattech.weatherapp.Model.WeatherModel
import barqexp.mersattech.weatherapp.Model.WeatherTextModel
import barqexp.mersattech.weatherapp.Network.NetworkUtils
import barqexp.mersattech.weatherapp.Presenter.WeatherPresenter


class WeatherActivity : AppCompatActivity(), WeatherView {
    lateinit var weatherPresenter: WeatherPresenter
    lateinit var callWeather: Call<APIXUResponse>
    lateinit var weatherAdapter: WeatherAdapter
    val MY_PERMISSIONS_REQUEST_LOCATION = 10
    var mlatLng: LatLng? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        weatherPresenter = WeatherPresenter(this)
        weather_RV.layoutManager = LinearLayoutManager(this)
        Location.getInstance(this).getLocation()
        retry_btn.setOnClickListener(object:View.OnClickListener
        {
            override fun onClick(p0: View?) {

                subscribeToLocationData()
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !== PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this@WeatherActivity,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION)
            return
        }





    }


    private fun subscribeToLocationData() {
        if(!NetworkUtils.getInstance().isNetworkAvailable(this)) {
            updatRetryNetworkView()
            return
        };

        val locationService = Location.getInstance(this)
        locationService?.locationLiveData?.observe(this, android.arch.lifecycle.Observer
        { latLng ->
            mlatLng = latLng

            weatherPresenter.updateWeather(mlatLng)
        })
    }


    private fun updateRecyclerViews() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }


    override fun updateRecyclerView(list: ArrayList<WeatherModel>) {
        super.updateRecyclerView(list)

        weatherAdapter = WeatherAdapter(list)
        weather_RV.adapter = weatherAdapter
    }

    override fun updateMainView(list0: WeatherTextModel) {
        super.updateMainView(list0)
        place_TV.text = list0.weatherDegree
        temptext_TV.text = list0.weatherLocation
        error_IV.visibility = View.GONE
        retry_btn.visibility = View.GONE
        place_TV.visibility = View.VISIBLE
        temptext_TV.visibility = View.VISIBLE
        weather_RV.visibility = View.VISIBLE


    }

    override fun onResume() {
        super.onResume()
        subscribeToLocationData()

    }

    fun updatRetryNetworkView() {
        error_IV.visibility = View.VISIBLE
        retry_btn.visibility = View.VISIBLE
        place_TV.visibility = View.GONE
        temptext_TV.visibility = View.GONE
        weather_RV.visibility = View.GONE


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION ->             subscribeToLocationData()

        }
    }
}




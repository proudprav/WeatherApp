package barqexp.mersattech.weatherapp.Presenter

import barqexp.mersattech.weatherapp.Interface.WeatherView
import barqexp.mersattech.weatherapp.Model.WeatherModel
import barqexp.mersattech.weatherapp.Network.NetworkUtils
import barqexp.mersattech.weatherapp.Model.WeatherResponseModel.APIXUResponse
import barqexp.mersattech.weatherapp.Model.WeatherTextModel
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherPresenter {


 lateinit var weatherView : WeatherView
lateinit var weatherModels: ArrayList<WeatherModel>

    constructor(weatherView: WeatherView) {
        this.weatherView = weatherView
    }

 fun updateWeather(latLng: LatLng?) {
     val callWeather = NetworkUtils.getApiInterface().getWeatherResponse("03a7949903004a0bb2590633181104",latLng?.latitude.toString()+","+latLng?.longitude.toString(), 7)

     callWeather.enqueue(object : Callback<APIXUResponse> {
         override fun onResponse(call: Call<APIXUResponse>, response: Response<APIXUResponse>) {
             var api : APIXUResponse? = response.body()
             if(api!=null){
             updateViews(api!!);}
             else{
                 retryViews();
             }
         }

         override fun onFailure(call: Call<APIXUResponse>, t: Throwable) {
             retryViews();

         }

     })


}

    private fun retryViews() {
        weatherView.updatRetryView()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun updateViews(api: APIXUResponse) {

        weatherView.updateMainView(WeatherTextModel(api.location.name,
                api.current.tempC.toString() + 0x00B0.toChar()))
   // Weather for 7 days
        var response: APIXUResponse = api
        weatherModels = ArrayList()
        for (i in 0..response.forecast.forecastday.size-1) {
            weatherModels.add(WeatherModel(response.forecast.forecastday.get(i).date,
                    response.forecast.forecastday.get(i).day.condition.icon,
                    response.forecast.forecastday.get(i).day.condition.text,
                    response.forecast.forecastday.get(i).day.mintempC.toString() + 0x00B0.toChar()
                            + "/" + response.forecast.forecastday.get(i).day.maxtempC.toString() + 0x00B0.toChar()))
        }

        weatherView.updateRecyclerView(weatherModels)
    }
}
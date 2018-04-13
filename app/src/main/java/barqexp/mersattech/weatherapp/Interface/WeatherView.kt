package barqexp.mersattech.weatherapp.Interface

import barqexp.mersattech.weatherapp.Model.WeatherModel
import barqexp.mersattech.weatherapp.Model.WeatherTextModel

interface WeatherView {
    
     fun updateRecyclerView(list: ArrayList<WeatherModel>) {
        
    }

    fun updateMainView(list0 : WeatherTextModel){}

    fun updatRetryView(){}
}
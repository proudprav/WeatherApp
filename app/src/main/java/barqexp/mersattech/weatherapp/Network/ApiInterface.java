package barqexp.mersattech.weatherapp.Network;

import java.util.ArrayList;

import barqexp.mersattech.weatherapp.Model.WeatherResponseModel.APIXUResponse;
import barqexp.mersattech.weatherapp.Model.WeatherTypeResponseModel.ResponseWeatherType;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by Mersat-tech on 22/11/2017.
 * Get and Post requests
 */

public interface ApiInterface {


    @GET(NetworkConstants.WEATHER)
    Call<APIXUResponse> getWeatherResponse(@Query("key") String latitude,
                                           @Query("q") String logitude,
                                           @Query("days") int days);



    @GET(NetworkConstants.WEATHER_TYPE)
    Call<ArrayList<ResponseWeatherType>> getWeatherType();
}

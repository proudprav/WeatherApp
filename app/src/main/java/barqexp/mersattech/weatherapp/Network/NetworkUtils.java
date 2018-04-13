package barqexp.mersattech.weatherapp.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mersat-tech on 22/11/2017.
 */

public class NetworkUtils {

    public static NetworkUtils sNetworkUtils;
    public static Retrofit sRetrofit;
    public static ApiInterface sApiInterface;

    public static NetworkUtils getInstance() {
        if (sNetworkUtils == null) {
            sNetworkUtils = new NetworkUtils();
        }

        return sNetworkUtils;
    }

    public static Retrofit getsRetrofit() {

        if (sRetrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            Retrofit.Builder builder = new Retrofit.Builder().baseUrl(NetworkConstants.BASE_URL).
                    addConverterFactory(GsonConverterFactory.create());

            sRetrofit = builder.client(httpClient.build()).build();
        }
        return sRetrofit;
    }

    public static ApiInterface getApiInterface() {
        sApiInterface = getsRetrofit().create(ApiInterface.class);
        return sApiInterface;
    }

    public Boolean isNetworkAvailable(Context context) {
        ConnectivityManager check = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] info = check.getAllNetworkInfo();
        for (int i = 0; i < info.length; i++) {
            if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }

        return false;

    }


}

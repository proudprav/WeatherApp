
package barqexp.mersattech.weatherapp.Model.WeatherTypeResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseWeatherType {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("night")
    @Expose
    private String night;
    @SerializedName("icon")
    @Expose
    private Integer icon;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

}

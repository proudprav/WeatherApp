package barqexp.mersattech.weatherapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import barqexp.mersattech.weatherapp.R;
import barqexp.mersattech.weatherapp.Model.WeatherModel;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    ArrayList<WeatherModel> weatherModels;

    public WeatherAdapter(ArrayList<WeatherModel> weatherModels) {
        this.weatherModels = weatherModels;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather,parent,false);

        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        WeatherModel weatherModel = weatherModels.get(position);
        try {
            holder.onBind(weatherModel);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return weatherModels.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder
    {
        TextView dateTV,textTV, degreeTV;
        ImageView iconIV;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            dateTV = itemView.findViewById(R.id.date_TV);
            textTV = itemView.findViewById(R.id.weather_text_TV);
            degreeTV = itemView.findViewById(R.id.weather_degree_TV);
            iconIV =itemView.findViewById(R.id.weather_icon_IV);
        }

        public void onBind(WeatherModel weatherModel) throws ParseException {
            switch (getAdapterPosition())
            {
                case 0 : dateTV.setText("Today");
                break;
                case 1: dateTV.setText("Tomorrow");
                break;
                default:
                    String oldstring = weatherModel.getWeatherDate();
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(oldstring);
                    String newstring = new SimpleDateFormat("dd MMM yyyy").format(date);
                    dateTV.setText(newstring);
                break;
            }

            textTV.setText(weatherModel.getWeatherText());
//            if(weatherModel.getWeatherText().toLowerCase().contains("Shower"))
//            {
//                textTV.setText("Shower");
//            }else if(weatherModel.getWeatherText().toLowerCase().contains("cloud"))
//                { textTV.setText("Cloud");}
//                else if(weatherModel.getWeatherText().toLowerCase().contains("rains"))
//                {textTV.setText("Rains"); }
            degreeTV.setText(weatherModel.getWeatherDegree());
            Picasso.with(itemView.getContext()).load(weatherModel.getWeatherIcon().replaceFirst("/","http:")).into(iconIV);
        }
    }
}

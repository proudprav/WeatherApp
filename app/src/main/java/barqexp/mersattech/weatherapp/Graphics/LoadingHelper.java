package barqexp.mersattech.weatherapp.Graphics;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class LoadingHelper {

public static RotateAnimation load(){

    RotateAnimation rotate = new RotateAnimation(
            0, 360,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
    );

    rotate.setDuration(900);
    rotate.setRepeatCount(Animation.INFINITE);
    return rotate;
}
}

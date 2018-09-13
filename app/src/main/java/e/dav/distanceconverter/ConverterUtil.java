package e.dav.distanceconverter;

import java.text.DecimalFormat;
public class ConverterUtil {
    // converts to kilometers


    public static String convertMilesToKilometers(double miles) {
       return new DecimalFormat("#.00").format(1.609344*miles);
    }

    // converts to Miles
    public static String convertKilometersToMiles(double kilometers) {
        return new DecimalFormat("#.00").format(0.621371*kilometers) ;
    }
}

package com.bhumiconverter;


import android.util.Log;

public class ConvertBhumi {

    public String useFormula( CharSequence d, CharSequence convertFrom, CharSequence convertTo ) {
        String ans = "";

        String convertTo1 = (String) convertTo;
        if (d.length() == 0) return "0";
        double s = Double.parseDouble(String.valueOf(d));
        switch ((String) convertFrom) {
            case "Inch":
                ans = InchtoAnything(s, convertTo1);
                break;
            case "Feet":
                ans = FeetToAnything(s, convertTo1);
                break;
            case "Meter":
                ans = MeterToAnything(s, convertTo1);
                break;
            case "Centimeter":
                ans = CentiMeterToAnything(s, convertTo1);

                break;
            case "Yard":
                ans = YardToAnything(s, convertTo1);
                break;
            case "Kadi":
                ans = KadiToAnything(s,convertTo1);
                break;
            default:
                return "Hello";
        }
        return ans;
    }
private String KadiToAnything(double kadi, String data){
switch (data){
    case "kadi":
        break;
    case "Inch":
        kadi *= 7.92;
        break;
    case "Feet":
        kadi *= 0.66;
        break;
    case "Meter":
        kadi *= 0.20;
        break;
    case "Centimeter":
        kadi *= 200;
        break;
    case "Yard":
        kadi *= 0.22;
        break;
    default:
        return "Hello";
}
return String.valueOf(kadi);
}
    private String YardToAnything( double yard, String data ) {
        switch (data) {
            case "Yard":
                break;
            case "Meter":
                yard /= 1.094;
                break;
            case "Inch":
                yard *= 36;
                break;
            case "Feet":
                yard *= 3;
                break;
            case "Centimeter":
                yard *= 91.44;
                break;
            case "Kadi":
                yard *= 285.15;
                break;
            default:
                return "hello";
        }
        return String.valueOf(yard);
    }

    private String CentiMeterToAnything( double centimeter, String data ) {
        switch (data) {
            case "Centimeter":
                break;
            case "Meter":
                centimeter /= 100;
                break;
            case "Inch":
                centimeter /= 2.54;
                break;
            case "Yard":
                centimeter /= 91.44;
                break;
            case "Feet":
                centimeter /= 30.48;
                break;
            case "Kadi":
                centimeter /= 200;
                break;
            default:
                return "Hello";
        }
        return String.valueOf(centimeter);
    }

    private String MeterToAnything(double meter, String data ) {
        switch (data) {
            case "Meter":
                break;
            case "Inch":
                meter *= 39.37;
                break;
            case "Feet":
                meter *= 3.281;
                break;
            case "Centimeter":
                meter *= 100;
                break;
            case "Yard":
                meter *= 1.094;
                break;
            case "Kadi":
                meter /= 0.20;
                break;
            default:
                return "Hello";
        }
        return String.valueOf(meter);
    }

    private String FeetToAnything(double feet, String data ) {
        switch (data) {
            case "Feet":
                break;
            case "Inch":
                feet *= 12;
                break;
            case "Meter":
                feet /= 3.281;
                break;
            case "Yard":
                feet /= 3;
                break;
            case "Centimeter":
                feet *= 30.48;
                break;
            case "Kadi":
                feet /= 0.66;
                break;
            default:
                return "Hello";
        }
        return String.valueOf(feet);
    }

    private String InchtoAnything( double inch, String Data ) {
        switch (Data) {
            case "Inch":
                break;
            case "Feet":
                inch = inch / 12;
                break;
            case "Centimeter":
                inch = inch * 2.54;
                break;
            case "Meter":
                inch *= 0.0254;
                break;
            case "Yard":
                inch /= 36;
                break;
            case "Kadi":
                inch /= 7.92;
                break;
            default:
                return "Hello";
        }
        return String.valueOf(inch);
    }

}

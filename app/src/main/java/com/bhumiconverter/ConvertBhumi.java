package com.bhumiconverter;


public class ConvertBhumi {

    public String useFormula( CharSequence s, CharSequence convertFrom, CharSequence convertTo ) {
        String ans = "";
        String convertTo1 = (String) convertTo;
        if (s.length() == 0) return "0";
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
            default:
                return "Hello";
        }
        return ans;
    }

    private String YardToAnything( CharSequence s, String data ) {
        double yard = Double.parseDouble(String.valueOf(s));
        switch (data) {
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
            default:
                return "hello";
        }
        return String.valueOf(yard);
    }

    private String CentiMeterToAnything( CharSequence s, String data ) {
        double centimeter = Double.parseDouble(String.valueOf(s));
        switch (data) {
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
            default:
                return "Hello";
        }
        return String.valueOf(centimeter);
    }

    private String MeterToAnything( CharSequence s, String data ) {
        double meter = Double.parseDouble(String.valueOf(s));
        switch (data) {
            case "Inch":
                meter *= 39.37;
                break;
            case "Feet":
                meter *= 3.281;
            case "Centimeter":
                meter *= 100;
                break;
            case "Yard":
                meter *= 1.094;
                break;
            default:
                return "Hello";
        }
        return String.valueOf(meter);
    }

    private String FeetToAnything( CharSequence s, String data ) {
        double feet = Double.parseDouble(String.valueOf(s));
        switch (data) {
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
            default:
                return "Hello";
        }
        return String.valueOf(feet);
    }

    private String InchtoAnything( CharSequence s, String Data ) {
        double inch = Double.parseDouble(String.valueOf(s));

        switch (Data) {
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
            default:
                return "Hello";
        }
        return String.valueOf(inch);
    }

}

package backend.main;

public class Reason {

    String textedReason;


    String Date1;
    String Date2;
    String image;


    public Reason(String textedReason, String date1, String date2) {
        this.textedReason = textedReason;
        Date1 = date1;
        Date2 = date2;
    }

    public Reason(String textedReason, String date1) {
        this.textedReason = textedReason;
        Date1 = date1;
    }

    public String getTextedReason() {
        return textedReason;
    }

    public String getDate1() {
        return Date1;
    }

    public String getDate2() {
        return Date2;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static Reason toReason( String str , boolean isPermenant , String image)
    {
        Reason reason ;
        String[] arr = str.split(",");
        if ( isPermenant)
        {
            reason = new Reason (arr[0],arr[1],arr[2]);
        }
        else
        {
            reason = new Reason (arr[0],arr[1]);
        }
        reason.setImage(image);

        return reason;
    }
}

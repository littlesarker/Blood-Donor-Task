package gub.app.blooddonor;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Donor {

    private  String name;
    private  String mobile;
    private  String  group;
    private  String last_donation;
    private  String donor_address;

    public Donor() {

    }

    public Donor(String name, String mobile, String group, String last_donation, String donor_address) {
        this.name = name;
        this.mobile = mobile;
        this.group = group;
        this.last_donation = last_donation;
        this.donor_address=donor_address;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getGroup() {
        return group;
    }

    public String getLast_donation() {
        return last_donation;
    }

    public String getDonor_address() {
        return donor_address;
    }
}

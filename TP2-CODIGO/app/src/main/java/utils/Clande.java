package utils;

public class Clande {

    private String email;
    private String province;
    private String locality;
    private String postalCode;
    private String streetName;
    private String altitudeStreet;
    private String description;
    private String fromHourClande;
    private String toHourClande;
    private String dateClande;
    private int imageClande;
    private String codClande;

    public Clande(){
    }

    public Clande(String email, String province, String locality,
                  String postalCode, String streetName, String altitudeStreet,
                  String description, String fromHourClande,
                  String toHourClande, String dateClande) {
        this.email = email;
        this.province = province;
        this.locality = locality;
        this.postalCode = postalCode;
        this.streetName = streetName;
        this.altitudeStreet = altitudeStreet;
        this.description = description;
        this.fromHourClande = fromHourClande;
        this.toHourClande = toHourClande;
        this.dateClande = dateClande;
    }

    public void setImageClande(int imageClande) {
        this.imageClande = imageClande;
    }

    public int getImageClande() {
        return imageClande;
    }

    @Override
    public String toString(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setAltitudeStreet(String altitudeStreet) {
        this.altitudeStreet = altitudeStreet;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFromHourClande(String fromHourClande) {
        this.fromHourClande = fromHourClande;
    }

    public void setToHourClande(String toHourClande) {
        this.toHourClande = toHourClande;
    }

    public void setDateClande(String dateClande) {
        this.dateClande = dateClande;
    }

    public String getEmail() {
        return email;
    }

    public String getProvince() {
        return province;
    }

    public String getLocality() {
        return locality;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getAltitudeStreet() {
        return altitudeStreet;
    }

    public String getDescription() {
        return description;
    }

    public String getFromHourClande() {
        return fromHourClande;
    }

    public String getToHourClande() {
        return toHourClande;
    }

    public String getDateClande() {
        return dateClande;
    }

    public String getCodClande() {
        return codClande;
    }

    public void setCodClande(String codClande) {
       this.codClande = codClande;
    }
}

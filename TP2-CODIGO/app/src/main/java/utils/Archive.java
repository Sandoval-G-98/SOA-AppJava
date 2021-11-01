package utils;

import java.io.File;

public class Archive {

    private String fileName;
    private String filePathRegister = "../../main/clandes/registerClande/";
    private String filePathJoin = "../../main/clandes/joinClande/";

    public Archive(String fileName){
        this.fileName = fileName + ".txt";
    }


    public void writeCreateClande(String email, String provinceClande, String localityClande, String postalCodeClande, String streetClande,
                      String altitudeClande, String descriptionClande, String fromHourClande, String toHourClande, String dateClande) {

        File localfile = new File(filePathRegister, this.fileName);
        if(!localfile.exists())
            localfile.mkdirs();

    }

    public void writeJoinClande(String emailOwner, String emailParticipant, String provinceClande, String localityClande, String postalCodeClande, String streetClande,
                                  String altitudeClande, String descriptionClande, String fromHourClande, String toHourClande, String dateClande) {



    }

}

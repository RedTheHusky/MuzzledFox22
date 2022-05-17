package userprofiles.entities;

public interface rGeneral {
    String  table="MemberProfile";
    String keyCharacters="characters";
    int restrictionNormal=5, restrictionBoosted=10;
    static  int intDefaultMinutes=15;
    String gFileMainPath ="resources/json/userprofiles/";
    String gCommandFileCharactersPath =gFileMainPath+"menuCharacters.json";
    public  interface RD{
        String gagProfileName="rdrestraints",gagTable="rdrestraints";
       String nRPSpeach="rpSpeach",nName="name",nAvatar="avatar",nOn="on";
    }


}

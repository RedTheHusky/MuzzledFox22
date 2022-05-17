package nsfw.verify;

public interface iLewdverify {
    String profileName="lewdverification";
    String fieldChastity="chastity",fieldDiaper="diaper",fieldCollar="collar",fieldGeneric="generic";
    String keyCode="code",keyUrl="url", keyImg ="img", keyTimeStamp="timestamps", keyName="name",keyExt="ext";
    String gTitle = "Lewd Verification";
    String gCommand="verify";
    String pathLSave4Chastity ="storage/lewdverify/chastity/userid";
    String pathLSave4Diaper ="storage/lewdverify/diaper/userid";
    String pathLSave4Collar ="storage/lewdverify/collar/userid";
    String pathLSave4Generic ="storage/lewdverify/generic/userid";
}

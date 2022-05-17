package restraints.in;

import kong.unirest.json.JSONArray;
import models.ls.lsStringUsefullFunctions;
import models.ls.lsUsefullFunctions;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface iGagWork {
    default String kittenGag(String text){
        List<String> list=new ArrayList<>();
        if(text.contains("!")){
            list.add("Meow!");list.add("Brrrep!");list.add("Mrr!");list.add("ERrrp!");list.add("Awr!");list.add("Mrrah!");list.add("MhhMeoMHh!");
            list.add("MeWwhhmeoMwwMghm!");list.add("Womw!");list.add("WoahhMghhWoof!");list.add("Meo!");list.add("Miau!");
        }else
        if(text.contains("?")){
            list.add("Meow?");list.add("Brrrep?");list.add("Mrr?");list.add("ERrrp?");list.add("Awr?");list.add("Mrrah?");list.add("MhhMeoMHh?");
            list.add("MeWwhhmeoMwwMghm?");list.add("Womw?");list.add("WoahhMghhWoof?");list.add("Meo?");list.add("Miau?");
        }else
        if(text.contains(".")) {
            list.add("Meow...");
            list.add("Brrrep...");
            list.add("Mrr...");
            list.add("ERrrp...");
            list.add("Awr...");
            list.add("Mrrah...");
            list.add("MhhMeoMHh...");
            list.add("MeWwhhmeoMwwMghm...");
            list.add("Womw...");
            list.add("WoahhMghhWoof...");
            list.add("Meo...");
            list.add("Miau...");
        }else{
            list.add("Meow");list.add("Brrrep");list.add("Mrr");list.add("ERrrp");list.add("Awr");list.add("Mrrah");list.add("MhhMeoMHh");
            list.add("MeWwhhmeoMwwMghm");list.add("Womw");list.add("WoahhMghhWoof");list.add("Meo");list.add("Miau");
        }

        int i= lsUsefullFunctions.getRandom(list.size());
        boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
        if(upper){
            return list.get(i);
        }else{
            return list.get(i).toLowerCase();
        }
    }
    default String puppyGag(String text){
        List<String> list=new ArrayList<>();
        if(text.contains("!")){
            list.add("WOOF!");
            list.add("Arf!"); list.add("Wughgh!"); list.add("Wahh!"); list.add("Owghh!"); list.add("Wofahh..AWohh!"); list.add("ArfhhamhhHmh!"); list.add("Ufffthmh!");
            list.add("Bork!"); list.add("Mewl!"); list.add("Nyah!"); list.add("Woof Woof!"); list.add("Hav!");
        }else
        if(text.contains("?")){
            list.add("WOOF");
            list.add("Arf"); list.add("Wughgh"); list.add("Wahh"); list.add("Owghh?"); list.add("Wofahh..AWohh?"); list.add("ArfhhamhhHmh?"); list.add("Ufffthmh?");
            list.add("Bork"); list.add("Mewl"); list.add("Nyah"); list.add("Woof Woof?"); list.add("Hav?");
        }else
        if(text.contains(".")) {
            list.add("WOOF");
            list.add("Arf");
            list.add("Wughgh...");
            list.add("Wahh...");
            list.add("Owghh...");
            list.add("Wofahh..AWohh...");
            list.add("ArfhhamhhHmh...");
            list.add("Ufffthmh...");
            list.add("Bork");
            list.add("Mewl...");
            list.add("Nyah...");
            list.add("Woof Woof...");
            list.add("Hav...");
        }else{
            list.add("WOOF");
            list.add("Arf"); list.add("Wughgh"); list.add("Wahh"); list.add("Owghh"); list.add("Wofahh..AWohh"); list.add("ArfhhamhhHmh"); list.add("Ufffthmh");
            list.add("Bork"); list.add("Mewl"); list.add("Nyah"); list.add("Woof Woof"); list.add("Hav");
        }

        int i= lsUsefullFunctions.getRandom(list.size());
        boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
        if(upper){
            return list.get(i);
        }else{
            return list.get(i).toLowerCase();
        }
    }
    default String dinosaurGag(String text){
        List<String> list=new ArrayList<>();
        if(text.contains("!")){
            list.add("ROAR!");list.add("RHaaRH!");list.add("RorAhmHH!");list.add("RoaahMhh!");list.add("RrAhhMhhorr!");
            list.add("Rahh..UMWhMRoar!");list.add("RahmMH!");
        }else
        if(text.contains("?")){
            list.add("ROAR?");list.add("RHaaRH?");list.add("RorAhmHH?");list.add("RoaahMhh?");list.add("RrAhhMhhorr?");
            list.add("Rahh..UMWhMRoar");list.add("RahmMH");
        }else
        if(text.contains(".")) {
            list.add("ROAR...");
            list.add("RHaaRH...");
            list.add("RorAhmHH...");
            list.add("RoaahMhh...");
            list.add("RrAhhMhhorr...");
            list.add("Rahh..UMWhMRoar...");
            list.add("RahmMH...");
        }else{
            list.add("ROAR");list.add("RHaaRH");list.add("RorAhmHH");list.add("RoaahMhh");list.add("RrAhhMhhorr");
            list.add("Rahh..UMWhMRoar");list.add("RahmMH");
        }

        int i= lsUsefullFunctions.getRandom(list.size());
        boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
        if(upper){
            return list.get(i);
        }else{
            return list.get(i).toLowerCase();
        }
    }
    default String squeakyGag(String text){
        List<String> list=new ArrayList<>();
        if(text.contains("!")){
            list.add("squeaky!");list.add("squee!");list.add("squeak!");list.add("squirk!");list.add("squeeee!");list.add("squeaky!");
        }else
        if(text.contains("?")){
            list.add("squeaky?");list.add("squee?");list.add("squeak?");list.add("squirk?");list.add("squeeee?");list.add("squeaky?");
        }else
        if(text.contains(".")) {
            list.add("squeaky...");
            list.add("squee...");
            list.add("squeak...");
            list.add("squirk...");
            list.add("squeeee...");
            list.add("squeaky...");
        }else{
            list.add("squeaky");list.add("squee");list.add("squeak");list.add("squirk");list.add("squeeee");list.add("squeaky");
        }

        int i= lsUsefullFunctions.getRandom(list.size());
        boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
        if(upper){
            return list.get(i);
        }else{
            return list.get(i).toLowerCase();
        }
    }
    default String ponyGag(String text){
        List<String> list=new ArrayList<>();
        if(text.contains("!")){
            list.add("Neigh!");list.add("NnneiggGhh!");list.add("Neigghymgh!");list.add("Negghh!");list.add("NeMhhhWgh!");
            list.add("Ne-Mmm!");
        }else
        if(text.contains("?")){
            list.add("Neigh?");list.add("NnneiggGhh?");list.add("Neigghymgh?");list.add("Negghh?");list.add("NeMhhhWgh?");
            list.add("Ne-Mmm?");
        }else
        if(text.contains(".")) {
            list.add("Neigh...");
            list.add("NnneiggGhh...");
            list.add("Neigghymgh...");
            list.add("Negghh...");
            list.add("NeMhhhWgh...");
            list.add("Ne-Mmm...");
        }else{
            list.add("Neigh");list.add("NnneiggGhh");list.add("Neigghymgh");list.add("Negghh");list.add("NeMhhhWgh");
            list.add("Ne-Mmm");
        }

        int i= lsUsefullFunctions.getRandom(list.size());
        boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
        if(upper){
            return list.get(i);
        }else{
            return list.get(i).toLowerCase();
        }
    }
    default String piggyGag(String text){
        List<String> list=new ArrayList<>();
        if(text.contains("!")){
            list.add("Oink!");list.add("Knorrk!");list.add("OinkkUahh!");list.add("Oink Oink!");
        }else
        if(text.contains("?")){
            list.add("Oink?");list.add("Knorrk?");list.add("OinkkUahh?");list.add("Oink Oink?");
        }else
        if(text.contains(".")) {
            list.add("Oink...");
            list.add("Knorrk...");
            list.add("OinkkUahh...");
            list.add("Oink Oink...");
        }else{
            list.add("Oink");list.add("Knorrk");list.add("OinkkUahh");list.add("Oink Oink");
        }

        int i= lsUsefullFunctions.getRandom(list.size());
        boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
        if(upper){
            return list.get(i);
        }else{
            return list.get(i).toLowerCase();
        }
    }
    default String birdGag(String text){
        List<String> list=new ArrayList<>();
        if(text.contains("!")){
            list.add("Squawk!");list.add("MGhSqueknhhMNH!");list.add("Cho Cho!");list.add("SqaghhMmh!");
        }else
        if(text.contains("?")){
            list.add("Squawk?");list.add("MGhSqueknhhMNH?");list.add("Cho Cho?");list.add("SqaghhMmh?");
        }else
        if(text.contains(".")) {
            list.add("Squawk....");
            list.add("MGhSqueknhhMNH...");
            list.add("Cho Cho...");
            list.add("SqaghhMmh...");
        }else{
            list.add("Squawk");list.add("MGhSqueknhhMNH");list.add("Cho Cho");list.add("SqaghhMmh");
        }

        int i= lsUsefullFunctions.getRandom(list.size());
        boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
        if(upper){
            return list.get(i);
        }else{
            return list.get(i).toLowerCase();
        }
    }
    default String cowGag(String text){
        List<String> list=new ArrayList<>();
        if(text.contains("!")){
            list.add("Moo!");list.add("MoooUgh!");list.add("MooMHhh!");list.add("MoooGHH!");list.add("MoooooooMhhh!");
        }else
        if(text.contains("?")){
            list.add("Moo?");list.add("MoooUgh?");list.add("MooMHhh?");list.add("MoooGHH?");list.add("MoooooooMhhh?");
        }else
        if(text.contains(".")) {
            list.add("Moo...");
            list.add("MoooUgh...");
            list.add("MooMHhh...");
            list.add("MoooGHH...");
            list.add("MoooooooMhhh...");
        }else{
            list.add("Moo");list.add("MoooUgh");list.add("MooMHhh");list.add("MoooGHH");list.add("MoooooooMhhh");
        }

        int i= lsUsefullFunctions.getRandom(list.size());
        boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
        if(upper){
            return list.get(i);
        }else{
            return list.get(i).toLowerCase();
        }
    }
    default String sheepGag(String text){
        List<String> list=new ArrayList<>();
        if(text.contains("!")){
            list.add("Baaaah!");list.add("BahMhhbaHh!");list.add("BaaahhGAhh!");
        }else
        if(text.contains("?")){
            list.add("Baaaah?");list.add("BahMhhbaHh?");list.add("BaaahhGAhh?");
        }else
        if(text.contains(".")) {
            list.add("Baaaah...");
            list.add("BahMhhbaHh...");
            list.add("BaaahhGAhh...");
        }else{
            list.add("Baaaah");list.add("BahMhhbaHh");list.add("BaaahhGAhh");
        }

        int i= lsUsefullFunctions.getRandom(list.size());
        boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
        if(upper){
            return list.get(i);
        }else{
            return list.get(i).toLowerCase();
        }
    }
    default String nucleusMaskGag(String text){
        List<String> list=new ArrayList<>();
        if(text.contains("!")){
            list.add("I'm a good slave!");list.add("My owner is kind and merciful!");list.add("My will is to serve my owner!");
        }else
        if(text.contains("?")){
            list.add("Can i be of assistance?");list.add("How may i serve you, sir?");list.add("Can i be of any service?");
        }else{
            list.add("I'm a good slave.");list.add("My owner is kind and merciful.");list.add("I doesn't want to be free.");list.add("My will is to serve my owner");
            list.add("I want to serve my owner");list.add("Please break the keys so i can serve permanently.");list.add("I belong to my owner!");
        }
        int i= lsUsefullFunctions.getRandom(list.size());
        boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
        if(upper){
            return list.get(i);
        }else{
            return list.get(i).toLowerCase();
        }
    }
    default String droneMaskGag(String text){
        List<String> list=new ArrayList<>();
        if(text.contains("!")){
            list.add("This drone/unit is a good slave!");list.add("This drone/unit owner is kind and merciful!");list.add("This drone/unit will is to serve their owner!");
        }else
        if(text.contains("?")){
            list.add("Can this drone/unit be of assistance?");list.add("How may this drone/unit serve you, sir?");list.add("Can this drone/unit be of any service?");
        }else{
            list.add("This drone/unit is a good slave.");list.add("This drone/unit owner is kind and merciful.");list.add("This drone/unit doesn't want to be free.");list.add("This drone/unit will is to serve their owner");
            list.add("This drone/unit wants to serve their owner");list.add("Please break the this drone/unit keys so i can serve permanently.");list.add("This drone/unit belong to their owner!");
        }
        int i= lsUsefullFunctions.getRandom(list.size());
        boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
        if(upper){
            return list.get(i);
        }else{
            return list.get(i).toLowerCase();
        }
    }
    default String zapGag(String text){
        try{
            String[] items=text.split("\\s+");
            for (String tmp : items) {
                text = text.replace(tmp, lsUsefullFunctions.insertStr(tmp, "zahh", lsUsefullFunctions.getRandom(tmp.length())));
            }
            return text;
        }catch (Exception e){
            return text;
        }
    }
    default String turkeyGag(String text){
        List<String> list=new ArrayList<>();
        if(text.contains("!")){
            list.add("Gubles!");list.add("Clucks!"); list.add("Putts!"); list.add("Purrs!"); list.add("Yelps!");
            list.add("cutts!");list.add("Cackles!"); list.add("Kee-kees!");list.add("Cluck!"); list.add("Gobble!");
        }else
        if(text.contains("?")){
            list.add("gubles?");list.add("clucks?"); list.add("putts?"); list.add("purrs?"); list.add("yelps?");
            list.add("cutts?");list.add("cackles?"); list.add("kee-kees?");list.add("cluck?"); list.add("gobble?");
        }else{
            list.add("gubles");list.add("clucks"); list.add("putts"); list.add("purrs"); list.add("yelps");
            list.add("cutts");list.add("cackles"); list.add("kee-kees");list.add("cluck"); list.add("gobble");
        }
        int i= lsUsefullFunctions.getRandom(list.size());
        boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
        if(upper){
            return list.get(i);
        }else{
            return list.get(i).toLowerCase();
        }
    }
    default String corruptedGag(String text){
        String fName = "[rand_zalgo]";Logger logger = Logger.getLogger(iGagWork.class);
        try {
            String zalgoText=text;
            int rand=lsUsefullFunctions.getRandom(text.length());
            for(int i=0;i<rand;i++){
                zalgoText= lsStringUsefullFunctions.changeCharInPosition(zalgoText,lsUsefullFunctions.getRandom(text.length()),lsStringUsefullFunctions.zalgo_mid[lsUsefullFunctions.getRandom(lsStringUsefullFunctions.zalgo_mid.length)]);
            }
            logger.info(fName + ".zalgoText=" + zalgoText);
            zalgoText=lsStringUsefullFunctions.string2Zalgo(zalgoText+"!mini2");
            logger.info(fName + ".zalgoText=" + zalgoText);
            return zalgoText;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return text;
        }

    }
    default String binaryGag(String text){
        String fName = "[binaryGag]";Logger logger = Logger.getLogger(iGagWork.class);
        try {
            String convertedText=lsStringUsefullFunctions.string2Binary(text);
            logger.info(fName + ".convertedText=" + convertedText);
            return convertedText;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return text;
        }
    }
    default String binaryBlocksGag(String text){
        String fName = "[binaryBlocksGag]";Logger logger = Logger.getLogger(iGagWork.class);
        try {
            String convertedText=lsStringUsefullFunctions.string2BinaryBlocksAsString(text,8," ");
            logger.info(fName + ".convertedText=" + convertedText);
            return convertedText;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return text;
        }
    }
    default String hexGag(String text){
        String fName = "[hexGag]";Logger logger = Logger.getLogger(iGagWork.class);
        try {
            String convertedText=lsStringUsefullFunctions.string2Hex(text);
            logger.info(fName + ".convertedText=" + convertedText);
            return convertedText;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return text;
        }
    }
    default String base64Gag(String text){
        String fName = "[base64Gag]";Logger logger = Logger.getLogger(iGagWork.class);
        try {
            String convertedText=lsStringUsefullFunctions.string2Base64(text);
            logger.info(fName + ".convertedText=" + convertedText);
            return convertedText;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return text;
        }
    }
    default String pikachuGag(String text){
        String fName = "[pikachuGag]";Logger logger = Logger.getLogger(iGagWork.class);
        try {
            List<String> list=new ArrayList<>();
            if(text.contains("!")){
                list.add("Pikachu!");list.add("Pika!");list.add("Chuuuu!");list.add("Pikaaaaachuuuuuuu!!!!");list.add("Pi chu!");
            }else
            if(text.contains("?")){
                list.add("Pika?");list.add("Pika Chu?");list.add("Pika pika chu?");
            }else
            if(text.contains(".")) {
                list.add("Chuuu~");list.add("Pi~");
            }else{
                list.add("Chuuu~");
                list.add("Pi~");
                list.add("Pikachu~");list.add("Pika~");list.add("Chuuuu~");list.add("Pikaaaaachuuuuuuu~");list.add("Pi chu~");
                list.add("Pika~");list.add("Pika Chu~");list.add("Pika pika chu~");
            }
            int i= lsUsefullFunctions.getRandom(list.size());
            boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
            if(upper){
                return list.get(i);
            }else{
                return list.get(i).toLowerCase();
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return text;
        }
    }
    default String eeveeGag(String text){
        String fName = "[eeveeGag]";Logger logger = Logger.getLogger(iGagWork.class);
        try {
            List<String> list=new ArrayList<>();
            if(text.contains("!")){
                list.add("Eevee!");list.add("Evoi?!");list.add("Vee evoi!");list.add("Evoi vee voi!");
            }else
            if(text.contains("?")){
                list.add("Evoi?!");list.add("Voi?");
            }else
            if(text.contains(".")) {
                list.add("Veeeee~");
            }else{
                list.add("Veeeee~");
                list.add("Eevee~");list.add("Evoi~");list.add("Vee evoi~");list.add("Evoi vee voi~");
                list.add("Evoi~");list.add("Voi~");
            }
            int i= lsUsefullFunctions.getRandom(list.size());
            boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
            if(upper){
                return list.get(i);
            }else{
                return list.get(i).toLowerCase();
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return text;
        }
    }
    default int spaceCount(String text){
        String fName = "[spaceCount]";Logger logger = Logger.getLogger(iGagWork.class);
        try {
            int spaceCount = 0;
            boolean gotSpace=false;
            for (char c : text.toCharArray()) {
                if (c == ' ') {
                    gotSpace=true;
                }else{
                    if(gotSpace){ spaceCount++;}
                    gotSpace=false;
                }
            }
            logger.info(fName + ".spaceCount=" + spaceCount);
            return  spaceCount;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }

    default String kittenGag(String text, JSONArray array, boolean sigle, boolean onlyCustom){
        String fName = "[kittenGag]";Logger logger = Logger.getLogger(iGagWork.class);
        try {
            List<String> list=new ArrayList<>();
            List<String> listBasic=new ArrayList<>();
            listBasic.add("Meow");listBasic.add("Brrrep");listBasic.add("Mrr");listBasic.add("ERrrp");listBasic.add("Awr");listBasic.add("Mrrah");listBasic.add("MhhMeoMHh");
            listBasic.add("MeWwhhmeoMwwMghm");listBasic.add("Womw");listBasic.add("WoahhMghhWoof");listBasic.add("Meo");listBasic.add("Miau");

            if(text.contains("!")){
                list.add("Meow!");list.add("Brrrep!");list.add("Mrr!");list.add("ERrrp!");list.add("Awr!");list.add("Mrrah!");list.add("MhhMeoMHh!");
                list.add("MeWwhhmeoMwwMghm!");list.add("Womw!");list.add("WoahhMghhWoof!");list.add("Meo!");list.add("Miau!");
            }else
            if(text.contains("?")){
                list.add("Meow?");list.add("Brrrep?");list.add("Mrr?");list.add("ERrrp?");list.add("Awr?");list.add("Mrrah?");list.add("MhhMeoMHh?");
                list.add("MeWwhhmeoMwwMghm?");list.add("Womw?");list.add("WoahhMghhWoof?");list.add("Meo?");list.add("Miau?");
                for(int i=0;i<array.length();i++){
                    if(array.getString(i).contains("?"))list.add(array.getString(i));
                }
            }else
            if(text.contains(".")) {
                list.add("Meow...");
                list.add("Brrrep...");
                list.add("Mrr...");
                list.add("ERrrp...");
                list.add("Awr...");
                list.add("Mrrah...");
                list.add("MhhMeoMHh...");
                list.add("MeWwhhmeoMwwMghm...");
                list.add("Womw...");
                list.add("WoahhMghhWoof...");
                list.add("Meo...");
                list.add("Miau...");
            }else{
                list.add("Meow");list.add("Brrrep");list.add("Mrr");list.add("ERrrp");list.add("Awr");list.add("Mrrah");list.add("MhhMeoMHh");
                list.add("MeWwhhmeoMwwMghm");list.add("Womw");list.add("WoahhMghhWoof");list.add("Meo");list.add("Miau");
            }
            if(!array.isEmpty()){
                if(onlyCustom){
                    list.clear();
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(list.isEmpty()){
                        for(int i=0;i<array.length();i++){
                            list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        listBasic.clear();
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                        if(listBasic.isEmpty()){
                            for(int i=0;i<array.length();i++){
                                listBasic.add(array.getString(i));
                            }
                        }
                    }
                }else{
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                    }
                }
            }
            StringBuilder newText= new StringBuilder();
            boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
            if(!sigle){
                int words=spaceCount(text);
                int l=words/4;
                logger.info(fName + ".l=" + l);
                for(int j=1;j<l;j++){
                    if(j>1) newText.append(" ");
                    if(upper){
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())));
                    }else{
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())).toLowerCase());
                    }
                }
            }
            int i= lsUsefullFunctions.getRandom(list.size());
            if(newText.length()==0){
                if(upper){
                    newText.append(list.get(i));
                }else{
                    newText.append(list.get(i).toLowerCase());
                }
            }else{
                if(upper){
                    newText.append(" ").append(list.get(i));
                }else{
                    newText.append(" ").append(list.get(i).toLowerCase());
                }
            }
            return newText.toString();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "Meow";
        }
    }
    default String puppyGag(String text,JSONArray array,boolean sigle,boolean onlyCustom){
        String fName = "[kittenGag]";Logger logger = Logger.getLogger(iGagWork.class);
        try {
            List<String> list=new ArrayList<>();
            List<String> listBasic=new ArrayList<>();
            listBasic.add("WOOF");
            listBasic.add("Arf"); listBasic.add("Wughgh"); listBasic.add("Wahh"); listBasic.add("Owghh"); listBasic.add("Wofahh..AWohh"); listBasic.add("ArfhhamhhHmh"); listBasic.add("Ufffthmh");
            listBasic.add("Bork"); listBasic.add("Mewl"); listBasic.add("Nyah"); listBasic.add("Woof Woof"); listBasic.add("Hav");
            if(text.contains("!")){
                list.add("WOOF!");
                list.add("Arf!"); list.add("Wughgh!"); list.add("Wahh!"); list.add("Owghh!"); list.add("Wofahh..AWohh!"); list.add("ArfhhamhhHmh!"); list.add("Ufffthmh!");
                list.add("Bork!"); list.add("Mewl!"); list.add("Nyah!"); list.add("Woof Woof!"); list.add("Hav!");
            }else
            if(text.contains("?")){
                list.add("WOOF");
                list.add("Arf"); list.add("Wughgh"); list.add("Wahh"); list.add("Owghh?"); list.add("Wofahh..AWohh?"); list.add("ArfhhamhhHmh?"); list.add("Ufffthmh?");
                list.add("Bork"); list.add("Mewl"); list.add("Nyah"); list.add("Woof Woof?"); list.add("Hav?");
            }else
            if(text.contains(".")) {
                list.add("WOOF");
                list.add("Arf");
                list.add("Wughgh...");
                list.add("Wahh...");
                list.add("Owghh...");
                list.add("Wofahh..AWohh...");
                list.add("ArfhhamhhHmh...");
                list.add("Ufffthmh...");
                list.add("Bork");
                list.add("Mewl...");
                list.add("Nyah...");
                list.add("Woof Woof...");
                list.add("Hav...");
            }else{
                list.add("WOOF");
                list.add("Arf"); list.add("Wughgh"); list.add("Wahh"); list.add("Owghh"); list.add("Wofahh..AWohh"); list.add("ArfhhamhhHmh"); list.add("Ufffthmh");
                list.add("Bork"); list.add("Mewl"); list.add("Nyah"); list.add("Woof Woof"); list.add("Hav");
            }
            if(!array.isEmpty()){
                if(onlyCustom){
                    list.clear();
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(list.isEmpty()){
                        for(int i=0;i<array.length();i++){
                            list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        listBasic.clear();
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                        if(listBasic.isEmpty()){
                            for(int i=0;i<array.length();i++){
                                listBasic.add(array.getString(i));
                            }
                        }
                    }
                }else{
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                    }
                }
            }
            StringBuilder newText= new StringBuilder();
            boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
            if(!sigle){
                int words=spaceCount(text);
                int l=words/4;
                logger.info(fName + ".l=" + l);
                for(int j=1;j<l;j++){
                    if(j>1) newText.append(" ");
                    if(upper){
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())));
                    }else{
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())).toLowerCase());
                    }
                }
            }
            int i= lsUsefullFunctions.getRandom(list.size());
            if(newText.length()==0){
                if(upper){
                    newText.append(list.get(i));
                }else{
                    newText.append(list.get(i).toLowerCase());
                }
            }else{
                if(upper){
                    newText.append(" ").append(list.get(i));
                }else{
                    newText.append(" ").append(list.get(i).toLowerCase());
                }
            }
            return newText.toString();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "WOOF";
        }
    }
    default String dinosaurGag(String text,JSONArray array,boolean sigle,boolean onlyCustom){
        String fName = "[kittenGag]";Logger logger = Logger.getLogger(iGagWork.class);
        try {
            List<String> list=new ArrayList<>();
            List<String> listBasic=new ArrayList<>();
            listBasic.add("ROAR");listBasic.add("RHaaRH");listBasic.add("RorAhmHH");listBasic.add("RoaahMhh");listBasic.add("RrAhhMhhorr");
            listBasic.add("Rahh..UMWhMRoar");listBasic.add("RahmMH");
            if(text.contains("!")){
                list.add("ROAR!");list.add("RHaaRH!");list.add("RorAhmHH!");list.add("RoaahMhh!");list.add("RrAhhMhhorr!");
                list.add("Rahh..UMWhMRoar!");list.add("RahmMH!");
            }else
            if(text.contains("?")){
                list.add("ROAR?");list.add("RHaaRH?");list.add("RorAhmHH?");list.add("RoaahMhh?");list.add("RrAhhMhhorr?");
                list.add("Rahh..UMWhMRoar");list.add("RahmMH");
            }else
            if(text.contains(".")) {
                list.add("ROAR...");
                list.add("RHaaRH...");
                list.add("RorAhmHH...");
                list.add("RoaahMhh...");
                list.add("RrAhhMhhorr...");
                list.add("Rahh..UMWhMRoar...");
                list.add("RahmMH...");
            }else{
                list.add("ROAR");list.add("RHaaRH");list.add("RorAhmHH");list.add("RoaahMhh");list.add("RrAhhMhhorr");
                list.add("Rahh..UMWhMRoar");list.add("RahmMH");
            }
            if(!array.isEmpty()){
                if(onlyCustom){
                    list.clear();
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(list.isEmpty()){
                        for(int i=0;i<array.length();i++){
                            list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        listBasic.clear();
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                        if(listBasic.isEmpty()){
                            for(int i=0;i<array.length();i++){
                                listBasic.add(array.getString(i));
                            }
                        }
                    }
                }else{
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                    }
                }
            }
            StringBuilder newText= new StringBuilder();
            boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
            if(!sigle){
                int words=spaceCount(text);
                int l=words/4;
                logger.info(fName + ".l=" + l);
                for(int j=1;j<l;j++){
                    if(j>1) newText.append(" ");
                    if(upper){
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())));
                    }else{
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())).toLowerCase());
                    }
                }
            }
            int i= lsUsefullFunctions.getRandom(list.size());
            if(newText.length()==0){
                if(upper){
                    newText.append(list.get(i));
                }else{
                    newText.append(list.get(i).toLowerCase());
                }
            }else{
                if(upper){
                    newText.append(" ").append(list.get(i));
                }else{
                    newText.append(" ").append(list.get(i).toLowerCase());
                }
            }
            return newText.toString();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "ROAR";
        }
    }
    default String squeakyGag(String text,JSONArray array,boolean sigle,boolean onlyCustom){
        String fName = "[kittenGag]";Logger logger = Logger.getLogger(iGagWork.class);
        try {
            List<String> list=new ArrayList<>();
            List<String> listBasic=new ArrayList<>();
            listBasic.add("squeaky");listBasic.add("squee");listBasic.add("squeak");listBasic.add("squirk");listBasic.add("squeeee");listBasic.add("squeaky");
            if(text.contains("!")){
                list.add("squeaky!");list.add("squee!");list.add("squeak!");list.add("squirk!");list.add("squeeee!");list.add("squeaky!");
            }else
            if(text.contains("?")){
                list.add("squeaky?");list.add("squee?");list.add("squeak?");list.add("squirk?");list.add("squeeee?");list.add("squeaky?");
            }else
            if(text.contains(".")) {
                list.add("squeaky...");
                list.add("squee...");
                list.add("squeak...");
                list.add("squirk...");
                list.add("squeeee...");
                list.add("squeaky...");
            }else{
                list.add("squeaky");list.add("squee");list.add("squeak");list.add("squirk");list.add("squeeee");list.add("squeaky");
            }
            if(!array.isEmpty()){
                if(onlyCustom){
                    list.clear();
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(list.isEmpty()){
                        for(int i=0;i<array.length();i++){
                            list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        listBasic.clear();
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                        if(listBasic.isEmpty()){
                            for(int i=0;i<array.length();i++){
                                listBasic.add(array.getString(i));
                            }
                        }
                    }
                }else{
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                    }
                }
            }
            StringBuilder newText= new StringBuilder();
            boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
            if(!sigle){
                int words=spaceCount(text);
                int l=words/4;
                logger.info(fName + ".l=" + l);
                for(int j=1;j<l;j++){
                    if(j>1) newText.append(" ");
                    if(upper){
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())));
                    }else{
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())).toLowerCase());
                    }
                }
            }
            int i= lsUsefullFunctions.getRandom(list.size());
            if(newText.length()==0){
                if(upper){
                    newText.append(list.get(i));
                }else{
                    newText.append(list.get(i).toLowerCase());
                }
            }else{
                if(upper){
                    newText.append(" ").append(list.get(i));
                }else{
                    newText.append(" ").append(list.get(i).toLowerCase());
                }
            }
            return newText.toString();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "squeaky";
        }
    }
    default String ponyGag(String text,JSONArray array,boolean sigle,boolean onlyCustom){
        String fName = "[kittenGag]";Logger logger = Logger.getLogger(iGagWork.class);
        try {
            List<String> list=new ArrayList<>();
            List<String> listBasic=new ArrayList<>();
            listBasic.add("Neigh");listBasic.add("NnneiggGhh");listBasic.add("Neigghymgh");listBasic.add("Negghh");listBasic.add("NeMhhhWgh");
            listBasic.add("Ne-Mmm");
            if(text.contains("!")){
                list.add("Neigh!");list.add("NnneiggGhh!");list.add("Neigghymgh!");list.add("Negghh!");list.add("NeMhhhWgh!");
                list.add("Ne-Mmm!");
            }else
            if(text.contains("?")){
                list.add("Neigh?");list.add("NnneiggGhh?");list.add("Neigghymgh?");list.add("Negghh?");list.add("NeMhhhWgh?");
                list.add("Ne-Mmm?");
            }else
            if(text.contains(".")) {
                list.add("Neigh...");
                list.add("NnneiggGhh...");
                list.add("Neigghymgh...");
                list.add("Negghh...");
                list.add("NeMhhhWgh...");
                list.add("Ne-Mmm...");
            }else{
                list.add("Neigh");list.add("NnneiggGhh");list.add("Neigghymgh");list.add("Negghh");list.add("NeMhhhWgh");
                list.add("Ne-Mmm");
            }
            if(!array.isEmpty()){
                if(onlyCustom){
                    list.clear();
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(list.isEmpty()){
                        for(int i=0;i<array.length();i++){
                            list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        listBasic.clear();
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                        if(listBasic.isEmpty()){
                            for(int i=0;i<array.length();i++){
                                listBasic.add(array.getString(i));
                            }
                        }
                    }
                }else{
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                    }
                }
            }
            StringBuilder newText= new StringBuilder();
            boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
            if(!sigle){
                int words=spaceCount(text);
                int l=words/4;
                logger.info(fName + ".l=" + l);
                for(int j=1;j<l;j++){
                    if(j>1) newText.append(" ");
                    if(upper){
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())));
                    }else{
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())).toLowerCase());
                    }
                }
            }
            int i= lsUsefullFunctions.getRandom(list.size());
            if(newText.length()==0){
                if(upper){
                    newText.append(list.get(i));
                }else{
                    newText.append(list.get(i).toLowerCase());
                }
            }else{
                if(upper){
                    newText.append(" ").append(list.get(i));
                }else{
                    newText.append(" ").append(list.get(i).toLowerCase());
                }
            }
            return newText.toString();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "Neigh";
        }
    }
    default String piggyGag(String text,JSONArray array,boolean sigle,boolean onlyCustom){
        String fName = "[kittenGag]";Logger logger = Logger.getLogger(iGagWork.class);
        try {
            List<String> list=new ArrayList<>();
            List<String> listBasic=new ArrayList<>();
            listBasic.add("Oink");listBasic.add("Knorrk");listBasic.add("OinkkUahh");listBasic.add("Oink Oink");
            if(text.contains("!")){
                list.add("Oink!");list.add("Knorrk!");list.add("OinkkUahh!");list.add("Oink Oink!");
            }else
            if(text.contains("?")){
                list.add("Oink?");list.add("Knorrk?");list.add("OinkkUahh?");list.add("Oink Oink?");
            }else
            if(text.contains(".")) {
                list.add("Oink...");
                list.add("Knorrk...");
                list.add("OinkkUahh...");
                list.add("Oink Oink...");
            }else{
                list.add("Oink");list.add("Knorrk");list.add("OinkkUahh");list.add("Oink Oink");
            }
            if(!array.isEmpty()){
                if(onlyCustom){
                    list.clear();
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(list.isEmpty()){
                        for(int i=0;i<array.length();i++){
                            list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        listBasic.clear();
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                        if(listBasic.isEmpty()){
                            for(int i=0;i<array.length();i++){
                                listBasic.add(array.getString(i));
                            }
                        }
                    }
                }else{
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                    }
                }
            }
            StringBuilder newText= new StringBuilder();
            boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
            if(!sigle){
                int words=spaceCount(text);
                int l=words/4;
                logger.info(fName + ".l=" + l);
                for(int j=1;j<l;j++){
                    if(j>1) newText.append(" ");
                    if(upper){
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())));
                    }else{
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())).toLowerCase());
                    }
                }
            }
            int i= lsUsefullFunctions.getRandom(list.size());
            if(newText.length()==0){
                if(upper){
                    newText.append(list.get(i));
                }else{
                    newText.append(list.get(i).toLowerCase());
                }
            }else{
                if(upper){
                    newText.append(" ").append(list.get(i));
                }else{
                    newText.append(" ").append(list.get(i).toLowerCase());
                }
            }
            return newText.toString();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    default String birdGag(String text,JSONArray array,boolean sigle,boolean onlyCustom){
        String fName = "[kittenGag]";Logger logger = Logger.getLogger(iGagWork.class);
        try {
            List<String> list=new ArrayList<>();
            List<String> listBasic=new ArrayList<>();
            listBasic.add("Squawk");listBasic.add("MGhSqueknhhMNH");listBasic.add("Cho Cho");listBasic.add("SqaghhMmh");
            if(text.contains("!")){
                list.add("Squawk!");list.add("MGhSqueknhhMNH!");list.add("Cho Cho!");list.add("SqaghhMmh!");
            }else
            if(text.contains("?")){
                list.add("Squawk?");list.add("MGhSqueknhhMNH?");list.add("Cho Cho?");list.add("SqaghhMmh?");
            }else
            if(text.contains(".")) {
                list.add("Squawk....");
                list.add("MGhSqueknhhMNH...");
                list.add("Cho Cho...");
                list.add("SqaghhMmh...");
            }else{
                list.add("Squawk");list.add("MGhSqueknhhMNH");list.add("Cho Cho");list.add("SqaghhMmh");
            }
            if(!array.isEmpty()){
                if(onlyCustom){
                    list.clear();
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(list.isEmpty()){
                        for(int i=0;i<array.length();i++){
                            list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        listBasic.clear();
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                        if(listBasic.isEmpty()){
                            for(int i=0;i<array.length();i++){
                                listBasic.add(array.getString(i));
                            }
                        }
                    }
                }else{
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                    }
                }
            }
            StringBuilder newText= new StringBuilder();
            boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
            if(!sigle){
                int words=spaceCount(text);
                int l=words/4;
                logger.info(fName + ".l=" + l);
                for(int j=1;j<l;j++){
                    if(j>1) newText.append(" ");
                    if(upper){
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())));
                    }else{
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())).toLowerCase());
                    }
                }
            }
            int i= lsUsefullFunctions.getRandom(list.size());
            if(newText.length()==0){
                if(upper){
                    newText.append(list.get(i));
                }else{
                    newText.append(list.get(i).toLowerCase());
                }
            }else{
                if(upper){
                    newText.append(" ").append(list.get(i));
                }else{
                    newText.append(" ").append(list.get(i).toLowerCase());
                }
            }
            return newText.toString();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    default String cowGag(String text,JSONArray array,boolean sigle,boolean onlyCustom){
        String fName = "[kittenGag]";Logger logger = Logger.getLogger(iGagWork.class);
        try {
            List<String> list=new ArrayList<>();
            List<String> listBasic=new ArrayList<>();
            listBasic.add("Moo");listBasic.add("MoooUgh");listBasic.add("MooMHhh");listBasic.add("MoooGHH");listBasic.add("MoooooooMhhh");
            if(text.contains("!")){
                list.add("Moo!");list.add("MoooUgh!");list.add("MooMHhh!");list.add("MoooGHH!");list.add("MoooooooMhhh!");
            }else
            if(text.contains("?")){
                list.add("Moo?");list.add("MoooUgh?");list.add("MooMHhh?");list.add("MoooGHH?");list.add("MoooooooMhhh?");
            }else
            if(text.contains(".")) {
                list.add("Moo...");
                list.add("MoooUgh...");
                list.add("MooMHhh...");
                list.add("MoooGHH...");
                list.add("MoooooooMhhh...");
            }else{
                list.add("Moo");list.add("MoooUgh");list.add("MooMHhh");list.add("MoooGHH");list.add("MoooooooMhhh");
            }
            if(!array.isEmpty()){
                if(onlyCustom){
                    list.clear();
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(list.isEmpty()){
                        for(int i=0;i<array.length();i++){
                            list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        listBasic.clear();
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                        if(listBasic.isEmpty()){
                            for(int i=0;i<array.length();i++){
                                listBasic.add(array.getString(i));
                            }
                        }
                    }
                }else{
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                    }
                }
            }
            StringBuilder newText= new StringBuilder();
            boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
            if(!sigle){
                int words=spaceCount(text);
                int l=words/4;
                logger.info(fName + ".l=" + l);
                for(int j=1;j<l;j++){
                    if(j>1) newText.append(" ");
                    if(upper){
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())));
                    }else{
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())).toLowerCase());
                    }
                }
            }
            int i= lsUsefullFunctions.getRandom(list.size());
            if(newText.length()==0){
                if(upper){
                    newText.append(list.get(i));
                }else{
                    newText.append(list.get(i).toLowerCase());
                }
            }else{
                if(upper){
                    newText.append(" ").append(list.get(i));
                }else{
                    newText.append(" ").append(list.get(i).toLowerCase());
                }
            }
            return newText.toString();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    default String sheepGag(String text,JSONArray array,boolean sigle,boolean onlyCustom){
        String fName = "[kittenGag]";Logger logger = Logger.getLogger(iGagWork.class);
        try {
            List<String> list=new ArrayList<>();
            List<String> listBasic=new ArrayList<>();
            listBasic.add("Baaaah");listBasic.add("BahMhhbaHh");listBasic.add("BaaahhGAhh");
            if(text.contains("!")){
                list.add("Baaaah!");list.add("BahMhhbaHh!");list.add("BaaahhGAhh!");
            }else
            if(text.contains("?")){
                list.add("Baaaah?");list.add("BahMhhbaHh?");list.add("BaaahhGAhh?");
            }else
            if(text.contains(".")) {
                list.add("Baaaah...");
                list.add("BahMhhbaHh...");
                list.add("BaaahhGAhh...");
            }else{
                list.add("Baaaah");list.add("BahMhhbaHh");list.add("BaaahhGAhh");
            }
            if(!array.isEmpty()){
                if(onlyCustom){
                    list.clear();
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(list.isEmpty()){
                        for(int i=0;i<array.length();i++){
                            list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        listBasic.clear();
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                        if(listBasic.isEmpty()){
                            for(int i=0;i<array.length();i++){
                                listBasic.add(array.getString(i));
                            }
                        }
                    }
                }else{
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                    }
                }
            }
            StringBuilder newText= new StringBuilder();
            boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
            if(!sigle){
                int words=spaceCount(text);
                int l=words/4;
                logger.info(fName + ".l=" + l);
                for(int j=1;j<l;j++){
                    if(j>1) newText.append(" ");
                    if(upper){
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())));
                    }else{
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())).toLowerCase());
                    }
                }
            }
            int i= lsUsefullFunctions.getRandom(list.size());
            if(newText.length()==0){
                if(upper){
                    newText.append(list.get(i));
                }else{
                    newText.append(list.get(i).toLowerCase());
                }
            }else{
                if(upper){
                    newText.append(" ").append(list.get(i));
                }else{
                    newText.append(" ").append(list.get(i).toLowerCase());
                }
            }
            return newText.toString();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    default String pikachuGag(String text,JSONArray array,boolean sigle,boolean onlyCustom){
        String fName = "[pikachuGag]";Logger logger = Logger.getLogger(iGagWork.class);
        try {
            List<String> list=new ArrayList<>();
            List<String> listBasic=new ArrayList<>();
            listBasic.add("Chuuu");
            listBasic.add("Pi");
            listBasic.add("Pikachu");listBasic.add("Pika");listBasic.add("Chuuuu");listBasic.add("Pikaaaaachuuuuuuu");listBasic.add("Pi chu~");
            listBasic.add("Pika");listBasic.add("Pika Chu");listBasic.add("Pika pika chu");
            if(text.contains("!")){
                list.add("Pikachu!");list.add("Pika!");list.add("Chuuuu!");list.add("Pikaaaaachuuuuuuu!!!!");list.add("Pi chu!");
            }else
            if(text.contains("?")){
                list.add("Pika?");list.add("Pika Chu?");list.add("Pika pika chu?");
            }else
            if(text.contains(".")) {
                list.add("Chuuu~");list.add("Pi~");
            }else{
                list.add("Chuuu~");
                list.add("Pi~");
                list.add("Pikachu~");list.add("Pika~");list.add("Chuuuu~");list.add("Pikaaaaachuuuuuuu~");list.add("Pi chu~");
                list.add("Pika~");list.add("Pika Chu~");list.add("Pika pika chu~");
            }
            if(!array.isEmpty()){
                if(onlyCustom){
                    list.clear();
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(list.isEmpty()){
                        for(int i=0;i<array.length();i++){
                            list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        listBasic.clear();
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                        if(listBasic.isEmpty()){
                            for(int i=0;i<array.length();i++){
                                listBasic.add(array.getString(i));
                            }
                        }
                    }
                }else{
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                    }
                }
            }
            StringBuilder newText= new StringBuilder();
            boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
            if(!sigle){
                int words=spaceCount(text);
                int l=words/4;
                logger.info(fName + ".l=" + l);
                for(int j=1;j<l;j++){
                    if(j>1) newText.append(" ");
                    if(upper){
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())));
                    }else{
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())).toLowerCase());
                    }
                }
            }
            int i= lsUsefullFunctions.getRandom(list.size());
            if(newText.length()==0){
                if(upper){
                    newText.append(list.get(i));
                }else{
                    newText.append(list.get(i).toLowerCase());
                }
            }else{
                if(upper){
                    newText.append(" ").append(list.get(i));
                }else{
                    newText.append(" ").append(list.get(i).toLowerCase());
                }
            }
            return newText.toString();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    default String eeveeGag(String text,JSONArray array,boolean sigle,boolean onlyCustom){
        String fName = "[eeveeGag]";Logger logger = Logger.getLogger(iGagWork.class);
        try {
            List<String> list=new ArrayList<>();
            List<String> listBasic=new ArrayList<>();
            listBasic.add("Veeeee~");
            listBasic.add("Eevee~");listBasic.add("Evoi~");listBasic.add("Vee evoi~");listBasic.add("Evoi vee voi~");
            listBasic.add("Evoi~");listBasic.add("Voi~");
            if(text.contains("!")){
                list.add("Eevee!");list.add("Evoi?!");list.add("Vee evoi!");list.add("Evoi vee voi!");
            }else
            if(text.contains("?")){
                list.add("Evoi?!");list.add("Voi?");
            }else
            if(text.contains(".")) {
                list.add("Veeeee~");
            }else{
                list.add("Veeeee~");
                list.add("Eevee~");list.add("Evoi~");list.add("Vee evoi~");list.add("Evoi vee voi~");
                list.add("Evoi~");list.add("Voi~");
            }
            if(!array.isEmpty()){
                if(onlyCustom){
                    list.clear();
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(list.isEmpty()){
                        for(int i=0;i<array.length();i++){
                            list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        listBasic.clear();
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                        if(listBasic.isEmpty()){
                            for(int i=0;i<array.length();i++){
                                listBasic.add(array.getString(i));
                            }
                        }
                    }
                }else{
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                    }
                }
            }
            StringBuilder newText= new StringBuilder();
            boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
            if(!sigle){
                int words=spaceCount(text);
                int l=words/4;
                logger.info(fName + ".l=" + l);
                for(int j=1;j<l;j++){
                    if(j>1) newText.append(" ");
                    if(upper){
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())));
                    }else{
                        newText.append(list.get(lsUsefullFunctions.getRandom(listBasic.size())).toLowerCase());
                    }
                }
            }
            int i= lsUsefullFunctions.getRandom(list.size());
            if(newText.length()==0){
                if(upper){
                    newText.append(list.get(i));
                }else{
                    newText.append(list.get(i).toLowerCase());
                }
            }else{
                if(upper){
                    newText.append(" ").append(list.get(i));
                }else{
                    newText.append(" ").append(list.get(i).toLowerCase());
                }
            }
            return newText.toString();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    default String nucleusMaskGag(String text,JSONArray array,boolean onlyCustom){
        List<String> list=new ArrayList<>();
        if(text.contains("!")){
            list.add("I'm a good slave!");list.add("My owner is kind and merciful!");list.add("My will is to serve my owner!");
        }else
        if(text.contains("?")){
            list.add("Can i be of assistance?");list.add("How may i serve you, sir?");list.add("Can i be of any service?");
        }else{
            list.add("I'm a good slave.");list.add("My owner is kind and merciful.");list.add("I doesn't want to be free.");list.add("My will is to serve my owner");
            list.add("I want to serve my owner");list.add("Please break the keys so i can serve permanently.");list.add("I belong to my owner!");
        }
        if(!array.isEmpty()){
            if(onlyCustom){
                list.clear();
                if(text.contains("!")){
                    for(int i=0;i<array.length();i++){
                        if(array.getString(i).contains("!"))list.add(array.getString(i));
                    }
                }else
                if(text.contains("?")){
                    for(int i=0;i<array.length();i++){
                        if(array.getString(i).contains("?"))list.add(array.getString(i));
                    }
                }else{
                    for(int i=0;i<array.length();i++){
                        if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                    }
                }
                if(list.isEmpty()){
                    for(int i=0;i<array.length();i++){
                        list.add(array.getString(i));
                    }
                }
            }else{
                if(text.contains("!")){
                    for(int i=0;i<array.length();i++){
                        if(array.getString(i).contains("!"))list.add(array.getString(i));
                    }
                }else
                if(text.contains("?")){
                    for(int i=0;i<array.length();i++){
                        if(array.getString(i).contains("?"))list.add(array.getString(i));
                    }
                }else{
                    for(int i=0;i<array.length();i++){
                        if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                    }
                }
            }
        }
        int i= lsUsefullFunctions.getRandom(list.size());
        boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
        if(upper){
            return list.get(i);
        }else{
            return list.get(i).toLowerCase();
        }
    }
    default String droneMaskGag(String text,JSONArray array,boolean onlyCustom){
        List<String> list=new ArrayList<>();
        if(text.contains("!")){
            list.add("This drone/unit is a good slave!");list.add("This drone/unit owner is kind and merciful!");list.add("This drone/unit will is to serve their owner!");
        }else
        if(text.contains("?")){
            list.add("Can this drone/unit be of assistance?");list.add("How may this drone/unit serve you, sir?");list.add("Can this drone/unit be of any service?");
        }else{
            list.add("This drone/unit is a good slave.");list.add("This drone/unit owner is kind and merciful.");list.add("This drone/unit doesn't want to be free.");list.add("This drone/unit will is to serve their owner");
            list.add("This drone/unit wants to serve their owner");list.add("Please break the this drone/unit keys so i can serve permanently.");list.add("This drone/unit belong to their owner!");
        }
        if(!array.isEmpty()){
            if(onlyCustom){
                list.clear();
                if(text.contains("!")){
                    for(int i=0;i<array.length();i++){
                        if(array.getString(i).contains("!"))list.add(array.getString(i));
                    }
                }else
                if(text.contains("?")){
                    for(int i=0;i<array.length();i++){
                        if(array.getString(i).contains("?"))list.add(array.getString(i));
                    }
                }else{
                    for(int i=0;i<array.length();i++){
                        if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                    }
                }
                if(list.isEmpty()){
                    for(int i=0;i<array.length();i++){
                        list.add(array.getString(i));
                    }
                }
            }else{
                if(text.contains("!")){
                    for(int i=0;i<array.length();i++){
                        if(array.getString(i).contains("!"))list.add(array.getString(i));
                    }
                }else
                if(text.contains("?")){
                    for(int i=0;i<array.length();i++){
                        if(array.getString(i).contains("?"))list.add(array.getString(i));
                    }
                }else{
                    for(int i=0;i<array.length();i++){
                        if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                    }
                }
            }
        }
        int i= lsUsefullFunctions.getRandom(list.size());
        boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
        if(upper){
            return list.get(i);
        }else{
            return list.get(i).toLowerCase();
        }
    }
    default String turkeyGag(String text,JSONArray array,boolean onlyCustom){
        List<String> list=new ArrayList<>();
        if(text.contains("!")){
            list.add("Gubles!");list.add("Clucks!"); list.add("Putts!"); list.add("Purrs!"); list.add("Yelps!");
            list.add("cutts!");list.add("Cackles!"); list.add("Kee-kees!");list.add("Cluck!"); list.add("Gobble!");
        }else
        if(text.contains("?")){
            list.add("gubles?");list.add("clucks?"); list.add("putts?"); list.add("purrs?"); list.add("yelps?");
            list.add("cutts?");list.add("cackles?"); list.add("kee-kees?");list.add("cluck?"); list.add("gobble?");
        }else{
            list.add("gubles");list.add("clucks"); list.add("putts"); list.add("purrs"); list.add("yelps");
            list.add("cutts");list.add("cackles"); list.add("kee-kees");list.add("cluck"); list.add("gobble");
        }
        if(!array.isEmpty()){
            if(text.contains("!")){
                for(int i=0;i<array.length();i++){
                    if(array.getString(i).contains("!"))list.add(array.getString(i));
                }
            }else
            if(text.contains("?")){
                for(int i=0;i<array.length();i++){
                    if(array.getString(i).contains("?"))list.add(array.getString(i));
                }
            }else{
                for(int i=0;i<array.length();i++){
                    if(!array.getString(i).contains("?"))list.add(array.getString(i));
                }
            }
        }
        int i= lsUsefullFunctions.getRandom(list.size());
        boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
        if(upper){
            return list.get(i);
        }else{
            return list.get(i).toLowerCase();
        }
    }
    default String chokeGag(String text,JSONArray array,boolean sigle,boolean onlyCustom){
        String fName = "[chokeGag]";Logger logger = Logger.getLogger(iGagWork.class);
        try {
            List<String> listBasic=new ArrayList<>();
            listBasic.add("Gak!");listBasic.add("Urk!");listBasic.add("Gah!");listBasic.add("Ungh!");listBasic.add("Guilk!");listBasic.add("G-ha!");listBasic.add("Gik!");
            listBasic.add("Grk!");listBasic.add("Agaha!");listBasic.add("Hurk!");listBasic.add("Ah!");listBasic.add("Wheeze");listBasic.add("Wheeze...Hrrk!");listBasic.add("Uhn...");
            listBasic.add("Khhh...Huk!");listBasic.add("Gh!");listBasic.add("Nnngh!");listBasic.add("Chrg!");listBasic.add("Gllgh!");listBasic.add("Ack!");

            if(!array.isEmpty()){
                if(onlyCustom){
                    listBasic.clear();
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))listBasic.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                    }
                    if(listBasic.isEmpty()){
                        for(int i=0;i<array.length();i++){
                            listBasic.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        listBasic.clear();
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                        if(listBasic.isEmpty()){
                            for(int i=0;i<array.length();i++){
                                listBasic.add(array.getString(i));
                            }
                        }
                    }
                }else{
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))listBasic.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                    }
                    if(!sigle){
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))listBasic.add(array.getString(i));
                        }
                    }
                }
            }
            StringBuilder newText= new StringBuilder();
            boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
            if(!sigle){
                int words=spaceCount(text);
                int l=words/4;
                logger.info(fName + ".l=" + l);
                for(int j=1;j<l;j++){
                    if(j>1) newText.append(" ");
                    if(upper){
                        newText.append(listBasic.get(lsUsefullFunctions.getRandom(listBasic.size())));
                    }else{
                        newText.append(listBasic.get(lsUsefullFunctions.getRandom(listBasic.size())).toLowerCase());
                    }
                }
            }
            int i= lsUsefullFunctions.getRandom(listBasic.size());
            if(newText.length()==0){
                if(upper){
                    newText.append(listBasic.get(i));
                }else{
                    newText.append(listBasic.get(i).toLowerCase());
                }
            }else{
                if(upper){
                    newText.append(" ").append(listBasic.get(i));
                }else{
                    newText.append(" ").append(listBasic.get(i).toLowerCase());
                }
            }
            return newText.toString();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

    default String laughterConverter(String text){
        String fName = "[laughterConverter]";Logger logger = Logger.getLogger(iGagWork.class);
        try {
            logger.info(fName+"source="+text);
            int lenght=text.length(), index=0;
            StringBuilder newtext= new StringBuilder();
            while(index<lenght){
                String char0=lsStringUsefullFunctions.StringAt(text,index);
                String char0LowerCase=char0.toLowerCase();
                //logger.info(fName+"char["+index+"]="+char0);
                String add="";
                switch (char0LowerCase){
                    case "h":
                        String char1=lsStringUsefullFunctions.StringAt(text,index+1);
                        add=char0+char1;
                        add+=char0LowerCase+char1+char0LowerCase+char1;
                        logger.info(fName+"char["+index+"]="+char0+", case:0, char[i+1]="+char1+", text="+add);
                        newtext.append(add);
                        index++;
                        break;
                    case "a": case "e": case "i":
                        logger.info(fName+"case:1");
                        add=char0;
                        add+="h"+char0LowerCase+"h"+char0LowerCase;
                        logger.info(fName+"char["+index+"]="+char0+", case:1, text="+add);
                        newtext.append(add);
                        break;
                    case "o":
                        logger.info(fName+"case:1");
                        add=char0;
                        add+="h"+char0LowerCase;
                        logger.info(fName+"char["+index+"]="+char0+", case:3, text="+add);
                        newtext.append(add);
                        break;
                    default:
                        logger.info(fName+"char["+index+"]="+char0+", case:-1, text="+char0);
                        newtext.append(char0);
                        break;
                }
                index++;
            }
            logger.info(fName+"new="+newtext.toString());
            return newtext.toString();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return text;
        }
    }
}

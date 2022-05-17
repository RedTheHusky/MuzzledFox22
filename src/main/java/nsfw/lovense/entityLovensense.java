package nsfw.lovense;

import kong.unirest.FailedResponse;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.log4j.Logger;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static nsfw.lovense.iLovense.gUrlLovesenseCommand;
import static nsfw.lovense.iLovense.gUrlLovesenseGetQrCode;


public class entityLovensense {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityLovensense]";
    final String keyTimeStamp=iLovense.keyTimeStamp,keyUpdated=iLovense.keyUpdated,keyRegistered=iLovense.keyRegistered;
     final String keyuId=iLovense.keyuid
         ,keyAppVersion=iLovense.keyappVersion
         ,keyAppType=iLovense.keyappType
         ,keyWssPort=iLovense.keywssPort
         ,keyWsPort=iLovense.keywsPort
         ,keyHttpPort=iLovense.keyhttpPort
         ,keyHttpsPort=iLovense.keyhttpsPort
         ,keyDomain=iLovense.keydomain
         ,keyVersion=iLovense.keyversion
         ,keyuToken=iLovense.keyutoken
         ,keyToys=iLovense.keytoys;
    String valueuId=""
            ,valueAppVersion=""
            ,valueAppType=""
            ,valueWssPort=""
            ,valueWsPort=""
            ,valueHttpPort=""
            ,valueHttpsPort=""
            ,valueDomain=""
            ,valueVersion=""
            ,valueuToken="";
    long valueNetLastGotTimestamp=0;
    long lovenseTimeout=iLovense.lovenseTimeout;
    List<entityLovensenseToyNet> toysNet=new ArrayList<>();
    List<entityLovensenseToyLoc> toysLoc=new ArrayList<>();
    static  final String keyNet="net",keyLoc="loc";
    public entityLovensense(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityLovensense(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        this.global=global;
    }
    public entityLovensense(lcGlobalHelper global,JSONObject jsonObject){
        String fName="[constructor]";
        try {
            this.global=global;
           set(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    lcGlobalHelper global;
    public boolean clear(){
        String fName="[clear]";
        try {
            clearLoc();clearNet();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean clearLoc(){
        String fName="[clearLoc]";
        try {
            toysLoc=new ArrayList<>();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean clearNet(){
        String fName="[clearNet]";
        try {
            valueuId="";
            valueAppVersion="";
            valueAppType="";
            valueWssPort="";
            valueWsPort="";
            valueHttpPort="";
            valueHttpsPort="";
            valueDomain="";
            valueVersion="";
            valueuToken="";
            toysNet=new ArrayList<>();
            valueNetLastGotTimestamp=0;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean set(JSONObject jsonObject){
        String fName="[set]";
        try {
            clear();
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return false;
            }
            setLoc(jsonObject);
            //setNet(jsonObject);
            setNetwNoToys(jsonObject);
            if(isNetLastGotTimestampValid()){
                logger.info(fName+".NetLastGotTimestamp is Valid>add toys state");
                setNetoToys(jsonObject);
            }else{
                logger.info(fName+".NetLastGotTimestamp is inValid>clear toys state");
                toysNet.clear();
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setLoc(JSONObject jsonObject){
        String fName="[setLoc]";
        try {
            clearLoc();
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return false;
            }
            JSONObject jsonTmp;
            if(jsonObject.has(keyLoc)){
                jsonTmp=jsonObject.getJSONObject(keyLoc);
            }else{
                jsonTmp=jsonObject;
            }
            logger.info(fName+"jsonTmp="+jsonTmp.toString());
            if(!jsonTmp.isEmpty()){
                Iterator<String> keys=jsonTmp.keys();
                while (keys.hasNext()){
                    String key=keys.next();
                    putLoc(key,jsonTmp);
                }
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setNet(JSONObject jsonObject){
        String fName="[setNet]";
        try {
            clearNet();
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return false;
            }
            JSONObject jsonTmp;
            if(jsonObject.has(keyNet)){
                jsonTmp=jsonObject.getJSONObject(keyNet);
            }else{
                jsonTmp=jsonObject;
            }
            logger.info(fName+"jsonTmp="+jsonTmp.toString());
            if(!jsonTmp.isEmpty()){
                Iterator<String> keys=jsonTmp.keys();
                while (keys.hasNext()){
                    String key=keys.next();
                    putNet(key,jsonTmp);
                }
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean setNetwNoToys(JSONObject jsonObject){
        String fName="[setNetWithoutToys]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return false;
            }
            JSONObject jsonTmp;
            if(jsonObject.has(keyNet)){
                jsonTmp=jsonObject.getJSONObject(keyNet);
            }else{
                jsonTmp=jsonObject;
            }
            logger.info(fName+"jsonTmp="+jsonTmp.toString());
            if(!jsonTmp.isEmpty()){
                Iterator<String> keys=jsonTmp.keys();
                while (keys.hasNext()){
                    String key=keys.next();
                    if(!key.equalsIgnoreCase(keyToys)){
                        putNet(key,jsonTmp);
                    }
                }
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean setNetoToys(JSONObject jsonObject){
        String fName="[setNetOnlyToys]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return false;
            }
            JSONObject jsonTmp;
            if(jsonObject.has(keyNet)){
                jsonTmp=jsonObject.getJSONObject(keyNet);
            }else{
                jsonTmp=jsonObject;
            }
            logger.info(fName+"jsonTmp="+jsonTmp.toString());
            if(!jsonTmp.isEmpty()&&jsonTmp.has(keyToys)&&!jsonTmp.isNull(keyToys)){
                putNet(keyToys,jsonTmp);
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean putLoc(String key,JSONObject jsonObject){
        String fName="[putLoc]";
        try {
            logger.info(fName+"key="+key);
            switch (key){
                case keyToys:
                    JSONObject jsonToys= jsonObject.getJSONObject(key);
                    Iterator<String> keys2=jsonToys.keys();
                    while (keys2.hasNext()){
                        try {
                            String key2=keys2.next();
                            JSONObject jsonToy=jsonToys.getJSONObject(key2);
                            logger.info(fName+"key="+key+", key2"+key2+", json="+jsonToy.toString());
                            entityLovensenseToyLoc toy=new entityLovensenseToyLoc(global);
                            toy.set(jsonToy);
                            toysLoc.add(toy);
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                    break;
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean putNet(String key,JSONObject jsonObject){
        String fName="[putNet]";
        try {
            logger.info(fName+"key="+key);
            switch (key){
                case keyTimeStamp:
                    valueNetLastGotTimestamp= jsonObject.getLong(key);
                    break;
                case keyAppType:
                    valueAppType= jsonObject.getString(key);
                    break;
                case keyAppVersion:
                    valueAppVersion= jsonObject.getString(key);
                    break;
                case keyDomain:
                    valueDomain= jsonObject.getString(key);
                    break;
                case keyHttpPort:
                    valueHttpPort= jsonObject.getString(key);
                    break;
                case keyHttpsPort:
                    valueHttpsPort= jsonObject.getString(key);
                    break;
                case keyuId:
                    valueuId= jsonObject.getString(key);
                    break;
                case keyuToken:
                    valueuToken= jsonObject.getString(key);
                    break;
                case keyVersion:
                    valueVersion= jsonObject.getString(key);
                    break;
                case keyWsPort:
                    valueWsPort= jsonObject.getString(key);
                    break;
                case keyWssPort:
                    valueWssPort= jsonObject.getString(key);
                    break;
                case keyToys:
                    JSONObject jsonToys= jsonObject.getJSONObject(key);
                    Iterator<String> keys2=jsonToys.keys();
                    while (keys2.hasNext()){
                        try {
                            String key2=keys2.next();
                            JSONObject jsonToy=jsonToys.getJSONObject(key2);
                            logger.info(fName+"key="+key+", key2"+key2+", json="+jsonToy.toString());
                            entityLovensenseToyNet toy=new entityLovensenseToyNet();
                            toy.set(jsonToy);
                            toysNet.add(toy);
                            try {
                                entityLovensenseToyLoc toyLoc=getToyLoc(toy.getID());
                                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                                logger.info(fName+".timestamp="+timestamp.getTime());
                                if(toyLoc==null){
                                    toyLoc=new entityLovensenseToyLoc(global);
                                    toyLoc.set(jsonToy);
                                    toysLoc.add(toyLoc);
                                    toyLoc.setRegistered(timestamp.getTime());
                                    toyLoc.setUpdated(timestamp.getTime());
                                }else{
                                    toyLoc.setNickname(toy.getNickname());
                                    toyLoc.setName(toy.getName());
                                    toyLoc.setUpdated(timestamp.getTime());
                                    updateToyLoc(toyLoc);
                                }

                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                    break;
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public JSONObject getJSON(){
        String fName="[getJSON]";
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(keyLoc,getJSONLoc());
            jsonObject.put(keyNet,getJSONNet());
            logger.info(fName+"jsonObject="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public JSONObject getJSONLoc(){
        String fName="[getJSONLoc]";
        try {
            JSONObject jsonLoc=new JSONObject();
            JSONObject jsonTOysLoc=new JSONObject();

            for(entityLovensenseToyLoc toy:toysLoc){
                logger.info(fName+"toy="+toy.getJSON());
                jsonTOysLoc.put(toy.getID(),toy.getJSON());
            }
            logger.info(fName+"jsonTOysLoc="+jsonTOysLoc.toString());
            jsonLoc.put(keyToys,jsonTOysLoc);
            logger.info(fName+"jsonObject="+jsonLoc.toString());
            return jsonLoc;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public JSONObject getJSONNet(){
        String fName="[getJSONNet]";
        try {
            JSONObject jsonNet=new JSONObject();
            JSONObject jsonToysNet=new JSONObject();
            jsonNet.put(keyTimeStamp,valueNetLastGotTimestamp);
            jsonNet.put(keyAppType,valueAppType);
            jsonNet.put(keyAppVersion,valueAppVersion);
            jsonNet.put(keyDomain,valueDomain);
            jsonNet.put(keyHttpPort,valueHttpPort);
            jsonNet.put(keyHttpsPort,valueHttpsPort);
            jsonNet.put(keyVersion,valueVersion);
            jsonNet.put(keyWsPort,valueWsPort);
            jsonNet.put(keyWssPort,valueWssPort);
            jsonNet.put(keyuId,valueuId);
            jsonNet.put(keyuToken,valueuToken);
            for(entityLovensenseToyNet toy:toysNet){
                logger.info(fName+"toy="+toy.getJSON());
                jsonToysNet.put(toy.getID(),toy.getJSON());
            }
            logger.info(fName+"jsonToysNet="+jsonToysNet.toString());
            jsonNet.put(keyToys,jsonToysNet);
            logger.info(fName+"jsonObject="+jsonNet.toString());
            return jsonNet;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public int getToysNetSize(){
        String fName="[getToysNetSize]";
        try {
            logger.info(fName+"size="+toysNet.size());
            return toysNet.size();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getToysLocSize(){
        String fName="[getToysLocSize]";
        try {
            logger.info(fName+"size="+toysLoc.size());
            return toysLoc.size();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public List<entityLovensenseToyNet> getToysNet(){
        String fName="[getToysNet]";
        try {
            logger.info(fName+"size="+toysNet.size());
            return toysNet;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public entityLovensenseToyNet getToyNet(int index){
        String fName="[getToyNet]";
        try {
            logger.info(fName+"size="+toysNet.size()+", index="+index);
            return toysNet.get(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLovensenseToyNet getToyNet(String  id){
        String fName="[getToyNet]";
        try {
            if(id==null){
                logger.info(fName+"id can't be null");
                return null;
            }
            logger.info(fName+"size="+toysNet.size()+", id="+id);
            for(entityLovensenseToyNet toy:toysNet){
                if(toy.getID().equalsIgnoreCase(id)){
                    return toy;
                }
            }
            return null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean hasToyNet(String  id){
        String fName="[hasToyNet]";
        try {
            if(id==null){
                logger.info(fName+"id can't be null");
                return false;
            }
            logger.info(fName+"size="+toysNet.size()+", id="+id);
            for(entityLovensenseToyNet toy:toysNet){
                if(toy.getID().equalsIgnoreCase(id)){
                    logger.info(fName+"found");
                    return true;
                }
            }
            logger.info(fName+"not found");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setToyNet(entityLovensenseToyNet toy){
        String fName="[setToyNet]";
        try {
            if(toy==null){
                logger.info(fName+"toy can't be null");
                return false;
            }
            String id=toy.getID();
            logger.info(fName+"size="+toysNet.size()+", id="+id);
            for(int i=0;i<toysNet.size();i++){
                entityLovensenseToyNet toy2=toysNet.get(i);
                logger.info(fName+"i="+i+", id="+toy2.getID());
                if(toy2.getID().equalsIgnoreCase(id)){
                    toysNet.set(i,toy);
                    logger.info(fName+"updated");
                    return true;
                }
            }
            toysNet.add(toy);
            logger.info(fName+"inserted");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean addToyNet(entityLovensenseToyNet toy){
        String fName="[addToyNet]";
        try {
            if(toy==null){
                logger.info(fName+"toy can't be null");
                return false;
            }
            String id=toy.getID();
            logger.info(fName+"size="+toysNet.size()+", id="+id);
            for(int i=0;i<toysNet.size();i++){
                entityLovensenseToyNet toy2=toysNet.get(i);
                logger.info(fName+"i="+i+", id="+toy2.getID());
                if(toy2.getID().equalsIgnoreCase(id)){
                    logger.info(fName+"already exists");
                    return false;
                }
            }
            toysNet.add(toy);
            logger.info(fName+"inserted");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean updateToyNet(entityLovensenseToyNet toy){
        String fName="[updateToyNet]";
        try {
            if(toy==null){
                logger.info(fName+"toy can't be null");
                return false;
            }
            String id=toy.getID();
            logger.info(fName+"size="+toysNet.size()+", id="+id);
            for(int i=0;i<toysNet.size();i++){
                entityLovensenseToyNet toy2=toysNet.get(i);
                logger.info(fName+"i="+i+", id="+toy2.getID());
                if(toy2.getID().equalsIgnoreCase(id)){
                    toysNet.set(i,toy);
                    logger.info(fName+"updated");
                    return true;
                }
            }
            logger.info(fName+"doesa not exists");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean remToyNet(entityLovensenseToyNet toy){
        String fName="[remToyNet]";
        try {
            if(toy==null){
                logger.info(fName+"toy can't be null");
                return false;
            }
            String id=toy.getID();
            logger.info(fName+"size="+toysNet.size()+", id="+id);
            for(int i=0;i<toysNet.size();i++){
                entityLovensenseToyNet toy2=toysNet.get(i);
                logger.info(fName+"i="+i+", id="+toy2.getID());
                if(toy2.getID().equalsIgnoreCase(id)){
                    toysNet.remove(i);
                    logger.info(fName+"removed");
                    return true;
                }
            }
            logger.info(fName+"not found");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public List<entityLovensenseToyLoc> getToysLoc(){
        String fName="[getToysLoc]";
        try {
            logger.info(fName+"size="+toysLoc.size());
            return toysLoc;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public entityLovensenseToyLoc getToyLoc(int index){
        String fName="[getToyLoc]";
        try {
            logger.info(fName+"size="+toysLoc.size()+", index="+index);
            return toysLoc.get(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLovensenseToyLoc getToyLoc(String  id){
        String fName="[getToyLoc]";
        try {
            if(id==null){
                logger.info(fName+"id can't be null");
                return null;
            }
            logger.info(fName+"size="+toysLoc.size()+", id="+id);
            for(entityLovensenseToyLoc toy:toysLoc){
                if(toy.getID().equalsIgnoreCase(id)){
                    return toy;
                }
            }
            return null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean hasToyLoc(int  index){
        String fName="[hasToyLoc]";
        try {
            logger.info(fName+"size="+toysLoc.size()+", index="+index);
            if(index>=0&&index<toysLoc.size()){
                logger.info(fName+"index area ok");
                return true;
            }
            logger.info(fName+"invalid index");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public int getToyLocStatus(int  index){
        String fName="[getToyLocStatus]";
        try {
            logger.info(fName+"size="+toysLoc.size()+", index="+index);
            entityLovensenseToyLoc toyLoc=getToyLoc(index);
            if(toyLoc==null){
                logger.info(fName+"toyLoc is null>0");
                return 0;
            }
            entityLovensenseToyNet toyNet=getToyNet(toyLoc.getID());
            if(toyNet==null){
                logger.info(fName+"toyNet is null>0");
                return 0;
            }
            int status=toyNet.getStatus();
            logger.info(fName+"status="+status);
            return status;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getToyLocStatus(String  id){
        String fName="[getToyLocStatus]";
        try {
            logger.info(fName+"size="+toysLoc.size()+", id="+id);
            entityLovensenseToyLoc toyLoc=getToyLoc(id);
            if(toyLoc==null){
                logger.info(fName+"toyLoc is null>0");
                return 0;
            }
            entityLovensenseToyNet toyNet=getToyNet(toyLoc.getID());
            if(toyNet==null){
                logger.info(fName+"toyNet is null>0");
                return 0;
            }
            int status=toyNet.getStatus();
            logger.info(fName+"status="+status);
            return status;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean hasToyLoc(String  id){
        String fName="[hasToyLoc]";
        try {
            if(id==null){
                logger.info(fName+"id can't be null");
                return false;
            }
            logger.info(fName+"size="+toysLoc.size()+", id="+id);
            for(entityLovensenseToyLoc toy:toysLoc){
                if(toy.getID().equalsIgnoreCase(id)){
                    logger.info(fName+"found");
                    return true;
                }
            }
            logger.info(fName+"not found");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setToyLoc(entityLovensenseToyLoc toy){
        String fName="[setToyLoc]";
        try {
            if(toy==null){
                logger.info(fName+"toy can't be null");
                return false;
            }
            String id=toy.getID();
            logger.info(fName+"size="+toysLoc.size()+", id="+id);
            for(int i=0;i<toysLoc.size();i++){
                entityLovensenseToyLoc toy2=toysLoc.get(i);
                logger.info(fName+"i="+i+", id="+toy2.getID());
                if(toy2.getID().equalsIgnoreCase(id)){
                    toysLoc.set(i,toy);
                    logger.info(fName+"updated");
                    return true;
                }
            }
            toysLoc.add(toy);
            logger.info(fName+"inserted");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean addToyLoc(entityLovensenseToyLoc toy){
        String fName="[addToyLoc]";
        try {
            if(toy==null){
                logger.info(fName+"toy can't be null");
                return false;
            }
            String id=toy.getID();
            logger.info(fName+"size="+toysLoc.size()+", id="+id);
            for(int i=0;i<toysLoc.size();i++){
                entityLovensenseToyLoc toy2=toysLoc.get(i);
                logger.info(fName+"i="+i+", id="+toy2.getID());
                if(toy2.getID().equalsIgnoreCase(id)){
                    logger.info(fName+"already exists");
                    return false;
                }
            }
            toysLoc.add(toy);
            logger.info(fName+"inserted");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean updateToyLoc(entityLovensenseToyLoc toy){
        String fName="[updateToyLoc]";
        try {
            if(toy==null){
                logger.info(fName+"toy can't be null");
                return false;
            }
            String id=toy.getID();
            logger.info(fName+"size="+toysLoc.size()+", id="+id);
            for(int i=0;i<toysLoc.size();i++){
                entityLovensenseToyLoc toy2=toysLoc.get(i);
                logger.info(fName+"i="+i+", id="+toy2.getID());
                if(toy2.getID().equalsIgnoreCase(id)){
                    toysLoc.set(i,toy);
                    logger.info(fName+"updated");
                    return true;
                }
            }
            logger.info(fName+"doesa not exists");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean remToyLoc(entityLovensenseToyLoc toy){
        String fName="[remToyLoc]";
        try {
            if(toy==null){
                logger.info(fName+"toy can't be null");
                return false;
            }
            String id=toy.getID();
            logger.info(fName+"size="+toysLoc.size()+", id="+id);
            for(int i=0;i<toysLoc.size();i++){
                entityLovensenseToyLoc toy2=toysLoc.get(i);
                logger.info(fName+"i="+i+", id="+toy2.getID());
                if(toy2.getID().equalsIgnoreCase(id)){
                    toysLoc.remove(i);
                    logger.info(fName+"removed");
                    return true;
                }
            }
            logger.info(fName+"not found");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public long getNetLastGotTimestamp(){
        String fName="[getNetLastGotTimestamp]";
        try {
            logger.info(fName+"valueNetLastGotTimestamp="+valueNetLastGotTimestamp);
            return valueNetLastGotTimestamp;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public boolean setNetLastGotTimestamp(long input){
        String fName="[setNetLastGotTimestamp]";
        try {
            logger.info(fName+"input="+input);
            valueNetLastGotTimestamp=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isNetLastGotTimestampValid(){
        String fName="[isNetLastGotTimestampValid]";
        try {
            if(valueNetLastGotTimestamp<=0){
                logger.info(fName+".valueNetLastGotTimestamp is 0 or smaller, invalid>false");
                return false;
            }
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            long diff=timestamp.getTime()-valueNetLastGotTimestamp;
            logger.info(fName+".current="+timestamp.getTime()+", previous="+valueNetLastGotTimestamp+", diff="+diff+", timeout="+lovenseTimeout);
            if(diff<lovenseTimeout){
                logger.info(fName+".diff still small>true");
                return true;
            }
            logger.info(fName+".diff too big>false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    String keyReq_Token="token",keyReq_uId="uid",keyReq_uName="uname",keyReq_uToken="utoken";
    public HttpResponse<JsonNode> reqGetQrCode(Guild guild, Member member){
        String fName="[doFunction]";
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(keyReq_Token, iLovense.Config.getToken(global));
            jsonObject.put(keyReq_uId,member.getId());
            jsonObject.put(keyReq_uName,member.getUser().getName());
            jsonObject.put(keyReq_uToken,member.getId()+","+guild.getId());
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            logger.info(fName + ".ready to send");
            HttpResponse<JsonNode> jsonResponse =a.post(gUrlLovesenseGetQrCode)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(jsonObject.toString())
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            logger.info(fName+".body ="+ jsonResponse.getBody());
            return jsonResponse;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public HttpResponse<JsonNode> doStop (entityLovensenseToyLoc toyLoc){
        String fName="[doStop]";
        try {
            List<entityLovensenseAction>actions=new ArrayList<>();
            actions.add(new entityLovensenseAction(iLovense.Actions4Function.Stop));
            return toyLoc.doFunction(valueuId,1,actions,20,0,0);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public HttpResponse<JsonNode> doFunction (entityLovensenseToyLoc toyLoc, iLovense.Actions4Function action, int strength, int timeSec, int loopRunningSec, int LoopPauseSec){
        String fName="[doFunction]";
        try {
            return toyLoc.doFunction(valueuId,1,action,strength,timeSec,loopRunningSec,LoopPauseSec);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public HttpResponse<JsonNode> doFunction (entityLovensenseToyLoc toyLoc,List<entityLovensenseAction>actions,int timeSec,int loopRunningSec, int LoopPauseSec){
        String fName="[doFunction]";
        try {
            return toyLoc.doFunction(valueuId,1,actions,timeSec,loopRunningSec,LoopPauseSec);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public HttpResponse<JsonNode> doPreset (entityLovensenseToyLoc toyLoc, iLovense.Actions4Preset preset, int timeSec){
        String fName="[doPreset]";
        try {
            return toyLoc.doPreset(valueuId,1,preset,timeSec);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
}

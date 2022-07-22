package models.lc;

import kong.unirest.json.JSONObject;
import models.lc.json.lcText2Json;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import models.llGlobalHelper;
import net.dv8tion.jda.api.entities.Guild;
import nsfw.chastity.chastikey.iChastiKey;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class lcSqlConnEntity {




    public String url="";
    public Connection conn;
    public boolean flagConn=false;
    public boolean flagDriver=false;
    Logger logger =Logger.getLogger(getClass());
    String cName="[sqlConnEntity]";
    public lcSqlConnEntity(){

    }
    String gUrl="";
    Properties gProps= new Properties();
    lcGlobalHelper global;
    public interface SQLConfig {
        String config="sql";
        public interface SQL {
            String url="url",
                    properties="properties";
            public interface Properties {
                String user="user",
                        password="password";
            }
        }

    }

    public boolean getProperties(lcGlobalHelper global){
        String fName="[getProperties]";
        try {
            this.global=global;
            JSONObject jsonSql=global.configfile.getJsonObjectAsJsonObject(SQLConfig.config);
            if(jsonSql==null){
                logger.warn(fName+"jsonSql is null");
                return false;
            }
            if(jsonSql.isEmpty()){
                logger.warn(fName+"jsonSql is empty");
                return false;
            }
            logger.info(fName+" jsonSql="+jsonSql.toString());
            gProps.clear();
            gUrl="";
            String key="";
            key=SQLConfig.SQL.url;if( jsonSql.has(key)&&!jsonSql.isNull(key))gUrl=jsonSql.optString(key,"");
            key=SQLConfig.SQL.properties;if( jsonSql.has(key)&&!jsonSql.isNull(key)){
                JSONObject jsonProperties=jsonSql.optJSONObject(key);
                Iterator<String>keysProperties=jsonProperties.keys();
                while (keysProperties.hasNext()){
                    key=keysProperties.next();
                    if( jsonProperties.has(key)&&!jsonProperties.isNull(key))gProps.setProperty(key, jsonProperties.optString(key,""));
                }

            }
            logger.info(fName + ".return true");
            return  true;


        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean openConnection(){
        String fName="[openConnection]";
        try {
            if(!flagDriver){
               logger.info(cName+fName+".driver not started!");
                return false;
            }
            flagConn=false;
            logger.info(cName+fName+".gUrl="+gUrl);
            logger.info(cName+fName+".gProps="+gProps.toString());
            conn = DriverManager.getConnection(gUrl, gProps);
            logger.info(cName+fName+".done");
            flagConn=true; return true;
        } catch (SQLException ex) {
            // handle any errors
            logger.error(cName+fName+".SQLException: " + ex.getMessage());
            logger.error(cName+fName+".SQLState: " + ex.getSQLState());
            logger.error(cName+fName+".VendorError: " + ex.getErrorCode());
            return false;
        }
    }
    public boolean closeConnection(){
        String fName="[closeConnection]";
        try {
            if(conn!=null){
                conn.close();flagConn=false;
                if(conn.isClosed()){
                   logger.info(cName+fName+".connection closed");
                    return true;
                }else{
                    logger.error(cName+fName+".connection error closed");
                    return false;
                }
            }else{
               logger.info(cName+fName+".Skip as it was not open in the first place!");
                return true;
            }
        } catch (SQLException ex) {
            logger.error(cName+fName+".SQLException: " + ex.getMessage());
            logger.error(cName+fName+".SQLState: " + ex.getSQLState());
            logger.error(cName+fName+".VendorError: " + ex.getErrorCode());
            return false;
        }
    }
    public boolean startDriver(){
        String fName="[startDriver]";
        try {
            //logger.info("@"+cName+fName);
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
           logger.info(cName+fName+".done");
            flagDriver=true;return true;
        } catch (Exception e) {
            logger.error(cName+fName+".exception:"+e);
            flagDriver=false;return false;
        }
    }
    public boolean insert(String table, Map<String, Object> map){
        String fName="[sqlInsert]";
        try{
            String query="";
            for(int attempt=1;attempt<=3;attempt++) {
                logger.info(cName + fName + ".attempt=" + attempt);
                try {
                    checkConnection();
                    if(!isConnected()){
                        logger.info(cName+fName+".no connection!");
                        return false;
                    }
                    logger.info(cName + fName + ".table=" + table);
                    logger.info(cName + fName + ".map=" + map);
                    StringBuilder columns = new StringBuilder();
                    StringBuilder space4values = new StringBuilder();
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        String key = entry.getKey();
                        //Object value = entry.getValue();
                        if (columns.length() > 0) {
                            columns.append(", ").append(key);
                            space4values.append(",?");
                        } else {
                            columns = new StringBuilder(key);
                            space4values = new StringBuilder("?");
                        }
                    }
                    query = "insert into " + table + " (" + columns + ")values(" + space4values + ")";
                    logger.info(cName + fName + ".sql=" + query);
                    PreparedStatement pst = conn.prepareStatement(query);
                    int i = 1;
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        //String key = entry.getKey();
                        Object value = entry.getValue();
                        pst.setObject(i, value);
                        i++;
                    }
                    logger.info(cName + fName + ".columns=" + columns);
                    logger.info(cName + fName + ".space4values=" + space4values);
                    int status = pst.executeUpdate();
                    logger.info(cName + fName + ".executed");
					map=null;
                    if (status == 0) {
                        logger.info(cName + fName + ".failed");
                        return false;
                    }
                    logger.info(cName + fName + ".success");
                    return true;
                } catch (Exception e) {
                    logger.error(cName + fName + ".exception["+attempt+"]:" + e);
                    if(attempt==3){
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        logger.error(cName + fName + ".query:" + query);
                    }
                }
            }
            return false;
        }
        catch(Exception e0){
            logger.error(cName+fName+".exception:"+e0); return false;
        }
    }
    public boolean delete(String table, String condition){
        String fName="[sglDelete]";
       try{
           String query="";
           for(int attempt=1;attempt<=3;attempt++){
               logger.info(cName+fName+".attempt="+attempt);
               try{
                   checkConnection();
                   if(!isConnected()){
                       logger.info(cName+fName+".no connection!");
                       return false;
                   }
                   logger.info(cName+fName+".table="+table);
                   logger.info(cName+fName+".condition="+condition);
                   query = "delete from "+table+" where "+condition;
                   logger.info(cName+fName+".sql="+query);
                   PreparedStatement pst = conn.prepareStatement(query);
                   int status=pst.executeUpdate();
                   logger.info(cName+fName+".executed");
                   if(status==0){
                       logger.info(cName+fName+".failed");
                       return false;
                   }
                   logger.info(cName+fName+".success");
                   return true;
               }
               catch(Exception e){
                   logger.error(cName + fName + ".exception["+attempt+"]:" + e);
                   if(attempt==3){
                       logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                       logger.error(cName + fName + ".query:" + query);
                   }
               }
           }
           return false;
        }
        catch(Exception e0){
            logger.error(cName+fName+".exception:"+e0); return false;
        }

    }
    public boolean update(String table, Map<String, Object> map, String condition){
        String fName="[sqlUpdate]";
        //boolean debugInfo=true;
        try{
            String query="";
            for(int attempt=1;attempt<=3;attempt++) {
                logger.info(cName + fName + ".attempt=" + attempt);
                try{
                    checkConnection();
                    if(!isConnected()){
                        logger.info(cName+fName+".no connection!");
                        return false;
                    }
                    logger.info(cName+fName+".table="+table);
                    logger.info(cName+fName+".map="+map);
                    logger.info(cName+fName+".condition="+condition);
                    StringBuilder space4values= new StringBuilder();
                    for(Map.Entry<String, Object> entry : map.entrySet()) {
                        String key = entry.getKey();
                        //Object value = entry.getValue();
                        if(space4values.length()>0){
                            space4values.append(", ").append(key).append(" = ?");
                        }else{
                            space4values = new StringBuilder(key + " = ?");
                        }
                    }
                    // String query = "update users set num_points = ? where first_name = ?";
                    query = "update "+table+" set "+space4values+" where "+condition;
                    logger.info(cName+fName+".sql="+query);
                    PreparedStatement pst = conn.prepareStatement(query);
                    int i=1;
                    for(Map.Entry<String, Object> entry : map.entrySet()) {
                        //String key = entry.getKey();
                        Object value = entry.getValue();
                        pst.setObject(i, value);
                        i++;
                    }
                    logger.info(cName+fName+".space4values="+space4values);
                    int status=pst.executeUpdate();
                    logger.info(cName+fName+".executed");
					map=null;
                    if(status==0){
                        logger.info(cName+fName+".failed");
                        return false;
                    }
                    logger.info(cName+fName+".success");
                    return true;
                }
                catch(Exception e){
                    logger.error(cName + fName + ".exception["+attempt+"]:" + e);
                    if(attempt==3){
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        logger.error(cName + fName + ".query:" + query);
                    }
                }
            }
            return false;
        }
        catch(Exception e0){
            logger.error(cName+fName+".exception:"+e0);return false;
        }

    }
    public Map<String,Object> selectFirst(String table, String condition, List<String> columns){
        String fName="[selectFirst]";
        //boolean debugInfo=true;
        try{
            String query="";
            for(int attempt=1;attempt<=3;attempt++) {
                logger.info(cName + fName + ".attempt=" + attempt);
                try{
                    checkConnection();
                    if(!isConnected()){
                        logger.info(cName+fName+".no connection!");
                        return null;
                    }
                    logger.info(cName+fName+".table="+table);
                    logger.info(cName+fName+".condition="+condition);
                    logger.info(cName+fName+".columns="+columns.toString());
                    query = "select * from "+table+" where "+condition+" LIMIT 1";
                    logger.info(cName+fName+".sql="+query);
                    PreparedStatement pst = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs=pst.executeQuery();
                    logger.info(cName+fName+".executed");
                    if(rs==null){
                        logger.info(cName+fName+".its null");
                        return new TreeMap<>();
                    }
                    if(rs.isClosed()){
                        logger.info(cName+fName+".its closed");
                        return new TreeMap<>();
                    }
                    if (!rs.next()) {
                        logger.info(cName+fName+".its empty");
                        return new TreeMap<>();
                    }
                    Map<String,Object> result= new TreeMap<>();
                    rs.first();
                    for(String column : columns){
                        logger.info(cName+fName+"row["+rs.getRow()+"], column["+column+"]");
                        result.put(column,rs.getObject(column));
                    }
                    columns=null;
                    return result;
                }
                catch(Exception e){
                    logger.error(cName + fName + ".exception["+attempt+"]:" + e);
                    if(attempt==3){
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        logger.error(cName + fName + ".query:" + query);
                    }
                }
            }
            return null;
        }
        catch(Exception e0){
            logger.error(cName+fName+".exception:"+e0); return null;
        }

    }
    public Map<String,Object> selectFirst(String table, String condition){
        String fName="[selectFirst]";
        //boolean debugInfo=true;
        try{
            String query="";
            for(int attempt=1;attempt<=3;attempt++) {
                logger.info(cName + fName + ".attempt=" + attempt);
                try{
                    checkConnection();
                    if(!isConnected()){
                        logger.info(cName+fName+".no connection!");
                        return null;
                    }
                    logger.info(cName+fName+".table="+table);
                    logger.info(cName+fName+".condition="+condition);
                    query = "select * from "+table+" where "+condition+" LIMIT 1";
                    logger.info(cName+fName+".sql="+query);
                    PreparedStatement pst = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs=pst.executeQuery();
                    logger.info(cName+fName+".executed");
                    if(rs==null){
                        logger.info(cName+fName+".its null");
                        return new TreeMap<>();
                    }
                    if(rs.isClosed()){
                        logger.info(cName+fName+".its closed");
                        return new TreeMap<>();
                    }
                    if (!rs.next()) {
                        logger.info(cName+fName+".its empty");
                        return new TreeMap<>();
                    }
                    Map<String,Object> result= new TreeMap<>();
                    rs.first();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();
                    for (int i = 1; i <= columnCount; i++ ) {
                        String name = rsmd.getColumnName(i);
                        logger.info(cName+fName+"row["+rs.getRow()+"], column["+name+"]");
                        result.put(name,rs.getObject(name));
                    }
                    return result;
                }
                catch(Exception e){
                    logger.error(cName + fName + ".exception["+attempt+"]:" + e);
                    if(attempt==3){
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        logger.error(cName + fName + ".query:" + query);
                    }
                }
            }
            return null;
        }
        catch(Exception e0){
            logger.error(cName+fName+".exception:"+e0); return null;
        }

    }
    public Boolean checkConnection(){
        String fName="[checkConnection]";
        logger.info(cName+fName+".@");
        //String table="version";
        try{
            if(conn==null){
                logger.info(cName+fName+"is null");
                return openConnection();
            }else
            if(conn.isClosed()){
                logger.info(cName+fName+"is closed");
                return openConnection();
            }
            //String query = "select 1 from "+table;
            String query="SELECT COUNT(*) FROM information_schema.tables ";
            logger.info(cName+fName+".sql="+query);
            PreparedStatement pst = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            //ResultSet rs=pst.executeQuery();
            logger.info(cName+fName+".executed");
            return true;
        }catch (Exception e){
            logger.error(cName+fName+".exception:"+e);
            logger.info(cName+fName+"attempt 2 open");
            return openConnection();
        }
    }
    public Boolean isConnected(){
        String fName="[isConnected]";
        logger.info(cName+fName+".@");
        try{
            if(conn==null){
                logger.info(cName+fName+"is null");
                return false;
            }else
            if(conn.isClosed()){
                logger.info(cName+fName+"is closed");
                return false;
            }
            String query="SELECT COUNT(*) FROM information_schema.tables ";
            logger.info(cName+fName+".sql="+query);
            PreparedStatement pst = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            //ResultSet rs=pst.executeQuery();
            logger.info(cName+fName+".executed");
            return true;
        }catch (Exception e){
            logger.error(cName+fName+".exception:"+e);
            return false;
        }
    }

    //not used functions
    /*public List <Map> selectAll(String table, String condition, List<String> columns){
        String fName="selectAll";
        //boolean debugInfo=true;
        logger.info(cName+fName+".@");
        try{
            checkConnection();
            if(!isConnected()){
                logger.info(cName+fName+".no connection!");
                return null;
            }
            logger.info(cName+fName+".table="+table);
            logger.info(cName+fName+".condition="+condition);
            logger.info(cName+fName+".columns="+columns.toString());
            String query = "select * from "+table+" where "+condition;
            logger.info(cName+fName+".sql="+query);
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs=pst.executeQuery();
            logger.info(cName+fName+".executed");
            if(rs==null){
                logger.info(cName+fName+".its null");
                return null;
            }
            if(rs.isClosed()){
                logger.info(cName+fName+".its closed");
                return null;
            }
            if (!rs.next()) {
                logger.info(cName+fName+".its empty");
                return null;
            }
            List <Map> result= new ArrayList<>();
            while (rs.next()){
                Map<String,Object> row= new TreeMap<>();
                for(String column : columns){
                    logger.info(cName+fName+"row["+rs.getRow()+"], column["+column+"]");
                    row.put(column,rs.getObject(column));
                }
                result.add(row);
            }
            return result;
        }
        catch(Exception e){
            logger.error(cName+fName+".exception:"+e);return null;
        }
    }
    public JSONObject getSettings( String table,Guild guild){
        String fName="getSettings";
        //boolean debugInfo=true;
        logger.info(cName+fName+".@");
        try{
            checkConnection();
            if(!isConnected()){
                logger.info(cName+fName+".no connection!");
                return null;
            }
            List<String> columns=new ArrayList<>();
            columns.add("value_boolean"); columns.add("value_int"); columns.add("value_timestamp"); columns.add("value_text");columns.add("value_json");
            logger.info(cName+fName+".table="+table);
            logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
            logger.info(cName+fName+".columns="+columns.toString());
            String query = "select * from "+table+" where guild_id='"+guild.getId()+"'";
            logger.info(cName+fName+".sql="+query);
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs=pst.executeQuery();
            logger.info(cName+fName+".executed");
            JSONObject result= new JSONObject();
            while (rs.next()){
                JSONObject row= new JSONObject();
                for(String column : columns){
                    logger.info(cName+fName+"row["+rs.getRow()+"], column["+column+"]="+rs.getObject(column));
                    row.put(column,rs.getObject(column));
                }
                String name=rs.getString("name");
                result.put(name,row);
            }
            return result;
        }
        catch(Exception e){
            logger.error(cName+fName+".exception:"+e);return null;
        }
    }
     */
}

package is.idega.idegaweb.tracker.data;

import java.sql.Timestamp;
import java.sql.SQLException;
import com.idega.data.GenericEntity;
import com.idega.core.data.ICLocale;


/**
 * Title:        com.idega.idegaweb.tracker.data.UserAgentStatistics
 * Description:  Keeps track of what browser(user agents) being used
 * Copyright:    Copyright (c) 2002
 * Company:      idega
 * @author <a href="eiki@idega.is">Eirikur S. Hrafnsson</a>
 * @version 1.0
 */

public class UserAgentStatistics extends GenericEntity {

  public UserAgentStatistics() {
    super();
  }

  public UserAgentStatistics(int id) throws SQLException{
    super(id);
  }

  public void initializeAttributes() {
    addAttribute(getIDColumnName());
    addAttribute(getColumnNameUserAgent(),"User agent string",true,true,String.class,500);
    addAttribute(getColumnNameSessions(),"Number of users with this agent",true,true,Integer.class);
    addAttribute(getColumnNameLocaleId(),"Locale",true,true, Integer.class,"many-to-one",ICLocale.class);
    addAttribute(getColumnNameDate(),"Date of record",true,true, Timestamp.class);
  }

  public String getEntityName() {
    return getEntityTableName();
  }

  public static String getEntityTableName(){ return "TR_USER_AGENT_STATISTICS";}
  public static String getColumnNameUserAgent(){return "IB_USER_AGENT";}
  public static String getColumnNameLocaleId(){return "IC_LOCALE_ID";}
  public static String getColumnNameSessions(){return "SESSIONS";}
  public static String getColumnNameDate(){return "MODIFICATION_DATE";}

  public String getUserAgent(){
    return (String) getColumnValue(getColumnNameUserAgent());
  }

  public int getSessions(){
    return getIntColumnValue(getColumnNameSessions());
  }

  public int getLocale(){
    return getIntColumnValue(getColumnNameLocaleId());
  }

  public Timestamp getDate(){
    return (Timestamp) getColumnValue(getColumnNameDate());
  }

  public void setUserAgent(String userAgentString){
    setColumn(getColumnNameUserAgent(), userAgentString);
  }

  public void setSessions(int sessionHits){
    setColumn(getColumnNameSessions(), sessionHits);
  }

  public void setLocale(int locale){
    setColumn(getColumnNameLocaleId(), locale);
  }

  public void setModificationDate(Timestamp date){
    setColumn(getColumnNameDate(), date);
  }


}
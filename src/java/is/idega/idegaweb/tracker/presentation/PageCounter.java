package is.idega.idegaweb.tracker.presentation;

import java.util.*;
import com.idega.presentation.Block;
import com.idega.presentation.*;
import com.idega.presentation.ui.*;
import com.idega.presentation.text.*;

import com.idega.presentation.IWContext;
import is.idega.idegaweb.tracker.business.TrackerBusiness;

import is.idega.idegaweb.tracker.data.*;
/**
 * Title:        is.idega.idegaweb.tracker.presentation.PageCounter
 * Description:  A simple page counter that can display the number of visits/hits with text/images
 * Copyright:    Copyright (c) 2002
 * Company:      Idega software
 * @author Eirikur S. Hrafnsson
 * @version 1.0
 */

public class PageCounter extends Block {
  private boolean showCurrentPH = true;//show current page hits
  private boolean showCurrentPS = false;//show current page sessions
  private boolean showTotalPH = false;//show total page hits
  private boolean showTotalPS = false;//show total page session
  private boolean showReferers = false;//show referers
  private boolean showUserAgents = false;//show user agents
  private boolean update = true;//update the stats(true) or just showing stats(false)
  private Map ipFilter = new HashMap();/**clone**/

  public PageCounter() {
  }

  public void main(IWContext iwc) throws Exception{
    if( (update) && (updateStats(iwc.getRemoteIpAddress())) ){
      TrackerBusiness.runThroughTheStatsMachine(iwc);
    }

    if(showCurrentPH){
      Text hits = new Text("Current page hits: "+TrackerBusiness.getCurrentPageHits(iwc));
      hits.setBold(true);
      add(hits);
      addBreak();
    }

    if(showCurrentPS){
      Text hits2 = new Text("Current page sessions: "+TrackerBusiness.getCurrentPageSessions(iwc));
      hits2.setBold(true);
      add(hits2);
      addBreak();
    }

    if(showTotalPS){
      Text hits4 = new Text("Total website sessions: "+TrackerBusiness.getTotalSessions());
      hits4.setBold(true);
      add(hits4);
      addBreak();
    }

    if(showTotalPH){
      Text hits3 = new Text("Total website hits: "+TrackerBusiness.getTotalHits());
      hits3.setBold(true);
      add(hits3);
    }



    if(showReferers){
      //referers
      debug("Sorting referers");
      Table refs = new Table();
      int y = 1;
      refs.add("Referer url",1,y);
      refs.add("Count",2,y);

      ArrayList referers =  TrackerBusiness.getRefererArrayListSortedBySessions();

      Iterator referer = referers.iterator();
      ReferrerStatistics item;
      while (referer.hasNext()) {
        item = (ReferrerStatistics) referer.next();
        refs.add(item.getReferrerUrl(),1,++y);
        refs.add(String.valueOf(item.getSessions()),2,y);
      }

     add(refs);

      debug("added referers");

     addBreak();
    }


    if(showUserAgents){
      //agents
      Table agents = new Table();
      int y2 = 1;
      agents.add("User agents",1,y2);
      agents.add("Count",2,y2);

      ArrayList agentsList = TrackerBusiness.getAgentArrayListSortedByAgent();
      Iterator ua = agentsList.iterator();
      UserAgentStatistics item;
      while (ua.hasNext()) {
        item = (UserAgentStatistics) ua.next();
        agents.add(item.getUserAgent(),1,++y2);
        agents.add(String.valueOf(item.getSessions()),2,y2);
      }

      add(agents);
    }

  }


  public void setShowCurrentPageHits(boolean show){
    showCurrentPH = show;
  }

  public void setShowCurrentPageSessions(boolean show){
    showCurrentPS = show;
  }

  public void setShowTotalHits(boolean show){
    showTotalPH = show;
  }

  public void setShowTotalSessions(boolean show){
    showTotalPS = show;
  }

  public void setUpdateStats(boolean update){
    this.update = update;
  }

  public void setShowReferers(boolean show){
    showReferers = show;
  }

  public void setShowAgents(boolean show){
    showUserAgents = show;
  }

  public void setIpFilterNumber(String ipNumber){
   ipFilter.put(ipNumber,ipNumber);
  }

  public void setIpFilterNumber(String name, String ipNumber){
   ipFilter.put(name,ipNumber);
  }

  public void removeIpFilterNumber(String nameOrIpNumber){
   ipFilter.remove(nameOrIpNumber);
  }

  private boolean updateStats(String ipNumber){
    return !(ipFilter.containsValue(ipNumber));
  }

 public synchronized Object clone() {
    PageCounter obj = null;
    try {
      obj = (PageCounter)super.clone();
      obj.ipFilter = this.ipFilter;
    }
    catch(Exception ex) {
      ex.printStackTrace(System.err);
    }

    return obj;
  }


}

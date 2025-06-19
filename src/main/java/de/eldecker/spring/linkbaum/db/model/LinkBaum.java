package de.eldecker.spring.linkbaum.db.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.RedisHash;


/**
 * Klasse ist mit @RedisHash annotiert, damit sie in Redis gespeichert
 * werden kann. Ein Hash in Redis ist eine Art Map, die Schlüssel-Wert-Paare
 * enthält. 
 */
@RedisHash("LinkBaum")
public class LinkBaum {

    private static final int DEFAULT_ANZAHL_LINKEINTRAEGE = 5;
    
    private String id;
    
    private String titel;
    
    private String beschreibung;
    
    private List<LinkEintrag> linkEintragList;
        
    public LinkBaum() {

        id           = "";
        titel        = "";
        beschreibung = "";
        
        linkEintragList = new ArrayList<>( DEFAULT_ANZAHL_LINKEINTRAEGE );
    }
    
    public LinkBaum( String id, String titel, String beschreibung ) {
        
        this.titel        = titel;
        this.beschreibung = beschreibung;
        
        linkEintragList = new ArrayList<>( DEFAULT_ANZAHL_LINKEINTRAEGE );
    }

    public String getTitel() {
        
        return titel;
    }

    public void setTitel( String titel ) {
        
        this.titel = titel;
    }

    public String getBeschreibung() {
        
        return beschreibung;
    }

    public void setBeschreibung( String beschreibung ) {
        
        this.beschreibung = beschreibung;
    }

    public List<LinkEintrag> getLinkEintragList() {
        
        return linkEintragList;
    }

    public void setLinkEintragList( List<LinkEintrag> linkEintragList ) {
        
        this.linkEintragList = linkEintragList;
    }
    
    public void addLinkEintraege( LinkEintrag... linkEintrage ) {
        
        if ( linkEintragList == null ) {
            
            linkEintragList = new ArrayList<>( DEFAULT_ANZAHL_LINKEINTRAEGE );
        }

        for ( LinkEintrag linkEintrag : linkEintrage ) {
            
            linkEintragList.add( linkEintrag );
        }
    }

    public void setId( String id ) {
        
        this.id = id;
    }
    
    public String getId() {
        
        return id;
    }
    
}


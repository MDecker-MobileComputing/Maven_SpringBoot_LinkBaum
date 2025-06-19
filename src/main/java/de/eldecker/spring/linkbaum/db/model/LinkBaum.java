package de.eldecker.spring.linkbaum.db.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


/**
 * Klasse ist mit @RedisHash annotiert, damit sie in Redis gespeichert
 * werden kann. Ein Hash in Redis ist eine Art Map, die Schlüssel-Wert-Paare
 * enthält. 
 */
@RedisHash("LinkBaum")
public class LinkBaum {

    private static final int DEFAULT_ANZAHL_LINKEINTRAEGE = 5;
    
    /**
     * ID wird selbst gesetzt, weil sie als Pfadparameter in der URL
     * verwendet wird und in den meisten Fällen auch sprechend sein
     * wird, z.B. Name des Influencers. Wenn als Key "abc" gewählt
     * wird, dann wird in Redis/Valkey folgender Key verwendet:
     * {@code LinkBaum:abc}
     */
    @Id
    private String _id;
    
    /** Titel enthält Name Influencer/Organisation oder Thema. */
    private String _titel;
    
    private String _beschreibung;
    
    private List<LinkEintrag> _linkEintragList;
        
    public LinkBaum() {

        _id           = "";
        _titel        = "";
        _beschreibung = "";
        
        _linkEintragList = new ArrayList<>( DEFAULT_ANZAHL_LINKEINTRAEGE );
    }
    
    public LinkBaum( String id, String titel, String beschreibung ) {
        
        _id           = id;
        _titel        = titel;
        _beschreibung = beschreibung;
        
        _linkEintragList = new ArrayList<>( DEFAULT_ANZAHL_LINKEINTRAEGE );
    }

    public String getTitel() {
        
        return _titel;
    }

    public void setTitel( String titel ) {
        
        this._titel = titel;
    }

    public String getBeschreibung() {
        
        return _beschreibung;
    }

    public void setBeschreibung( String beschreibung ) {
        
        _beschreibung = beschreibung;
    }

    public List<LinkEintrag> getLinkEintragList() {
        
        return _linkEintragList;
    }

    public void setLinkEintragList( List<LinkEintrag> linkEintragList ) {
        
        _linkEintragList = linkEintragList;
    }
    
    public void addLinkEintraege( LinkEintrag... linkEintrage ) {
        
        if ( _linkEintragList == null ) {
            
            _linkEintragList = new ArrayList<>( DEFAULT_ANZAHL_LINKEINTRAEGE );
        }

        for ( LinkEintrag linkEintrag : linkEintrage ) {
            
            _linkEintragList.add( linkEintrag );
        }
    }

    public void setId( String id ) {
        
        _id = id;
    }
    
    public String getId() {
        
        return _id;
    }

    @Override
    public int hashCode() {

        return Objects.hash( _id, _titel, _beschreibung, _linkEintragList );
    }
    
    @Override
    public boolean equals( Object obj ) {

        if ( this == obj ) {
            
            return true;
        }
        
        if ( obj == null ) {
            
            return false;
        }
            

        if ( obj instanceof LinkBaum that ) {
            
            return Objects.equals( _titel          , that._titel           ) && 
                   Objects.equals( _beschreibung   , that._beschreibung    ) &&
                   Objects.equals( _linkEintragList, that._linkEintragList );
        } else {
            
            return false;
        }
    }
    
    @Override
    public String toString() {

        return "LinkBaum \"" + _titel + "\"";
    }

}


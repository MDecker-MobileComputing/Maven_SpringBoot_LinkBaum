package de.eldecker.spring.linkbaum.db.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.redis.core.RedisHash;

import de.eldecker.spring.linkbaum.logik.LinkBaumService;


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
     * "LinkBaum:abc"
     */
    @Id
    private String _id;
    
    /** Titel enthält Name Influencer/Organisation oder Thema. */
    private String _titel;
    
    /** Beschreibungstext, z.B. mit Kurzvorstellung des Influencers. */
    private String _beschreibung;

    /** Gesamtanzahl der Aufrufe des Link-Baums. */
    private int _zugriffszaehler = 0;
    
    /** Eigentliche Links. */
    private List<LinkEintrag> _linkEintragList;

    /** 
	 * Version muss selbst verwaltet werden (Bug von <i>Spring Data Redis</i>?),
	 * siehe Methode {@link LinkBaumService#saveMitVersion(LinkBaum)}.
     */
    @Version
    private Long _version;
        
    
    /**
     * Default-Konstruktor.
     */
    public LinkBaum() {

    	this( "", "", "" );    	
    }
    
    public LinkBaum( String id, String titel, String beschreibung ) {
        
        _id           = id;
        _titel        = titel;
        _beschreibung = beschreibung;
        
        _linkEintragList = new ArrayList<>( DEFAULT_ANZAHL_LINKEINTRAEGE );
    }

    public void setId( String id ) {
        
        _id = id;
    }
    
    public String getId() {
        
        return _id;
    }    

    public String getTitel() {
        
        return _titel;
    }

    public void setTitel( String titel ) {
        
        _titel = titel;
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
    
    public int getZugriffszaehler() {

        return _zugriffszaehler;
    }

    public void setZugriffszaehler( int zugriffszaehler ) {

        _zugriffszaehler = zugriffszaehler;
    }
    
    public Long getVersion() {

        return _version;
    }
    
    public void setVersion( Long version ) {

        _version = version;
    }

    
    /**
     * Convenience-Methode, um einen oder mehrere LinkEinträge
     * hinzuzufügen.
     * 
     * @param linkEintrage Link-Einträge, die hinzuzufügen sind
     */
    public void addLinkEintraege( LinkEintrag... linkEintrage ) {
        
        if ( _linkEintragList == null ) {
            
            _linkEintragList = new ArrayList<>( DEFAULT_ANZAHL_LINKEINTRAEGE );
        }

        for ( LinkEintrag linkEintrag : linkEintrage ) {
            
            _linkEintragList.add( linkEintrag );
        }
    }

    
    @Override
    public int hashCode() {
    	
    	return Objects.hash( _id, 
    			             _beschreibung, 
    			             _zugriffszaehler, 
    			             _linkEintragList 
    			           );    	
    }

    
    @Override
    public boolean equals( Object obj ) {
    	
    	if ( obj == null ) { return false; }
    	if ( obj == this ) { return true;  }
    	
    	if ( obj instanceof LinkBaum that ) {
    		
    		return _id.equals(              that._id              ) &&
    			   _beschreibung.equals(    that._beschreibung    ) &&
    			   _linkEintragList.equals( that._linkEintragList ) &&
     			   _zugriffszaehler == that._zugriffszaehler;

    	} else {
    		
    		return false;
    	}
    }
    
    
    /**
     * String-Repräsentation des aufrufenden Objekts.
     * 
     * @return String mit Schlüssel und Titel des Link-Baums.
     *         Beispiel:
     *         <pre>Link-Baum mit ID=droidev: "Android-Entwicklung"</pre> 
     */
    @Override
    public String toString() {

        return String.format( "Link-Baum mit ID={}: \"{}\"", _id, _titel );        		
    }

}


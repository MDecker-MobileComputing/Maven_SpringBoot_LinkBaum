package de.eldecker.spring.linkbaum.db.model;

import java.io.Serializable;
import java.util.Objects;


/**
 * Repräsentiert einen Eintrag in einem Link-Baum, also z.B.
 * einen Link auf ein Social-Media-Profil.
 * <br><br>
 * 
 * Muss Serializable sein, damit es in Redis gespeichert werden kann.
 */
@SuppressWarnings("serial")
public class LinkEintrag implements Serializable {

    /** Titel des Links, z.B. "Facebook-Profil". */
    private String _linkTitel;
    
    /** 
     * URL des Links, z.B. "https://www.facebook.com/username".
     * <br><br>
     * 
     * Wir können hier nicht die Klasse {@code URL} verwenden, da diese
     * nicht voll serialisierbar ist. */
    private String _url;

    
    public LinkEintrag() {
        
       _linkTitel = "";
       _url = null;
    }
    
    public LinkEintrag( String linkTitel, String url ) {
        
        _linkTitel = linkTitel;
        _url       = url;
    }

    public String getLinkTitel() {
        
        return _linkTitel;
    }

    public void setLinkTitel( String linkTitel ) {
        
        _linkTitel = linkTitel;
    }

    public String getUrl() {
        
        return _url;
    }

    public void setUrl( String url ) {
        
        _url = url;
    }
    
    
    @Override
    public int hashCode() {

        return Objects.hash( _linkTitel, _url );
    }

    
    @Override
    public boolean equals( Object obj ) {

    	if ( obj == null ) { return false; }
    	if ( obj == this ) { return true;  }

        if ( obj instanceof LinkEintrag that) {
            
            return Objects.equals( _linkTitel, that._linkTitel ) && 
                   Objects.equals( _url      , that._url       );

        } else {
         
            return true;
        }
    }

    
    @Override
    public String toString() {
        
        return "LinkEintrag [linkTitel=" + _linkTitel + ", url=" + _url + "]";
    }

}

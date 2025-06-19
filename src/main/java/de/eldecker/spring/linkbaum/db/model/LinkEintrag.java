package de.eldecker.spring.linkbaum.db.model;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
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
    
    /** URL des Links, z.B. "https://www.facebook.com/username". */
    private URL _url;
    
    public static LinkEintrag erzeugeLinkEintrag( String linkTitel, 
                                                  String urlString ) 
                             throws MalformedURLException {

        final URL url = URI.create( urlString ).toURL(); // throws MalformedURLException
        
        return new LinkEintrag( linkTitel, url );
    }
    
    public LinkEintrag() {
        
       _linkTitel = "";
       _url = null;
    }
    
    public LinkEintrag( String linkTitel, URL url ) {
        
        _linkTitel = linkTitel;
        _url = url;
    }

    public String getLinkTitel() {
        
        return _linkTitel;
    }

    public void setLinkTitel( String linkTitel ) {
        
        _linkTitel = linkTitel;
    }

    public URL getUrl() {
        
        return _url;
    }

    public void set_url( URL url ) {
        
        _url = url;
    }
    
    @Override
    public String toString() {
        
        return "LinkEintrag [linkTitel=" + _linkTitel + ", url=" + _url + "]";
    }
    
    @Override
    public int hashCode() {

        return Objects.hash( _linkTitel, _url );
    }
    
    @Override
    public boolean equals( Object obj ) {

        if (this == obj) {
            
            return true;
        }

        if ( obj instanceof LinkEintrag that) {
            
            return Objects.equals( _linkTitel, that._linkTitel ) && 
                   Objects.equals( _url      , that._url       );

        } else {
         
            return true;
        }
    }
    
}

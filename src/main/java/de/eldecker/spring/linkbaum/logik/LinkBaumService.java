package de.eldecker.spring.linkbaum.logik;

import de.eldecker.spring.linkbaum.db.LinkBaumRepo;

import de.eldecker.spring.linkbaum.db.model.LinkBaum;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;


/**
 * Bean-Klasse für Geschäftslogik rund um den LinkBaum.
 */
@Service
public class LinkBaumService {

    private final static Logger LOG = LoggerFactory.getLogger( LinkBaumService.class );

    /** 
     * Maximale Anzahl an Versuchen, den Zugriffs-Zähler zu erhöhen, bevor aufgegeben wird. 
     * Wegen des "Optimistic Locking" kann es passieren, dass der Zugriffs-Zähler nicht erhöht 
     * werden kann.
     */
    private static final int MAX_ANZAHL_INKREMENT_VERSUCHE = 3;

    /** Repo-Bean für Zugriff auf Link-Baum-Objekte in Redis. */
    @Autowired
    private LinkBaumRepo _linkBaumRepo;

    
    /**
     * {@code LinkBaum}-Objekt speichern und dabei Version auf {@code 1} setzen oder
     * um {@code +1} erhöhen.
     * 
     * @param linkBaum Objekt, das gespeichert werden soll.
     * 
     * @return Objekt mit gesetzter oder erhöhter Version. 
     * 
     * @throws OptimisticLockingFailureException Zugriffs-Zähler konnte nicht erhöht werden, weil
     *                                           ein anderer Thread den Link-Baum zwischenzeitlich 
     *                                           geändert hat.
     */
    public LinkBaum saveMitVersion( LinkBaum linkBaum ) throws OptimisticLockingFailureException {
    	
    	final Long versionAlt = linkBaum.getVersion();
    	if ( versionAlt == null ) {
    		
    		linkBaum.setVersion( 1L );
    		
    	} else {
    		
    		final Long versionNeu = versionAlt + 1;
    		linkBaum.setVersion( versionNeu );
    	}
    	
    	return _linkBaumRepo.save( linkBaum ); // throws OptimisticLockingFailureException
    }

    
    /**
     * Erhöht den Zugriffs-Zähler für den LinkBaum mit der angegebenen ID um {@code +1}.
     * 
     * @param linkBaumKey Schlüssel des Link-Baums, dessen Zugriffs-Zähler erhöht werden soll.
     */
    public void erhoeheZugriffsZaehler( String linkBaumKey ) {

        for ( int i = 1; i <= MAX_ANZAHL_INKREMENT_VERSUCHE; i++ ) {
            
            try {
                
                final int ergebnis = einInkrementVersuch( linkBaumKey ); // throws OptimisticLockingFailureException
                
                if ( ergebnis == -1 ) {
                    
                    LOG.error( "Link-Baum mit Key=\"{}\" für Erhöhung Zähler nicht gefunden.", linkBaumKey );
                    return;
                    
                } else {
                    
                    LOG.info("Zugriffszähler für Link-Baum mit Key=\"{}\" erhöht auf {}.",
                            linkBaumKey, ergebnis );
                    return;
                }
            }
            catch ( OptimisticLockingFailureException ex ) {
                
                LOG.warn( "Exception beim Erhöhen des Zugriffs-Zählers für Link-Baum mit Key=\"{}\" (Versuch {}/{}).", 
                          linkBaumKey, i, MAX_ANZAHL_INKREMENT_VERSUCHE );
            }
        }
        
        LOG.error(
                "Zugriffszähler für Link-Baum mit Key=\"{}\" konnte nicht innerhalb von {} Versuchen erhöht werden.",
                linkBaumKey, MAX_ANZAHL_INKREMENT_VERSUCHE );
    }
    
    
    /**
     * Einzelner Versuch, den Zugriffs-Zähler für den Link-Baum mit {@code linkBaumKey} zu erhöhen.
     * 
     * @param linkBaumKey Schlüssel des Link-Baums, dessen Zugriffs-Zähler erhöht werden soll.
     * 
     * @return Zahl der Zugriffe nach der Erhöhung, oder {@code -1}, wenn kein Link-Baum mit
     *         {@code linkBaumId} gefunden wurde.
     *  
     * @throws OptimisticLockingFailureException Zugriffs-Zähler konnte nicht erhöht werden, weil
     *                                           ein anderer Thread den Link-Baum zwischenzeitlich 
     *                                           geändert hat.
     */
    private int einInkrementVersuch( String linkBaumKey ) throws OptimisticLockingFailureException {
        
        final Optional<LinkBaum> linkBaumOptional = _linkBaumRepo.findById( linkBaumKey );
        if ( linkBaumOptional.isEmpty() ) {

            return -1;  
        } 
        
        final LinkBaum linkBaum = linkBaumOptional.get();
        
        final int zaehlerVorher  = linkBaum.getZugriffszaehler();
        final int zaehlerNachher = zaehlerVorher + 1;
        linkBaum.setZugriffszaehler( zaehlerNachher );
        
        saveMitVersion( linkBaum ); // throws OptimisticLockingFailureException
        
        return zaehlerNachher;
    }
    
}

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

    @Autowired
    private LinkBaumRepo _linkBaumRepo;


    /**
     * Erhöht den Zugriffs-Zähler für den LinkBaum mit der angegebenen ID um {@code +1}.
     * 
     * @param linkBaumKey Schlüssel des Link-Baums, dessen Zugriffs-Zähler erhöht werden soll.
     * 
     * @return Zahl der Zugriffe nach der Erhöhung, oder {@code -1}, wenn kein LinkBaum 
     *         mit {@code linkBaumId} gefunden wurde, oder {@code -2} wenn die maximale Anzahl
     *         von Versuchen zum Erhöhen des Zugriffs-Zählers erreicht wurde, der neue
     *         Wert auf der Datenbank aber nicht gespeichert werden konnte.
     */
    public int erhoeheZugriffsZaehler( String linkBaumKey ) {

        final Optional<LinkBaum> linkBaumOptional = _linkBaumRepo.findById( linkBaumKey );
        if ( linkBaumOptional.isEmpty() ) {

            LOG.error( "Kein LinkBaum mit ID={} gefunden.", linkBaumKey );
            return -1;

        } else {


            for ( int i = 1; i <= MAX_ANZAHL_INKREMENT_VERSUCHE; i++ ) {

                try {

                    // Versuche, den Zugriffs-Zähler zu erhöhen
                    final LinkBaum linkBaum = linkBaumOptional.get();
                    linkBaum.setZugriffsZaehler( linkBaum.getZugriffsZaehler() + 1 );
                    _linkBaumRepo.save( linkBaum );

                    return linkBaum.getZugriffsZaehler();
                }
                catch ( OptimisticLockingFailureException ex ) {

                    LOG.warn( "OptimisticLockingFailureException beim Erhöhen des Zugriffs-Zählers für LinkBaum mit ID={} (Versuch {}/{}).", 
                              linkBaumKey, i, MAX_ANZAHL_INKREMENT_VERSUCHE );
                }                
            } // for

            LOG.error( "Gebe Versuche, den Zugriffs-Zähler für LinkBaum mit ID={} zu erhöhen, auf. ", linkBaumKey );
            return -2;
        }
    }
}

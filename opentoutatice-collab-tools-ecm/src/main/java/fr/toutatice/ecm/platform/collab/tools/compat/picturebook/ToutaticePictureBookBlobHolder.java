/*
 * (C) Copyright 2014 Académie de Rennes (http://www.ac-rennes.fr/), OSIVIA (http://www.osivia.com) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 *
 * Contributors:
 * mberhaut1
 * 
 */
package fr.toutatice.ecm.platform.collab.tools.compat.picturebook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.NuxeoException;
import org.nuxeo.ecm.platform.picture.api.adapters.PictureBookBlobHolder;

/**
 * Cette surcharge a été rendue nécessaire pour le bon fonctionnement de la vue "toutatice_view" sur un document de type "PictureBook".
 * Le ticket Jira suivant identifie le souci: https://jira.nuxeo.com/browse/SUPNXP-7421
 */
public class ToutaticePictureBookBlobHolder extends PictureBookBlobHolder {

    private static final Log log = LogFactory.getLog(ToutaticePictureBookBlobHolder.class);

    public ToutaticePictureBookBlobHolder(DocumentModel doc, String xPath) {
        super(doc, xPath);
    }

    @Override
    public Blob getBlob() throws NuxeoException {
        Blob b = null;

        try {
            b = super.getBlob();
        } catch (Exception e) {
            log.warn("Failed to get the blob item from the document '" + this.doc.getTitle() + "', error: " + e.getMessage());
        }

        return b;
    }

}

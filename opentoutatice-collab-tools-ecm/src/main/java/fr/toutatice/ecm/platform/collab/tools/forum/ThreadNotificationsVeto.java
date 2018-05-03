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
 * dchevrier
 * lbillon
 */
package fr.toutatice.ecm.platform.collab.tools.forum;

import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.event.DocumentEventTypes;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.impl.DocumentEventContext;
import org.nuxeo.ecm.platform.ec.notification.NotificationListenerVeto;


/**
 * @author David Chevrier.
 *
 */
public class ThreadNotificationsVeto implements NotificationListenerVeto {

    /**
     * Stop Thread modified event (temporay solution waiting new Forum model).
     */
    @Override
    public boolean accept(Event event) {
        boolean accept = true;

        DocumentEventContext docCtx = (DocumentEventContext) event.getContext();
        DocumentModel sourceDocument = docCtx.getSourceDocument();
        String type = sourceDocument.getType();

        String eventName = event.getName();

        if (DocumentEventTypes.DOCUMENT_UPDATED.equals(eventName) && ("Thread".equals(type))) {
            accept = false;
        }

        return accept;
    }

}

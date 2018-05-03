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
 *   mberhaut1
 *    
 */
package fr.toutatice.ecm.platform.collab.tools.forum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.nuxeo.ecm.core.api.NuxeoException;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.platform.forum.web.PostActionBean;
import org.nuxeo.ecm.platform.task.Task;
import org.nuxeo.ecm.platform.task.core.service.TaskEventNotificationHelper;

import fr.toutatice.ecm.platform.core.constants.ExtendedSeamPrecedence;


@Name("postAction")
@Scope(ScopeType.CONVERSATION)
@Install(precedence = ExtendedSeamPrecedence.TOUTATICE)
public class ToutaticePostActionBean extends PostActionBean {

    /** Post's Blobs (files schema). */
    private List<Map<String, Serializable>> blobs = new ArrayList<>(0);

    /**
     * @return the blobs
     */
    public List<Map<String, Serializable>> getBlobs() {
        return blobs;
    }

    /**
     * @param blobs the blobs to set
     */
    public void setBlobs(List<Map<String, Serializable>> blobs) {
        this.blobs = blobs;
    }

    private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void startModeration(DocumentModel post) throws NuxeoException {
		super.startModeration(post);
		
		DocumentModel thread = getParentThread();
		ArrayList<String> moderators = (ArrayList<String>) thread.getProperty("thread", "moderators");
        String[] listmoderators = new String[moderators.size()];
        int i = 0;
        for (String moderator : moderators) {
        	listmoderators[i++] = moderator;
        }
		
		// notification pour modération du nouveau commentaire/post par les modérateurs
        Map<String, Serializable> properties = new HashMap<String, Serializable>();
        properties.put("recipients", listmoderators);
        Task currentTask = getModerationTask(thread, post.getId());
        TaskEventNotificationHelper.notifyEvent(documentManager, thread, (NuxeoPrincipal) currentUser, currentTask, "ForumCommentPublication", properties, "", null);
	}
	
}

package com.yunussandikci.GithubImporter.Models.GithubAPI;

/*******************************************************************************
 *  Copyright (c) 2011 GitHub Inc.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *    Kevin Sawicki (GitHub Inc.) - initial API and implementation
 *******************************************************************************/

import org.springframework.http.ResponseEntity;

public class GithubLinks {

    private static final String DELIM_LINKS = ",";
    private static final String DELIM_LINK_PARAM = ";";
    private String first;
    private String last;
    private String next;
    private String prev;

    GithubLinks(ResponseEntity response ){
        String linkHeader = response.getHeaders().get("Link") == null ? null : response.getHeaders().get("Link").get(0);
        if (linkHeader != null) {
            String[] links = linkHeader.split(DELIM_LINKS);
            for (String link : links) {
                String[] segments = link.split(DELIM_LINK_PARAM);
                if (segments.length < 2)
                    continue;

                String linkPart = segments[0].trim();
                if (!linkPart.startsWith("<") || !linkPart.endsWith(">"))
                    continue;
                linkPart = linkPart.substring(1, linkPart.length() - 1);

                for (int i = 1; i < segments.length; i++) {
                    String[] rel = segments[i].trim().split("=");
                    if (rel.length < 2 || !"rel".equals(rel[0]))
                        continue;

                    String relValue = rel[1];
                    if (relValue.startsWith("\"") && relValue.endsWith("\""))
                        relValue = relValue.substring(1, relValue.length() - 1);

                    if ("first".equals(relValue))
                        first = linkPart;
                    else if ("last".equals(relValue))
                        last = linkPart;
                    else if ("next".equals(relValue))
                        next = linkPart;
                    else if ("prev".equals(relValue))
                        prev = linkPart;
                }
            }
        }
    }

    /**
     * @return first
     */
    public String getFirst() {
        return first;
    }

    /**
     * @return last
     */
    public String getLast() {
        return last;
    }

    /**
     * @return next
     */
    public String getNext() {
        return next;
    }

    /**
     * @return prev
     */
    public String getPrev() {
        return prev;
    }
}

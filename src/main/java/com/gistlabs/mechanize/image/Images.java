/**
 * Copyright (C) 2012 Gist Labs, LLC. (http://gistlabs.com)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.gistlabs.mechanize.image;

import java.io.OutputStream;
import java.util.List;

import com.gistlabs.mechanize.Node;
import com.gistlabs.mechanize.Page;
import com.gistlabs.mechanize.PageElements;
import com.gistlabs.mechanize.util.NullOutputStream;
import com.gistlabs.mechanize.util.Util;

/** 
 * A collection of Image objects.  
 * 
 * @author Martin Kersten<Martin.Kersten.mk@gmail.com>
 * @version 1.0
 * @since 2012-09-12
 */
public class Images extends PageElements<Image> {
	
	public Images(Page page) {
		this(page, newEmptyList());
	}

	private static List<? extends Node>newEmptyList() {
		return Util.newEmptyList();
	}
	
	public Images (Page page, List<? extends Node> forms) {
		super(page, forms);
	}

	@Override
	protected Image newRepresentation(Node node) {
		return new Image(page, node);
	}
	
	public ImageCollection loadAll() {
		return loadAllMissing(new ImageCollection());
	}
	
	/** Returns the given image collection after loading all missing images that are present within the 
	 *  page but not marked as being already loaded within the image collection. */
	public ImageCollection loadAllMissing(ImageCollection imageCollection) {
		for(Image image : this) {
			if(!imageCollection.hasLoaded(image)) {
				OutputStream out = new NullOutputStream();
				getPage().getAgent().get(image.getAbsoluteSrc()).saveTo(out);
				imageCollection.markAsLoaded(image);
			}
		}
		return imageCollection;
	}
}
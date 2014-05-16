/*Copyright 2014 Santi Muñoz Mallofre
 
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/
package com.planfeed.elements;

//@XmlRootElement
public class PointOfAgenda {

	private String name;
	
	/**
	 * Duració des de l'ultim canvi.
	 */
	private long duration;
	
	/**
	 * Duració original del punt. Per poder restaurarlo en cas de Stop.
	 */
	private long originalDuration;
	private String comment;
	private String pointId=null;
	private boolean open;
	

	public PointOfAgenda(){
		
	}

	public PointOfAgenda(String pointId, String name, long duration,long originalDuration, String comment) {
		super();
		this.pointId=pointId;
		this.name = name;
		this.duration = duration;
		this.originalDuration=originalDuration;
		this.comment = comment;
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPointId() {
		return pointId;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
	public boolean getOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public long getOriginalDuration() {
		return originalDuration;
	}

	public void setOriginalDuration(long originalDuration) {
		this.originalDuration = originalDuration;
	}
	
}

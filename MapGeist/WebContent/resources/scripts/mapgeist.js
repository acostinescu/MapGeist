L.Marker.include({
	setEventId: function(eventid){
		
		if(eventid){
			
			this._icon.dataset.eventid = eventid;
		} 
	}
});


function setEventListHover(listItem) {
	listItem.onmouseenter = function() {
		
		var id = this.getAttribute("data-eventid");
		var selector = ".leaflet-marker-icon[data-eventid='" + id + "']"
		
		var eventMarker = document.querySelector(selector);
		if(eventMarker != null) {
			eventMarker.style["transform"] += " translateY(-100%) translateX(-50%) scale(2)";
			eventMarker.style["filter"] = "hue-rotate(250deg)"; 
		}
	}
	
	listItem.onmouseleave = function(){
		var id = this.getAttribute("data-eventid");
		var selector = ".leaflet-marker-icon[data-eventid='" + id + "']"
		
		var eventMarker = document.querySelector(selector);
		if(eventMarker != null){
			eventMarker.style["transform"] = eventMarker.style["transform"].replace(" translateY(-100%) translateX(-50%) scale(2)", "");
			eventMarker.style["filter"] = "";
		}
	}
}
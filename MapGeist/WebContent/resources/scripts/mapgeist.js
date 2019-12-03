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

Date.prototype.getShortTime = function() {
	var hh = this.getHours();
	var mm = this.getMinutes();
	
	return [
		(hh > 9 ? '' : '0') + hh,
		(mm > 9 ? '' : '0') + mm
	].join(':');
}

Date.prototype.getShortDate = function() {
	var mm = this.getMonth() + 1;
	var dd = this.getDay();
	var yy = this.getFullYear();
	
	return [
		(mm > 9 ? '' : '0') + mm,
		(dd > 9 ? '' : '0') + dd,
		yy
	].join('/');
}

function formatDateTime(startTime, endTime) {
	
	var formatted = "";
	startTime = new Date(startTime);
	
	if(endTime != "null")
	{
		endTime = new Date(endTime);
		
		if(startTime.getShortDate() == endTime.getShortDate())
		{
			formatted += "From " + startTime.getShortTime();
			formatted += " to " + endTime.getShortTime();
			formatted += " on " + startTime.getShortDate();
		}
		else
		{
			formatted += "From " + startTime.getShortTime();
			formatted += " on " + startTime.getShortDate();
			formatted += " to " + endTime.getShortTime();
			formatted += " on " + endTime.getShortDate();
		}
	}
	else
	{
		formatted += "Starting at " + startTime.getHours() + ":" + startTime.getMinutes();
		formatted += " on " + startTime.toDateString();
	}

	return formatted;
}

const types = ["default", "success"];

function createAlert(content, type="default") {
	
	if(!content){
		console.err("Error: An alert must have content.");
		return;
	}
	
	if(!types.includes(type)){
		console.err("Error: Invalid alert type");
		return;
	}
	
	var alertBox = document.createElement("div");
	alertBox.className = "alert alert__" + type;
	alertBox.innerText = content;
	
	var closeButton = document.createElement("a");
	closeButton.className = "alert--close";
	closeButton.innerText = '\u00D7';
	closeButton.addEventListener("click", function(){
		removeAlert(alertBox);
	})
	alertBox.append(closeButton);

	document.body.append(alertBox);
	
	alertBox.timeout = setTimeout(function(){
		removeAlert(alertBox);
	}, 10000);
}

function removeAlert(alertBox) {
	alertBox.className += " alert__remove";
	var newAlert = alertBox.cloneNode(true);
	
	alertBox.parentNode.replaceChild(newAlert, alertBox);
	
	setTimeout(function(){
		newAlert.remove();
	}, 200);
	
	if(alertBox.timeout) clearTimeout(alertBox.timeout)
}






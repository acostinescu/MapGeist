<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
	<head>
		<title>MapGeist</title>
		
		<!-- FontAwesome -->
		<script src="https://kit.fontawesome.com/2d5aa19ad7.js" crossorigin="anonymous"></script>

		<!-- Leaflet -->
		<link rel="stylesheet" href="https://unpkg.com/leaflet@1.5.1/dist/leaflet.css" integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ==" crossorigin=""/>
		<script src="https://unpkg.com/leaflet@1.5.1/dist/leaflet.js" integrity="sha512-GffPMF3RvMeYyc1LWMHtK8EbPv0iNZ8/oTtHPx9/cc2ILxQ+u905qIwdpULaqDkyBKgOaB57QTMg7ztg8Jm2Og==" crossorigin=""></script>

	   	<!-- tail.DateTime -->
		<script src="<c:url value="/resources/scripts/tail.datetime-full.min.js"/>"></script> 
	  	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/tail.datetime-default-green.min.css" />" />
		   
		<!-- Favicon -->   
		<link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico"/>" />
		   
	   	<!-- MapGeist Resources -->
		<link rel="stylesheet" href="<c:url value="/resources/styles/styles.css?v=2"  />" />
		<script src="<c:url value="/resources/scripts/mapgeist.js"  />" type="text/javascript"></script>
	</head>
	<body>

		<div id="input-container">
			<h3 class="popup--title">Add Event</h3>
			<form id='newEvent' action="<c:url var="newEvent" value="/Event/new" />" method="POST">
				<div class="form--group">
					<input class="form--control" type="text" id="Title" placeholder="Title" name="Title">
				</div> 
				<div class="form--group">
					<input class="form--control" type="text" id="Description" placeholder="Description" name="Description">
				</div>
				<div class="form--group">
					<input class="form--control" type="text" id="Location" placeholder="Location" name="Location">
				</div>
				<div class="form--group">
					<input class="form--control" type="text" id="Email" placeholder="Email Address" name="Email">
				</div> 
				<div class="form--group" id="StartGroup">
					<input class="form--control" type="text" id="StartTime" placeholder="Start Time" name="StartTime">
				</div>
				<div class="form--group" id="EndGroup">
					<input class="form--control" type="text" id="EndTime" placeholder="End Time" name="EndTime">
				</div>
				
				<input type="hidden" id="Longitude" name="Longitude">
				<input type="hidden" id="Latitude" name="Latitude">
				
				<a onclick="submitNewEvent()" class="btn">Create Event</a>
				
				<div id="postResponseForm"></div>
			</form>	
		</div>
		
		<div class="page-container">
			<section class="map-container">
				<div id="leafletMap"></div>
				<img class="mapgeist--logo" src="<c:url value="/resources/images/logo.svg" />" />
			</section>
			<section class="sidebar">
				<div class="sidebar--static">
					<h2 class="sidebar--title">Upcoming Events</h2>
				</div>
				<div class="sidebar--scroll" id="scroll">
					<div id="eventBar"></div>
				</div>
			</section>
		</div>

		<c:url var="activeEvents" value="/Event/active" ></c:url>
		
	  	<script type="text/javascript">
	  	
	  		// DateTime Pickers
		  	tail.DateTime("#StartTime", {});
		    tail.DateTime("#EndTime", {});
	  	
	  		// Create a Leaflet map centered on Boulder
		  	var map = L.map('leafletMap', {
		  		doubleClickZoom: false
		  	}).setView([40.006463, -105.265991], 15);
		  	
	  		// Tile layer for the Leaflet map
		 	L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibHVzdzYxMjYiLCJhIjoiY2oxbDdxc3BxMDAwcTMybDI2M28wM3VnaiJ9.2Oop3pz_TvNkmyOBvWWA7A', {
		        maxZoom: 18,
		        attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery � <a href="http://mapbox.com">Mapbox</a>',
		        id: 'mapbox.streets'
		    }).addTo(map);
		    
		    // Add marker and pop open dialog on click
		    var popupForm = document.createElement('div');
		    var inputForm = document.querySelector("#input-container");
		    popupForm.append(inputForm);
		    var marker = null;
		    function onMapClick(e){
		    	if(marker != null){
			    	marker.remove(map);
			    }
		    	marker = L.marker(e.latlng).bindPopup(popupForm).on("popupclose", onPopupClose).addTo(map).openPopup();
		    	document.getElementById("Longitude").value = e.latlng.lng;
		    	document.getElementById("Latitude").value = e.latlng.lat;
		    }
		    function onPopupClose(e){
		    	if(marker != null){
		    		marker.remove(map);
		    	}
		    }
		    map.on("dblclick", onMapClick);

		    
		    
		    
		    // Request the active events from the server
		    var xhttp = new XMLHttpRequest();
		    xhttp.onreadystatechange = function(){
		    	if (this.readyState == 4 && this.status == 200) {
		    		var eventArr = JSON.parse(this.responseText);
		    		addEventMarkers(eventArr);
	    	    }
		    }
		    xhttp.open("GET", "${activeEvents}", true);
		    xhttp.send();
		    
		    var markers = {};
		    // Add the requested events to the map
		    function addEventMarkers(eventArr){
		    	var eventList = document.createElement("ul");
		    	eventList.className = "event-list";		    	
		    	for(var i = 0; i < eventArr.length; i++){
		    		
		    		// Create the marker and set its hover behavior
					markers[eventArr[i].id] = L.marker([eventArr[i].latitude, eventArr[i].longitude]).addTo(map);
		    		markers[eventArr[i].id].setEventId(eventArr[i].id);
		    		setMarkerHover(markers[eventArr[i].id]);
		    				    		
		    		// Create li container
		    		var listItem = document.createElement("li");
		    		listItem.className = "event-item";
		    		
		    		// Event ID
		    		listItem.dataset.eventid = eventArr[i].id;
		    		
		    		// Set hover
		    		setEventListHover(listItem, map, markers);
		    		
		    		// Event title
		    		var eventTitle = document.createElement("h3");
		    		eventTitle.innerText = eventArr[i].title;
		    		listItem.append(eventTitle);
		    		
		    		// Event description
		    		var eventDescription = document.createElement("p");
		    		eventDescription.innerText = eventArr[i].description;
		    		listItem.append(eventDescription);
		    		
		    		// Event location
		    		var eventLocation = document.createElement("p");
		    		eventLocation.className = "event--info";
		    		eventLocation.innerHTML = '<i class="event--icon fas fa-map-marker-alt"></i>' + eventArr[i].location; 
		    		listItem.append(eventLocation);
		    		
		    		// Event time
		    		var eventTime = document.createElement("p");
		    		eventTime.className = "event--info";
		    		eventTime.innerHTML = '<i class="event--icon fas fa-clock"></i>' + formatDateTime(eventArr[i].starttime, eventArr[i].endtime);
		    		listItem.append(eventTime);
		    		
		    		// Add the li container to the list
		    		eventList.append(listItem);		
		    		
		    	}
		    	
		    	document.getElementById("eventBar").append(eventList);
		    }

		    
		    // Submit the new event form
		    function submitNewEvent(e){
		    	var form = document.querySelector("#newEvent");
		    	var formData = new FormData(form)
		    	
		    	var formattedData = "";
		    	for (var [key, value] of formData.entries()) { 
		    		
		    		if(key == "StartTime" || key == "EndTime") value = value.replace(" ", "T");
		    		
			    	formattedData += encodeURIComponent(key) + "=" + encodeURIComponent(value) + "&";
				}
		    	
		    	var eventName = document.querySelector("#Title").value;
		    	
		    	var request = new XMLHttpRequest();
		    	request.onreadystatechange = function(){
			    	if (this.readyState == 4 && this.status == 200) {
			    		createAlert("Your event was successfully submitted for approval! Our moderators will get to it... eventually.", "success");			    
			    		if(marker != null){
			    			marker.remove(map);
			    			clearForm();
			    		}
			    	}
			    }
		    	
		    	request.open("POST", "${newEvent}", true);
		    	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		    	request.send(formattedData);
		    }
		    
		    
		    function clearForm(){
		    	var form = document.querySelector("#newEvent");
		    	var formElements = form.querySelectorAll("input");
		    	
		    	for(var formElem of formElements){
		    		formElem.value = "";
		    	}
		    }
		    
	  	</script>
	</body>
</html>
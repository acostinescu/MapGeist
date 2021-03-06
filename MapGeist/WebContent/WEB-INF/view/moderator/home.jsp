<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html> 
	<head>
		<title>MapGeist - Moderator</title>
		
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
		
		
		<div class="page-container">
			<section class="map-container">
				<div id="leafletMap"></div>
				<img class="mapgeist--logo" src="<c:url value="/resources/images/logo.svg" />" />
			</section> 
			<section class="sidebar">
				
				<div class="sidebar--static">
					<h2 class="sidebar--title">${message}</h2>
					<a class="btn" href="<c:url value="/login?logout" />">Log out</a> 
				</div>
				
				<div class="sidebar--scroll" id="scroll">
					<div id="eventBar"></div>
				</div>
			</section>
		</div>
		
		<c:url var="allEvents" value="/Event/queued"></c:url>
		<c:url var="reviewEvent" value="/Event/review"></c:url>

		<script type="text/javascript">
	  		var map = L.map('leafletMap').setView([40.006463, -105.265991], 15);
	  		
		 	L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibHVzdzYxMjYiLCJhIjoiY2oxbDdxc3BxMDAwcTMybDI2M28wM3VnaiJ9.2Oop3pz_TvNkmyOBvWWA7A', {
		        maxZoom: 18,
		        attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
		            '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
		            'Imagery � <a href="http://mapbox.com">Mapbox</a>',
		        id: 'mapbox.streets'
		    }).addTo(map);

		 	// Request the active events from the server
		    var xhttp = new XMLHttpRequest();
		    xhttp.onreadystatechange = function(){
		    	if (this.readyState == 4 && this.status == 200) {
		    		var eventArr = JSON.parse(this.responseText);
		    		addEventMarkers(eventArr);
	    	    }
		    }
		    xhttp.open("GET", "${allEvents}", true);
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
		    		
		    		// Buttons
		    		var buttonBox = document.createElement("div");
		    		buttonBox.className = "event--buttons";
		    		
		    		var approveButton = document.createElement("button");
		    		approveButton.className = "event--button button__approve";
		    		approveButton.textContent = "Approve";
					approveButton.addEventListener("click", function(){
						var id = this.closest(".event-item").dataset.eventid;
		    			reviewEvent(id, true, this.closest(".event-item"));
		    		});
		    		buttonBox.append(approveButton);
		    		
		    		
		    		var denyButton = document.createElement("button");
		    		denyButton.className = "event--button button__deny";
		    		denyButton.textContent = "Deny";
		    		denyButton.addEventListener("click", function(){
		    			var id = this.closest(".event-item").dataset.eventid;
		    			reviewEvent(id, false, this.closest(".event-item"));
		    		});
		    		buttonBox.append(denyButton);
		    		
		    		listItem.append(buttonBox);
		    		
		    		// Add the li container to the list
		    		eventList.append(listItem);
		    	}
		    	
		    	document.getElementById("eventBar").append(eventList);
		    }
		    
		    
		    function reviewEvent(id, approved, eventBlock){
		    	
		    	var request = new XMLHttpRequest();
		    	request.onreadystatechange = function(){
			    	if (this.readyState == 4 && this.status == 200) {
			    		if(approved){
			    			createAlert("Event approved.", "success");	
			    		}
			    		else{
			    			createAlert("Event denied.", "success");	
			    		}
			    		console.log("Removing Marker 4");
		    			var eventMarker = markers[id];
		    			map.removeLayer(eventMarker);
		    			eventBlock.parentNode.removeChild(eventBlock);
			    	}
			    }
		    	
		    	var formattedData = "id=" + encodeURIComponent(id) + "&";
		    	formattedData += "approved=" + encodeURIComponent(approved);
		    	
		    	request.open("POST", "${reviewEvent}", true);
		    	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		    	request.send(formattedData);
		    }
		    
		  </script>
	</body>
</html>
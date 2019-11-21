<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
	<head>
		<title>MapGeist</title>
		<link rel="stylesheet" href="<c:url value="/resources/styles/styles.css"  />" />
		<style>
			#mapid {
				display: inline-block;		
			    position: relative;
			    height: inherit;
			    width: inherit;
			    top: 0px;
			    left: 0px;
			    right: 0px;
			    bottom: 0px;
			    }
		    #map-container {
		    	display: inline-block;
			    position: relative;
			    height: 600px;
			    width: 600px;
			    float: right;
			}
			#input-container {
				display: inline-block;
				position: relative;
			    height: 50%;
			    width: 50%;
			    float: left;
			}
		</style>
		<link rel="stylesheet" href="https://unpkg.com/leaflet@1.5.1/dist/leaflet.css"
		   integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ=="
		   crossorigin=""/>
		   
		 <script src="https://unpkg.com/leaflet@1.5.1/dist/leaflet.js"
		   integrity="sha512-GffPMF3RvMeYyc1LWMHtK8EbPv0iNZ8/oTtHPx9/cc2ILxQ+u905qIwdpULaqDkyBKgOaB57QTMg7ztg8Jm2Og=="
		   crossorigin=""></script>
	</head>
	<body>
		<h1 style="text-align:center">Welcome To MapGeist!</h1>
		<div id = "input-container">
			<form id='newEvent' action="<c:url var="newEvent" value="/NewEventSubmit" />" method="POST">
				Event Title: <input type="text" id="Title" name="Title"><br><br>
				Event Description: <input type="text" name="Description"><br><br>
				Event Location: <input type="text" name="Location"><br><br>
				Event Start Time: <input type="datetime-local" name="StartTime"><br><br>
				Event End Time: <input type="datetime-local" name="EndTime"><br><br>
				Email Address: <input type="text" name="Email"><br><br>
				<input type="hidden" id="Longitude" name="Longitude">
				<input type="hidden" id="Latitude" name="Latitude">	
				<input type="submit">
				<div color="red" id="postResponseForm"></div>
			</form>	
		</div>
		
		<div id="test"></div>
		
		<div id = "map-container">
			<div id="mapid"></div>
		</div>
		
		<c:url var="activeEvents" value="/Event/active" ></c:url>
		
	  	<script type="text/javascript">
		  	var mymap = L.map('mapid').setView([40.006463, -105.265991], 15);
		 	L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibHVzdzYxMjYiLCJhIjoiY2oxbDdxc3BxMDAwcTMybDI2M28wM3VnaiJ9.2Oop3pz_TvNkmyOBvWWA7A', {
		        maxZoom: 18,
		        attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
		            '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
		            'Imagery � <a href="http://mapbox.com">Mapbox</a>',
		        id: 'mapbox.streets'
		    }).addTo(mymap);
		    var customOptions = {
		        'maxWidth': '500',
		        'className' : 'custom'
		    }
		    var domelem = document.createElement('a');
		    domelem.href = "#point_555_444";
		    domelem.innerHTML = "Click me";
		    
		    function onMapClick(e){
		    	console.log("Map clicked @" + e.latlng);
		    	document.getElementById("Longitude").value = e.latlng.lng;
		    	document.getElementById("Latitude").value = e.latlng.lat;
		    }
		    
		    mymap.on("click", onMapClick);
		    
		    var marker = L.marker(["40.006463", "-105.265991"]).bindPopup(domelem).addTo(mymap);   
		    
		    
		    
		    var xhttp = new XMLHttpRequest();
		    xhttp.onreadystatechange = function(){
		    	if (this.readyState == 4 && this.status == 200) {
		    		var eventArr = JSON.parse(this.responseText);
		    		addEventMarkers(eventArr);
		    	     
	    	    }
		    }
		    xhttp.open("GET", "${activeEvents}", true);
		    xhttp.send();
		    
		    //Handle the submit post by an asynchronous AJAX request.
		    var eventForm = document.getElementById('newEvent');
		    eventForm.onsubmit = function(event) {
		      event.preventDefault();
		    	
			    var newEventHTTP = new XMLHttpRequest();
			    
				var eventName = document.getElementById('Title').value;
				console.log(eventName);
				
			    newEventHTTP.onreadystatechange = function(){
			    	if (this.readyState == 4 && this.status == 200) {
			    		document.getElementById('postResponseForm').innerText="Event " + eventName + " submitted.";				    
			    	}
			    }
			    
			    var form = document.querySelector("#newEvent");
		    	var formData = new FormData(form);
		    	
		    	var data = ""
		    	
			    for (var [key, value] of formData.entries()) { 
					data += encodeURIComponent(key) + "=" + encodeURIComponent(value) + "&";
				}
			    
			    
			    newEventHTTP.open('POST', "${newEvent}", /* async = */ true);
			    newEventHTTP.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				console.log(data);
				newEventHTTP.send(data);
		    }
		    
		    function addEventMarkers(eventArr){
		    	var out = "";
		    	var i;
		    	for(i = 0; i < eventArr.length; i++){
		    		
		    		var marker = L.marker([eventArr[i].latitude, eventArr[i].longitude]).bindPopup(domelem).addTo(mymap);
		    	}
		    }
		    
	  	</script>
	</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html> 
	<head>
		<link rel="stylesheet" href="<c:url value="/resources/styles/styles.css"  />" />
		
		<link rel="stylesheet" href="https://unpkg.com/leaflet@1.5.1/dist/leaflet.css"
		   integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ=="
		   crossorigin=""/>
		   
		 <script src="https://unpkg.com/leaflet@1.5.1/dist/leaflet.js"
		   integrity="sha512-GffPMF3RvMeYyc1LWMHtK8EbPv0iNZ8/oTtHPx9/cc2ILxQ+u905qIwdpULaqDkyBKgOaB57QTMg7ztg8Jm2Og=="
		   crossorigin=""></script>
	</head>
	<body>
		
		
		<div class="page-container">
			<section class="map-container">
				<div id="leafletMap"></div>
				<img class="mapgeist--logo" src="<c:url value="/resources/images/logo.svg" />" />
			</section>
			<section class="sidebar">
				${message}
			</section>
		</div>
	
		<script type="text/javascript">
	  		var mymap = L.map('leafletMap').setView([40.006463, -105.265991], 15);
	  		
		 	L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibHVzdzYxMjYiLCJhIjoiY2oxbDdxc3BxMDAwcTMybDI2M28wM3VnaiJ9.2Oop3pz_TvNkmyOBvWWA7A', {
		        maxZoom: 18,
		        attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
		            '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
		            'Imagery © <a href="http://mapbox.com">Mapbox</a>',
		        id: 'mapbox.streets'
		    }).addTo(mymap);
		 	
		    var customOptions = {
		        'className' : 'custom'
		    }
		    
		    var domelem = document.createElement('a');
		    domelem.href = "#point_555_444";
		    domelem.innerHTML = "Click me";
		    domelem.onclick =  function myfunction(){
		       
	       	}
		    
		    var marker = L.marker([40.006463, -105.265991]).bindPopup(domelem).addTo(mymap);
		  </script>
	</body>
</html>
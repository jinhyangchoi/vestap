/****************************************************************
 *
 * 파일명 : map.js

 * 설  명 : 지도생성 JavaScript
 *
 *   수정일         	수정자           Function 명
 * ------------    ---------    ----------------------------
 * 2018.11.14	    최진향		최초 작성
 *
 *
 */

/*initShadow 음영
 * 
 * 
 * 
 * 
 * */
window.app = {};

var app = window.app;
var map;
var vestap_layer = 4;
var esti_map;
var expo_map;
var comp_base_map;
var comp_comp_map;
var custom_esti_map;
var whole_esti_map;
var scenario;
var sd_layer, sgg_layer, emd_layer;
var shadow_layer, shadow_source;
var shadow_layer_sgg, shadow_layer_sd;

/* 범례 컬러램프 */
var _colorType=4;
/* 범례 색상 단계 수 */
var _step = 10;
/* 범례 색상 변경 시 다시그리기*/
var _vector = new Object();

var level_1, level_2, level_3, level_4, level_5;
var source_1, source_2, source_3, source_4, source_5;
var fill_1, fill_2, fill_3, fill_4, fill_5, fill_6, fill_7, fill_8, fill_9, fill_10; 

//var serverIp = 'https://vestap.kei.re.kr/geoserver/'
var serverIp = 'http://172.30.1.56:8088/geoserver/'

$("#sidoList").change(function(){
	//moveSido("one");
});

$("#select-sd").change(function(){
	moveSido("two");
});

$("#sidoCList").change(function(){
	moveSido("three");
});

$("#sigunguList").change(function(){
	moveSigungu("one");
});

$("#select-sgg").change(function(){
	moveSigungu("two");
});

$("#sigunguCList").change(function(){
	moveSigungu("three");
});

$("#scenSectionList").change(function(){
	changeLayer(scenario);
});
$("#scenYearList").change(function(){
	changeLayer(scenario);
});

/*********************************************************************
 *	행정구역선택 시 레이어(시도) 이동
 *********************************************************************/
function resetZoom(){
	map = esti_map;
	var geom = new ol.geom.Point([128, 36]);
	map.getView().fit(geom.transform("EPSG:4326", "EPSG:3857"));
	map.getView().setZoom(7);
	updateMap();
}

/*********************************************************************
 *	행정구역선택 시 레이어(시도) 이동
 *********************************************************************/
function moveSido(type){
	if(type=="one"){
		var sido = $("#sidoList option:selected").val();
		var sigungu = $("#sigunguList option:selected").val();
		if(sido==null||sido==''){
			sido = $("#sido").val();
		}
		if(sigungu==null||sigungu==''){
			sigungu = $("#sigungu").val();
		}
	}else if(type=="two"){
		var sido = $("#select-sd option:selected").val();
		var sigungu = $("#select-sgg option:selected").val();
	}else if(type =="three"){
		var sido = $("#sidoCList option:selected").val();
		var sigungu = $("#sigunguCList option:selected").val();
	}
	
	var depth = $("#depth").val();
	var objs = depth.split("-");
	if(objs[0] == "admin"){
		
		if(objs[1] == "system"){
			map = system_esti_map;
		}else{
			map = whole_esti_map;
		}
		
	}else if(objs[0] == "climate"){
		
		if(objs[1] == "estimation"){
			map = esti_map;
			
		}else if(objs[1] == "comparison"){
			map = comp_base_map;
			if(type =="one"){
				map = comp_base_map;
			}
			if(type =="three"){
				map = comp_comp_map;
			}
			
		}else if(objs[1] == "exposure"){
			map = expo_map;
			
		}
		
	}else if(objs[0] == "custom"){
		map = custom_esti_map;
		
	}
	type = null;
	if(sido !="" && sido != undefined){
		var xhr = new XMLHttpRequest();
		var url = 'selectWfsLayer.do?service=WFS&version=1.1.0&request=GetFeature&typename=vestap:sd_layer&outputFormat=application/'
			+ 'json&srsname=EPSG:3857&maxFeatures=1&outputFormat=application/json&cql_filter=sd_cd=' + sido; //URL
		
		xhr.open('GET', url );
		xhr.setRequestHeader('Content-Type','application/json; charset=UTF-8');
		xhr.onreadystatechange = function () {
			
			if (this.readyState == 4) {
				
				if(this.status == 200) {
					
					var item = JSON.parse(this.responseText);
					//var test = jQuery.parseJSON(this.responseText);
					
					var features = new ol.format.GeoJSON().readFeatures(item);

					map.getView().fit(features[0].getGeometry());
				
				}
			}
		};
		
		xhr.send('');
	}
	else{
		var geom = new ol.geom.Point([128, 36]);
		map.getView().fit(geom.transform("EPSG:4326", "EPSG:3857"));
		map.getView().setZoom(7);
		
	}
}

/*********************************************************************
 *	행정구역선택 시 레이어(시군구) 이동
 *********************************************************************/
function moveSigungu(type){
	if(type=="one"){
		var sido = $("#sidoList option:selected").val();
		var sigungu = $("#sigunguList option:selected").val();
		if(sido==null||sido==''){
			sido = $("#sido").val();
		}
		if(sigungu==null||sigungu==''){
			sigungu = $("#sigungu").val();
		}
	}else if(type=="two"){
		var sido = $("#select-sd option:selected").val();
		var sigungu = $("#select-sgg option:selected").val();
	}else if(type=="three"){
		var sido = $("#sidoCList option:selected").val();
		var sigungu = $("#sigunguCList option:selected").val(); 
	}
	
	var depth = $("#depth").val();
	var objs = depth.split("-");
	if(objs[0] == "admin"){
		map = whole_esti_map;
		
	}else if(objs[0] == "climate"){
		
		if(objs[1] == "estimation"){
			map = esti_map;
			
		}else if(objs[1] == "comparison"){
			if(type =="three"){
				map = comp_comp_map;
			}else{
				map = comp_base_map;
			}
		}else if(objs[1] == "exposure"){
			map = expo_map;
			
		}
		
	}else if(objs[0] == "custom"){
		map = custom_esti_map;
		
	}
	
	if(sigungu != '' && sigungu != undefined && sigungu !='all'){
		var xhr = new XMLHttpRequest();
		var url = 'selectWfsLayer.do?service=WFS&version=1.1.0&request=GetFeature&typename=vestap:sgg_layer&outputFormat=application/'
			+ 'json&srsname=EPSG:3857&maxFeatures=1&outputFormat=application/json&cql_filter=sigungu_cd=' + sigungu; //URL
		
		xhr.open('GET', url );
		xhr.setRequestHeader('Content-Type','application/json; charset=UTF-8');
		xhr.onreadystatechange = function () {
			
			if (this.readyState == 4) {
				
				if(this.status == 200) {
					
					var item = JSON.parse(this.responseText);
					var features = new ol.format.GeoJSON().readFeatures(item);

					map.getView().fit(features[0].getGeometry());
					
				}
			}
		};
		
		xhr.send('');

	}else {
		if(type =="two"){
			moveSido("two");
		}else {
			moveSido("one");
		}
	}
}

document.addEventListener("DOMContentLoaded", function(){
  // Handler when the DOM is fully loaded
	
	// Custom Button - 지도저장
	app.ExportMapCanvas = function(opt_options) {
		var options = opt_options || {};
		var button = document.createElement('button');
		
		button.innerHTML = '';
		button.className = 'fa fa-download';
		button.title = '지도저장';
		
		var this_ = this;
		var exportMapCanvas = function() {
			var depth = $("#depth").val();
			var objs = depth.split("-");
			
			if(objs[1] == "comparison"){
				if (this == $("#comp_base_map > div > div > div > .fa-download")[0]){
					comp_base_map.once('postcompose', function(event) {
						var canvas = event.context.canvas;
						if (navigator.msSaveBlob) {
							navigator.msSaveBlob(canvas.msToBlob(), 'map.png');
						} else {
							canvas.toBlob(function(blob) {
								saveAs(blob, 'map.png');
							});
						}
					});
			        
					comp_base_map.renderSync();
				}else if(this == $("#comp_comp_map > div > div > div > .fa-download")[0]){
					comp_comp_map.once('postcompose', function(event) {
						var canvas = event.context.canvas;
						if (navigator.msSaveBlob) {
							navigator.msSaveBlob(canvas.msToBlob(), 'map.png');
						} else {
							canvas.toBlob(function(blob) {
								saveAs(blob, 'map.png');
							});
						}
					});
			        
					comp_comp_map.renderSync();
				}
				
			}else{
				switch(objs[0]){
				case "admin" : map = whole_esti_map;
				break;
				case "climate" : 
					switch(objs[1]){
						case "estimation": map = esti_map;
						break;
						case "exposure" : map = expo_map;
						break;
					}
				break;
				case "custom" : map = custom_esti_map;
				break;
				}
				
				map.once('postcompose', function(event) {
					var canvas = event.context.canvas;
					if (navigator.msSaveBlob) {
						navigator.msSaveBlob(canvas.msToBlob(), 'map.png');
					} else {
						canvas.toBlob(function(blob) {
							saveAs(blob, 'map.png');
						});
					}
				});
		        
				map.renderSync();
			}
		};
		
		button.addEventListener('click', exportMapCanvas, false);
        button.addEventListener('touchstart', exportMapCanvas, false);
				
        var element = document.createElement('div');
        element.className = 'ol-rotate ol-unselectable ol-control';
        element.style.right = "4.8em";
        element.appendChild(button);

        ol.control.Control.call(this, {
        	element: element,
        	target: options.target
        });
		
	};
	
	// Custom Button - 범례
	app.ViewLegend = function(opt_options) {
		var options = opt_options || {};
		var button = document.createElement('button');
		button.innerHTML = '';
		button.className = 'fas fa-list';
		button.title = '범례 보기';
		
		var this_ = this;
		var viewLegend = function() {
			if($('#map-legend').css("display") != 'none') {
				$('#map-legend').hide();
			} else {
				checkCustom();
				$('#map-legend').show();
			}
		};
		
		button.addEventListener('click', viewLegend, false);
        button.addEventListener('touchstart', viewLegend, false);
		
        var element = document.createElement('div');
        element.className = 'ol-rotate ol-unselectable ol-control';
        element.style.right = "2.6em";
        element.appendChild(button);

        ol.control.Control.call(this, {
        	element: element,
        	target: options.target
        });
        
	};
	
	//custom button - 색상 선택
	app.SelectColor = function(opt_options) {
		var options = opt_options || {};
		var button = document.createElement('button');
		button.innerHTML = '';
		button.className = 'fas fa-paint-brush';
		button.title = '색상 선택';
		
		var this_ = this;
		var selectColor = function() {
			if($('#map-color').css("display") != 'none') {
				$('#map-color').hide();
			} else {
				checkCustom();
				$('#map-color').show();
			}
		};
		
		button.addEventListener('click', selectColor, false);
        button.addEventListener('touchstart', selectColor, false);
		
        var element = document.createElement('div');
        element.className = 'ol-rotate ol-unselectable ol-control';
        element.appendChild(button);

        ol.control.Control.call(this, {
        	element: element,
        	target: options.target
        });
        
	};
	
	ol.inherits(app.ExportMapCanvas, ol.control.Control);
	ol.inherits(app.ViewLegend, ol.control.Control);
	ol.inherits(app.SelectColor, ol.control.Control);
	
	
	var korea = ol.proj.fromLonLat([128, 36]);
	
	var layer_style = new ol.style.Style({
		fill: new ol.style.Fill({
			color: 'rgba(255, 255, 255, 0)'
        }),
        stroke: new ol.style.Stroke({
          color: '#000000',
          width: 1
        }),
        text: new ol.style.Text({
        	fill : new ol.style.Fill({
        		color : '#FFF'
        	}),
        	stroke : new ol.style.Stroke({
        		color : '#000',
        		width : 5
        	})
        })
	});
	
	shadow_style = new ol.style.Style({
		fill: new ol.style.Fill({
			color: 'rgba(0, 0, 0, 0.4)'
		})
	});
	
	var vworld_base = new ol.layer.Tile({
        title : '브이월드(일반)',
        layerId : 'vworld-base',
        visible : true,
        type : 'base',
        source : new ol.source.XYZ({
            url : 'http://xdworld.vworld.kr:8080/2d/Base/201802/{z}/{x}/{y}.png',
        	crossOrigin : 'anonymous'
        })
    });
	
	var vworld_satellite = new ol.layer.Tile({
        title : '브이월드(위성)',
        layerId : 'vworld-sat',
        visible : true,
        type : 'bases',
        source : new ol.source.XYZ({
            url : 'http://xdworld.vworld.kr:8080/2d/Satellite/201710/{z}/{x}/{y}.jpeg',
        	crossOrigin : 'anonymous'
        })
    });

    var vworld_hybrid = new ol.layer.Tile({
        title : '브이월드(하이브리드)',
        visible : true,
        layerId : 'vworld-hybrid',
        opacity : 0.6,
        type : 'bases',
        source : new ol.source.XYZ({
            url : 'http://xdworld.vworld.kr:8080/2d/Hybrid/201802/{z}/{x}/{y}.png',
        	crossOrigin : 'anonymous'
        })
    });

    var vworld_grey = new ol.layer.Tile({
    	title : '브이월드(회색)',
    	visible : true, 
    	layerId : 'vworld-grey',
    	source : new ol.source.XYZ({
    		url : 'http://xdworld.vworld.kr:8080/2d/gray/service/{z}/{x}/{y}.png',
    		crossOrigin : 'anonymous'
    	})
    });
    
    var vworld_satbrid = new ol.layer.Group({
    	title : '브이월드(위성)',
    	visible : true,
    	layers: [vworld_satellite, vworld_hybrid],
    	type: 'base',
    	layerId : 'vworld-satbrid'
    });
    
    sd_layer = new ol.layer.Image({
    	source : new ol.source.ImageWMS({
    		url : serverIp+'vestap/wms',
    		params : {
    			layers : 'vestap:sd_layer'
    		},
    		crossOrigin: "Anonymous",
    	}),
    	id : 'sd',
		minResolution : 153,
		visible : true,
        zIndex : 30/*,
        style :  function(feature) {
        	layer_style.getText().setText(feature.get('sd_nm'));
            return layer_style;
        }*/
    });
    
    sd_layer.getSource().on('imageloadend', function(){
    	$(".display").css("display","none");
    });
    sgg_layer = new ol.layer.Vector({
		source : new ol.source.Vector({
			format : new ol.format.GeoJSON(),
			url: function(extent) {
				var strUrl = 'selectWfsLayer.do?service=WFS&version=1.1.0&request=GetFeature&typename=vestap:sgg_layer&outputFormat=application/json&'+
				'srsname=EPSG:3857&bbox='+extent.join(',')+',EPSG:3857';
				/*var strUrl = 'selectWfsLayer.do?service=WFS&version=1.1.0&request=GetFeature&typename=vestap:sgg_layer_2019&outputFormat=application/json&'+
				'srsname=EPSG:3857&bbox='+extent.join(',')+',EPSG:3857';*/
				
				return strUrl;
			},
			strategy : ol.loadingstrategy.bbox
		}),
		renderMode : 'image',
		id : 'sgg',
		maxResolution : 153,
		minResolution : 20,
		visible : true,
        zIndex : 30,
        style: function(feature) {
        	layer_style.getText().setText(feature.get('sigungu_nm'));
            return layer_style;
        }
	});
    
    emd_layer = new ol.layer.Vector({
    	source : new ol.source.Vector({
    		format : new ol.format.GeoJSON(),
    		url : function (extent){
    			var strUrl = 'selectWfsLayer.do?service=WFS&version=1.1.0&request=GetFeature&typename=vestap:emd_layer&outputFormat=application/json&'+
				'srsname=EPSG:3857&bbox='+extent.join(',')+',EPSG:3857'; 
    			/*var strUrl = 'selectWfsLayer.do?service=WFS&version=1.1.0&request=GetFeature&typename=vestap:emd_layer_2019&outputFormat=application/json&'+
				'srsname=EPSG:3857&bbox='+extent.join(',')+',EPSG:3857'; */
    			
    			return strUrl;
    		},
    		strategy : ol.loadingstrategy.bbox
    	}),
    	renderMode : 'image',
		id : 'emd',
		maxResolution : 20,
		visible : true,
        zIndex : 30,
        style: function(feature) {
        	layer_style.getText().setText(feature.get('hjd_nm'));
        	//layer_style.getText().setText(feature.get('adm_dr_nm'));
            return layer_style;
        }
    });
    
    var scen_layer = new ol.layer.Group({
		name : 'scen',
		visible : true,
		layers : []
	});
    
    shadow_layer_sd = new ol.layer.Image({
    	source : new ol.source.ImageWMS({
    		url : serverIp+'vestap/wms',
    		params : {
    			layers : 'vestap:shadow_layer_sd'
    		},
    		crossOrigin: "Anonymous",
    	}),
    	id : 'shadow_sd',
		visible : true,
        style: new ol.style.Style({
          fill: new ol.style.Fill({
            color: '#ffffff',
            opacity : '0.7'
          })
        })
    });
    shadow_layer_sd.setOpacity(0.7);
    
    shadow_layer_sgg = new ol.layer.Image({
    	source : new ol.source.ImageWMS({
    		url : serverIp+'vestap/wms',
    		params : {
    			layers : 'vestap:shadow_layer_sgg'
    		},
    		crossOrigin: "Anonymous",
    	}),
    	id : 'shadow_sgg',
		visible : true,
        style: new ol.style.Style({
          fill: new ol.style.Fill({
            color: '#ffffff',
            opacity : '0.7'
          })
        })
    });
    shadow_layer_sgg.setOpacity(0.7);
    
    //vworld_base
	esti_map = new ol.Map({
		 view:new ol.View({
			 zoom:7,
			 center:korea,
			 minZoom : 6,
			 maxZoom : 17
		 }),
		 target:'esti_map',
		 layers: [vworld_grey, sd_layer, sgg_layer, emd_layer],
		 controls : ol.control.defaults({
			 attributionOptions : {
				 collapsible : false
			 }
		 }).extend([
			 new app.ExportMapCanvas(), new app.ViewLegend(), new app.SelectColor()
		 ]),
		 style: layer_style
	});

	/*********************************************************************
     *	기후노출 세부정보
     *********************************************************************/
	expo_map = new ol.Map({
		 view:new ol.View({
			 zoom:7,
			 center:korea,
			 minZoom : 6,
			 maxZoom : 17
		 }),
		 target:'expo_map',
		 layers: [vworld_grey, sd_layer, sgg_layer, scen_layer],
		 controls : ol.control.defaults({
			 attributionOptions : {
				 collapsible : false
			 }
		 }).extend([
			 new app.ExportMapCanvas(), new app.ViewLegend()
		 ]),
		 style: layer_style
	});
		
	/*********************************************************************
     *	지역간 비교분석
     *********************************************************************/
	comp_base_map = new ol.Map({
		 view:new ol.View({
			 zoom:7,
			 center:korea,
			 minZoom : 6,
			 maxZoom : 17
		 }),
		 target:'comp_base_map',
		 layers: [vworld_grey, sd_layer, sgg_layer, emd_layer],
		 controls : ol.control.defaults({
			 attributionOptions : {
				 collapsible : false
			 }
		 }).extend([
			 new app.ExportMapCanvas(), new app.ViewLegend()
		 ]),
		 style: layer_style
	});
	
	comp_comp_map = new ol.Map({
		 view:new ol.View({
			 zoom:7,
			 center:korea,
			 minZoom : 6,
			 maxZoom : 17
		 }),
		 target:'comp_comp_map',
		 layers: [vworld_grey, sd_layer, sgg_layer, emd_layer],
		 controls : ol.control.defaults({
			 attributionOptions : {
				 collapsible : false
			 }
		 }).extend([
			 new app.ExportMapCanvas(), new app.ViewLegend()
		 ]),
		 style: layer_style
	});
			
	/*********************************************************************
     *	사용자정의 취약성 - 자체취약성평가
     *********************************************************************/
	custom_esti_map = new ol.Map({
		 view:new ol.View({
			 zoom:7,
			 center:korea,
			 minZoom : 6,
			 maxZoom : 17
		 }),
		 target:'custom_esti_map',
		 layers: [vworld_grey, sd_layer, sgg_layer, emd_layer],
		 controls : ol.control.defaults({
			 attributionOptions : {
				 collapsible : false
			 }
		 }).extend([
			 new app.ExportMapCanvas(), new app.ViewLegend()
		 ]),
		 style: layer_style
	});
	/*********************************************************************
     *	전국단위 - 취약성평가
     *********************************************************************/
	whole_esti_map = new ol.Map({
		 view:new ol.View({
			 zoom:7,
			 center:korea,
			 minZoom : 6,
			 maxZoom : 17
		 }),
		 target:'whole_esti_map',
		 layers: [vworld_grey, sd_layer, sgg_layer, emd_layer],
		 controls : ol.control.defaults({
			 attributionOptions : {
				 collapsible : false
			 }
		 }).extend([
			 new app.ExportMapCanvas(), new app.ViewLegend(), new app.SelectColor()
		 ]),
		 style: layer_style
	});
	
	/*********************************************************************
     *	시스템정의 - 취약성평가
     *********************************************************************/
	system_esti_map = new ol.Map({
		 view:new ol.View({
			 zoom:7,
			 center:korea,
			 minZoom : 6,
			 maxZoom : 17
		 }),
		 target:'system_esti_map',
		 layers: [vworld_grey, sd_layer, sgg_layer, emd_layer],
		 controls : ol.control.defaults({
			 attributionOptions : {
				 collapsible : false
			 }
		 }).extend([
			 new app.ExportMapCanvas(), new app.ViewLegend(), new app.SelectColor()
		 ]),
		 style: layer_style
	});
	
	var drawSourceVector = new ol.source.Vector({
		wrapX : false
	});
	
	interactionDraw = new ol.interaction.Draw({
		source: drawSourceVector,
		type: 'Point'
	});
	
	expo_map.addInteraction(interactionDraw);
	
	interactionDraw.on('drawend',function(e){
		var coord = e.feature.getGeometry();
		var geom = e.feature.getGeometry().getCoordinates();
		var x = geom[0];
		var y = geom[1];
		
		fn_showChart();
		
		getValue(x,y,scenario);
		getAddress(geom);
		
		
		var iconFeature = new ol.Feature({
			geometry: coord,
			name: 'My Point',
			population: 4000,
			rainfall: 500
		});
		var iconStyle = new ol.style.Style({
			
			image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
				anchor: [0.5, 50],
				anchorXUnits: 'fraction',
				anchorYUnits: 'pixels',
				src: '/resources/img/location-pointer.png',
			}))
		});
		
		iconFeature.setStyle(iconStyle);

		var vectorSource = new ol.source.Vector({
			features: [iconFeature]
		});
		var vectorLayer = new ol.layer.Vector({
			source: vectorSource,
			name : "Popup",
			id : "MyHome",
			zIndex:25
		});
		
		if(expo_map.getLayers().a[4]){
			expo_map.removeLayer(expo_map.getLayers().a[4]);
		}
		
		expo_map.addLayer(vectorLayer);
	});
	
	for(var i=1;i<=10;i++){
		eval("fill_"+i+" = new ol.style.Fill();");
	}
	
	/*for(var i=1;i<=step;i++){
		eval("source_"+i+" = new ol.source.Vector({features:[]});");
		eval("level_"+i+" = new ol.layer.Vector({ source: source_"+i+", style: new ol.style.Style({ stroke: new ol.style.Stroke({ color: '#f00', width: 4 }), fill: new ol.style.Fill({ color: color["+(i-1)+"], opacity : '0.4' }) }) });");
		eval("level_"+i+".setOpacity(0.7);");
	}*/
	
	//$(".display").css("display","block");
});
/*********************************************************************
 *	시나리오 모델 연도 변경시 래스터 변경전 레이어제거 (setVisible(false))
 *********************************************************************/
function removeLayer(){
	map = expo_map; 
	map.getLayers();
		
	for(var i = 3; i<map.getLayers().a.length; i++){
		map.getLayers().a[i].setVisible(false);
	}
}
/*********************************************************************
 *	시나리오 모델 연도 변경시 래스터 변경 (setVisible(true))
 *********************************************************************/
function changeLayer(data){
	var depth = $("#depth").val();
	var objs = depth.split("-");
	var url;
	if(objs[1] =='exposure'){
		map = expo_map;
		scenario = data;
		if(map.getLayers().a[3]){
			map.getLayers().a[3].setVisible(false);
		}
		
		/*********************************************************************
		 * SECTION
		 * 	RC001 : 과거관측자료
		 * 	RC002 : RCP4.5
		 * 	RC003 : RCP8.5
		 * 
		 * MODEL
		 *	CM001 : MME5
		 *	CM002 : GRIM
		 *	CM003 : HadGEM
		 *	CM004 : RegCM
		 *	CM005 : SNUMM5
		 *	CM006 : WRF
		 *
		 *YEAR
		 *	YC001 : 01~10
		 *	YC002 : 11~20
		 *	YC003 : 21~30
		 *	YC004 : 31~40
		 *	YC005 : 41~50
		 *	YC006 : 51~60
		 *	YC007 : 61~70
		 *	YC008 : 71~80
		 *	YC009 : 81~90
		 *	YC010 : 91~100
		 *
		 *	YC301 : 21~50
		 *	YC302 : 31~60
		 *	YC303 : 41~70
		 *	YC304 : 51~80
		 *	YC305 : 61~90
		 *	YC306 : 71~100
		 *********************************************************************/
		var section = $("#scenSectionList option:selected").val();
		var model = $("#scenModelList option:selected").val();
		var year = $("#scenYearList option:selected").val();
		var RC002 = "R45";
		var RC003 = "R85";
		var YC003 = "21-30";
		var YC004 = "31-40";
		var YC005 = "41-50";
		var YC006 = "51-60";
		var YC007 = "61-70";
		var YC008 = "71-80";
		var YC009 = "81-90";
		var YC010 = "91-100";
		var YC301 = "21-50";
		var YC302 = "31-60";
		var YC303 = "41-70";
		var YC304 = "51-80";
		var YC305 = "61-90";
		var YC306 = "71-100";
		//fn_xlabel(year);
		
		switch(section){
			case "RC002" : section = "R45";
			break;
			case "RC003" : section = "R85";
			break;
			default : section = "none";
			break;
		}
		switch(year){
			case "YC003" : year = YC003;
			break;
			case "YC004" : year = YC004;
			break;
			case "YC005" : year = YC005;
			break;
			case "YC006" : year = YC006;
			break;
			case "YC007" : year = YC007;
			break;
			case "YC008" : year = YC008;
			break;
			case "YC009" : year = YC009;
			break;
			case "YC010" : year = YC010;
			break;
			case "YC301" : year = YC301;
			break;
			case "YC302" : year = YC302;
			break;
			case "YC303" : year = YC303;
			break;
			case "YC304" : year = YC304;
			break;
			case "YC305" : year = YC305;
			break;
			case "YC306" : year = YC306;
			break;
			default : year = "none";
			break;
		}
		
		//RCP45 를 보여줄지 RCP85 보여줄지 ?! 
		
		var layerName = 'vestap:'+data+'_'+section+'_'+year;
		
		if(section == "none" || year == "none"){
			swal({
				title:'선택 오류'
				,text:'해당 지도는 추가 준비중입니다.'
				,type:'error'
			});
			return false;
		}else {
			thematicLayers = geoserver_tile_layer(data+'_'+section+'_'+year, layerName, true, 0.8);
			map.getLayers().setAt("3", thematicLayers);
			
			//RASTER 스타일불러오기위해 'vestap:' 자르기
			layerName = layerName.replace('vestap:','');
			layerName = layerName.substring(0,layerName.indexOf('_',0));
			url = serverIp+'styles/' + layerName + '.sld';
			
			getRasterStyle(url);
		}
	}
}

/*********************************************************************
 *	시나리오 모델 연도 변경시 연도별 30년치 Value 구한다.
 *********************************************************************/
function getValue(x,y,data){
	
	var year = $("#scenYearList option:selected").val();
	var YC003 = "21-30";
	var YC004 = "31-40";
	var YC005 = "41-50";
	var YC006 = "51-60";
	var YC007 = "61-70";
	var YC008 = "71-80";
	var YC009 = "81-90";
	var YC010 = "91-100";
	var YC301 = "21-50";
	var YC302 = "31-60";
	var YC303 = "41-70";
	var YC304 = "51-80";
	var YC305 = "61-90";
	var YC306 = "71-100";
	//fn_xlabel(year);
	/*switch(year){
	case "YC003" : year = YC003;
	break;
	case "YC004" : year = YC004;
	break;
	case "YC005" : year = YC005;
	break;
	case "YC006" : year = YC006;
	break;
	case "YC007" : year = YC007;
	break;
	case "YC008" : year = YC008;
	break;
	case "YC009" : year = YC009;
	break;
	case "YC010" : year = YC010;
	break;
	case "YC301" : year = YC301;
	break;
	case "YC302" : year = YC302;
	break;
	case "YC303" : year = YC303;
	break;
	case "YC304" : year = YC304;
	break;
	case "YC305" : year = YC305;
	break;
	case "YC306" : year = YC306;
	break;
	default : year = "none";
	break;
}*/
	fn_ensemble();
	if(data!=undefined){
		var scen = data.toLowerCase();
				
		$.ajax({
			url : '/member/base/climate/exposure/selectAnalysis.do',
			method : 'get',
			data : {
				x : x,
				y : y,
				scen : scen
			},
			dataType : 'json',
			success : function(item){
				var val = new Array();
								
				var name;
				
				if(year.substring(2) > 200 ){
					for(var i in item.list){
						name = item.list[i].year;
						
						if(name.indexOf("21-50") == 0 ){
							val.push(item.list[i].val);
						}else if(name.indexOf("31-60") == 0){
							val.push(item.list[i].val); 
						}else if(name.indexOf("41-70") == 0){
							val.push(item.list[i].val);
						}else if(name.indexOf("51-80") == 0){
							val.push(item.list[i].val);
						}else if(name.indexOf("61-90") == 0){
							val.push(item.list[i].val);
						}else if(name.indexOf("71-100") == 0){
							val.push(item.list[i].val);
						}
					}
					fn_lineRedraw_new(val);
				}else{
					for(var i in item.list){
						name = item.list[i].year;
						
						if(name.indexOf("21-30") == 0){
							val.push(item.list[i].val);
						}else if(name.indexOf("31-40") == 0){
							val.push(item.list[i].val);
						}else if(name.indexOf("41-50") == 0){
							val.push(item.list[i].val);
						}else if(name.indexOf("51-60") == 0){
							val.push(item.list[i].val);
						}else if(name.indexOf("61-70") == 0){
							val.push(item.list[i].val);
						}else if(name.indexOf("71-80") == 0){
							val.push(item.list[i].val);
						}else if(name.indexOf("81-90") == 0){
							val.push(item.list[i].val);
						}else if(name.indexOf("91-100") == 0){
							val.push(item.list[i].val);
						}
					}
					fn_lineRedraw(val);
				}
				
			},
			error : function(request,status,error) {
				console.log("[ajax error] "+request.status);
			}
		});
		
	}else{
		swal({
			title:'선택 오류'
			,text:'기후노출 부문지표를 선택해주세요.'
			,type:'error'
		});
		return false;
	}
}

/*********************************************************************
 *	페이지 본문 변경시 지도사이즈 변경
 *********************************************************************/
function updateMap(){
	var depth = $("#depth").val();
	var objs = depth.split("-");
	if(objs[0] == "admin"){
		
		if(objs[1] =="climate"){
			map = whole_esti_map;
		}else if(objs[1] =="system"){
			map = system_esti_map;
		}else {
			map = null;
		}
		
	}else if(objs[0] == "climate"){
		
		if(objs[1] == "estimation"){
			map = esti_map;
			
		}else if(objs[1] == "comparison"){
			map = comp_base_map;
			map.updateSize();
			map = comp_comp_map;
			
		}else if(objs[1] == "exposure"){
			map = expo_map;
			
		}else{
			map = null;
		}
		
	}else if(objs[0] == "custom"){
		if(objs[1] == "estimation"){
			map = custom_esti_map;
			
		}else{
			map = null;
		}
	}else{
		map = null;
	}
	if(map){
		map.updateSize();
	}
}

/*********************************************************************
 *	지표레이어 불러오는 함수!
 *********************************************************************/
function geoserver_tile_layer(name, layer, visible, opacity){
	return new ol.layer.Image({
    	source : new ol.source.ImageWMS({
    		url : serverIp+'vestap/wms',
    		params : {
    			layers : layer
    		},
    		crossOrigin: "Anonymous",
    	}),
    	zIndex : 20,
    	visible : true
    });
}

/*********************************************************************
 *	기후노출 세부정보 - 지도 클릭 시 좌표 구하기
 *********************************************************************/
function getCoord(){
	var x,y;
	expo_map.on('click',function(e){
		var coord = expo_map.getCoordinateFromPixel(e.pixel);
		
		x = Math.floor(coord[0]);
		y = Math.floor(coord[1]);
		
		
	});
	return x,y;
}

/*********************************************************************
 *	기후노출 세부정보 - 지도 클릭 시 좌표로 주소 구하기
 *********************************************************************/
function getAddress(coord){
	var VAPI = "B447E0A4-F930-3FE7-8361-563505B31150";
	//운영 var VAPI = "024B42C6-44FA-3EF7-90D7-073F4EF022C9";
	
	$.ajax({
		url : '/member/base/climate/exposure/selectAddressInfo.do',
		method : 'get',
		data : {
			key : VAPI,
			service : 'address',
			crs : 'EPSG:3857',
			point : coord[0]+','+coord[1],
			type : 'PARCEL',
			request : 'GetAddress'
		},
		dataType : 'json',
		success : function(item) {
			
			if(item.response.status=="NOT_FOUND"){
				swal({
					title:'선택 오류'
					,text:'지점선택을 다시 해주십시오.'
					,type:'error'
				});
				
				return false;
				
			}else{
				
				var result = item.response.result[0];
				$("._address").text(' :  '+result.text);
			}
		},
		error : function () {
			console.log("error");
		}
	});
}

/*********************************************************************
 *	기후노출 세부정보 - 래스터 스타일 불러와 범례색상지정 
 *********************************************************************/
function getRasterStyle(url){
	$.get(url, function(xml){
		var _html = '';
		
		$(xml).find('ColorMapEntry[opacity!="0.0"]').each(function(idx,element){

			_html += "<li><span style='background: {0};'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>~{1}</li>".replace('{0}', $(element).attr('color')).replace('{1}',$(element).attr('quantity'));
		});
		
		$(".ol-expo .legend-labels").html(_html);
		//$(".ol-expo > #map-legend").show();
	});
}

/*********************************************************************
 *	취약성평가 - 벡터레이어 스타일 범례색상지정 
 *********************************************************************/
function getVectorStyle(legend, color, map, existMinus,colorType){
	window.__style_state = {
		legend:legend,
		color:color,
		map:map,
		existMinus:existMinus,
	}
	var _html = '';
	for(var i in legend){
		if(existMinus === true && +i === 0){
			_html += "<li><span style='background: {0};'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>0미만</li>".replace('{0}', color[i]).replace('{1}',legend[i]);
		}else if(existMinus === true && +i === legend.length - 1){
			_html += "<li><span style='background: {0};'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>1이상</li>".replace('{0}', color[legend.length - 1]).replace('{1}',legend[legend.length - 1]);
		}else{
			_html += "<li><span style='background: {0};'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>~{1}</li>".replace('{0}', color[i]).replace('{1}',legend[i]);
		}
	}
	var allColor = new Array();
	var step = _step;
	
	for(var i=0;i<7;i++){
		allColor.push(new Array());
		allColor[i] = colorlamp(i+1,existMinus);
	}
	var __html = '';
	for(var j=0;j<7;j++){
		__html += "<li class='liColor'><input type='radio' name='colorChange' data='"+(j+1)+"' "+(j+1 == colorType ? "checked='checked'" : '')+" onclick='changeColor("+(j+1)+")' />"
		__html += "<span class='color-item' style='background-image: linear-gradient(to right,{0},{1},{2});'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></li>".replace('{0}', allColor[j][0]).replace('{1}', allColor[j][parseInt(step/2)]).replace('{2}', allColor[j][step-1]);
	}
	switch(map){
		case "esti_map" : $(".ol-esti-map .legend-labels").html(_html);
			$(".ol-colorlamp .color-labels").html(__html);
			break;
		case "custom_esti_map" :  $(".ol-custom-esti-map .legend-labels").html(_html);
			break;
		case "comp_base_map": $(".ol-comp-base .legend-labels").html(_html);
			break;
		case "comp_comp_map": $(".ol-comp-comp .legend-labels").html(_html);
			break;
		case "whole_esti_map": $(".ol-whole-esti-map .legend-labels").html(_html);
			$(".ol-colorlamp .color-labels").html(__html);
			break;
		case "system_esti_map": $(".ol-system-esti-map .legend-lables").html(_html);
			$(".ol-colorlamp .color-labels").html(__html);
			break;
	}
}
function changeColor(colorType){
	setColorType(colorType);
	var state = window.__style_state
	var legend = state.legend
	var color = colorlamp(getColorType(),state.existMinus)
	/* 벡터 색상 변경 부분 */
	for(var i=1;i<=legend.length;i++){
		eval("fill_"+i+".setColor(color["+(i-1)+"]);");
		eval("style_"+i+".setFill(fill_"+i+");");
		eval("level_"+i+".setStyle(style_"+i+");");
	}
	getVectorStyle(state.legend,color,state.map,state.existMinus,colorType)
}


/*********************************************************************
 *	취약성평가 - 벡터 getter/setter
 *********************************************************************/
function setVector(cd,val){
	_vector.cd = cd;
	_vector.val = val;
}
function getVector(){
	return _vector;
}

/*********************************************************************
 *	취약성평가 - 벡터레이어 변경
 *********************************************************************/
function addVector(cd, val, onlyCoastNear){
	///
	var existMinus = false
	if($('input[name="legendChange"]:checked').val() == 'fixed'){
		existMinus = true
	}
	///
	setVector(cd,val);
	var cd = cd;
	/* 가변 벡터 범위 */
	var variableLegends = variableLegend(val, 10);
	
	/* 범례(가변, 고정) 체크 후 List 값 */
	var legend = legendChkOption(val, 10, existMinus);
	
	var mapArray = ["esti_map","comp_base_map","comp_comp_map","expo_map","whole_esti_map","custom_esti_map","system_esti_map"];
	for(var i in mapArray){
		var map_id=$("#"+mapArray[i]).attr('id');
		if(map_id!=null){
			map = map_id;
		}
	}
	var step = _step;
	if(existMinus === true){
		step = step + 1
	}
	color=colorlamp(getColorType(),existMinus);

	/* 10레벨의 레이어 생성 */
	createLayer(existMinus);
	
	/* 전국단위 취약성평가 -> 시/도 기준  */
	if(cd[0].length < 3){
		shadow_source = new ol.source.Vector();
		shadow_layer = new ol.layer.Vector({
			id:"shadow",
	        source: shadow_source,
	        style: new ol.style.Style({
	        })
	      });
		shadow_layer.setOpacity(0.7);
		
		for(var i in cd){
			var sido = cd[i];
			var xhr = new XMLHttpRequest();
			var url = 'selectWfsLayer.do?service=WFS&version=1.1.0&request=GetFeature&typename=vestap:sd_layer&outputFormat=application/'
				+ 'json&srsname=EPSG:3857&maxFeatures=1&outputFormat=application/json&cql_filter=sd_cd=' + sido; //URL
			
			xhr.open('GET', url );
			xhr.setRequestHeader('Content-Type','application/json; charset=UTF-8');
			xhr.onreadystatechange = function () {
				
				if (this.readyState == 4) {
					
					if(this.status == 200) {
						
						var item = JSON.parse(this.responseText);
						var features = new ol.format.GeoJSON().readFeatures(item);
						for(var i in cd){
							if(cd[i]== features[0].getProperties().sd_cd){
								
								for(var j=1;j<=step;j++){
									var k=j-1;
									
									if(j==1){
										eval("if(val[i] <= legend["+k+"]) {source_"+j+".addFeature(features[0]);}");
									}else if(j==step){
										eval("if(val[i] > legend["+(k-1)+"]){source_"+j+".addFeature(features[0]);}");
									}else{
										eval("if(val[i] > legend["+(k-1)+"] && val[i] <= legend["+k+"]) {source_"+j+".addFeature(features[0]);}");
									}
								}
							}
						}
					}
				}
			};
			xhr.send('');
		}
		
	}else if( cd[0].length > 3 && cd[0].length < 6){
		initShadow(cd, 'SGG');
		initLayer(cd, val, legend, 'SGG',onlyCoastNear);
		
	}else if(cd[0].length > 6){
		
		initShadow(cd, 'EMD');
		initLayer(cd, val, legend, 'EMD',onlyCoastNear);
	}
	var levels = new Array();
	levels.push(shadow_layer);
	
	for(var i=1;i<=step;i++){
		eval("levels.push(level_"+i+");");
	}
	
	
	
	var layerGroups = new ol.layer.Group({
		name : 'estimation',
		visible : true,
		zIndex:31,
		layers : levels
	});
	switch(map){
	case "esti_map" : if(esti_map.getLayers().a[vestap_layer]){
		esti_map.removeLayer(esti_map.getLayers().a[vestap_layer]);
	}
	esti_map.addLayer(layerGroups);
	
	break;
	case "custom_esti_map" : if(custom_esti_map.getLayers().a[vestap_layer]){
		custom_esti_map.removeLayer(custom_esti_map.getLayers().a[vestap_layer]);
		}
	custom_esti_map.addLayer(layerGroups);
	break;  
	case "whole_esti_map": if(whole_esti_map.getLayers().a[vestap_layer]){
		whole_esti_map.removeLayer(whole_esti_map.getLayers().a[vestap_layer]);
	}
	whole_esti_map.addLayer(layerGroups);
	break;
	case "system_esti_map" : if(system_esti_map.getLayers().a[vestap_layer]){
		system_esti_map.removeLayer(system_esti_map.getLayers().a[vestap_layer]);
	}
	system_esti_map.addLayer(layerGroups);
	break;
	}
	getVectorStyle(legend, color, map, existMinus);
	
}

/*********************************************************************
 *	지역간 비교분석 - 벡터레이어 변경
 *********************************************************************/
function addVectorComparison(base, comp, min, max){
	
	/*가변 백터 범위 */
	/*
	var role = Number ((max - min) / 5);
	var role_1 = parseFloat(parseFloat(min) + parseFloat(role)).toFixed(2);
	var role_2 = parseFloat(parseFloat(role_1) + parseFloat(role)).toFixed(2);
	var role_3 = parseFloat(parseFloat(role_2) + parseFloat(role)).toFixed(2);
	var role_4 = parseFloat(parseFloat(role_3) + parseFloat(role)).toFixed(2);
	var role_5 = parseFloat(parseFloat(role_4) + parseFloat(role)).toFixed(2);
	*/
	
	/* 고정 벡터 범위 */
	var role = 1/5;
	var role_1 = parseFloat(role).toFixed(2);
	var role_2 = parseFloat(parseFloat(role_1) + parseFloat(role)).toFixed(2);
	var role_3 = parseFloat(parseFloat(role_2) + parseFloat(role)).toFixed(2);
	var role_4 = parseFloat(parseFloat(role_3) + parseFloat(role)).toFixed(2);
	var role_5 = parseFloat(parseFloat(role_4) + parseFloat(role)).toFixed(2);
	
	var legend = new Array();
	var color = new Array();
	var list = new Array();
	list.push(base,comp);
	
	legend.push(role_1);
	legend.push(role_2);
	legend.push(role_3);
	legend.push(role_4);
	legend.push(role_5);
	
	color.push('#fef0d9');
	color.push('#fdcc8a');
	color.push('#fc8d59');
	color.push('#e34a33');
	color.push('#b30000');
	
	getVectorStyle(legend, color, "comp_base_map");
	base_source = new ol.source.Vector({
		features:[]
	});
	comp_source = new ol.source.Vector({
		features:[]
	});
	
	var base_fill = new ol.style.Fill({
		color : '#fef0d9'
	});
	var comp_fill = new ol.style.Fill({
		color : '#fef0d9'
	});
	
	var base_style = new ol.style.Style ({
		stroke: new ol.style.Stroke({
            color: '#f00',
            width: 4
          }),
          fill: new ol.style.Fill({
            color: '#fef0d9',
          })
	});
	var comp_style = new ol.style.Style ({
		stroke: new ol.style.Stroke({
            color: '#f00',
            width: 4
          }),
          fill: new ol.style.Fill({
            color: '#fef0d9',
          })
	});
	base_layer = new ol.layer.Vector({
        source: base_source,
        style: new ol.style.Style({
          stroke: new ol.style.Stroke({
            color: '#f00',
            width: 4
          }),
          fill: new ol.style.Fill({
            color: '#fef0d9',
          })
        })
      });
	
	comp_layer = new ol.layer.Vector({
        source: comp_source,
        style: new ol.style.Style({
          stroke: new ol.style.Stroke({
            color: '#f00',
            width: 4  
          }),
          fill: new ol.style.Fill({
        	color: '#fdcc8a',
          })
        })
      });
	
	//시도 (광역)
	if(base[0].length < 3 ){
		var sido = base[0];
		var base_val = base[1];
		var xhr = new XMLHttpRequest();
		var url = 'selectWfsLayer.do?service=WFS&version=1.1.0&request=GetFeature&typename=vestap:sd_layer&outputFormat=application/'
			+ 'json&srsname=EPSG:3857&maxFeatures=1&outputFormat=application/json&cql_filter=sd_cd=' + sido; //URL
		
		xhr.open('GET', url );
		xhr.setRequestHeader('Content-Type','application/json; charset=UTF-8');
		xhr.onreadystatechange = function () {
			
			if (this.readyState == 4) {
				
				if(this.status == 200) {
					
					var item = JSON.parse(this.responseText);
					var features = new ol.format.GeoJSON().readFeatures(item);
					if(base[0] == features[0].getProperties().sd_cd){
						
						if(base_val <= role_1){
							base_fill.setColor('#fef0d9');
							base_source.addFeature(features[0]);
							base_style.setFill(base_fill);
							base_layer.setStyle(base_style);
							base_layer.setOpacity(0.7);
						}else if (base_val > role_1 && base_val <= role_2){
							base_fill.setColor('#fdcc8a');
							base_source.addFeature(features[0]);
							base_style.setFill(base_fill);
							base_layer.setStyle(base_style);
							base_layer.setOpacity(0.7);
						}else if(base_val > role_2 && base_val <= role_3){
							base_fill.setColor('#fc8d59');
							base_source.addFeature(features[0]);
							base_style.setFill(base_fill);
							base_layer.setStyle(base_style);
							base_layer.setOpacity(0.7);
						}else if(base_val > role_3 && base_val <= role_4){
							base_fill.setColor('#e34a33');
							base_source.addFeature(features[0]);
							base_style.setFill(base_fill);
							base_layer.setStyle(base_style);
							base_layer.setOpacity(0.7);
						}else if(base_val > role_4){
							base_fill.setColor('#b30000');
							base_source.addFeature(features[0]);
							base_style.setFill(base_fill);
							base_layer.setStyle(base_style);
							base_layer.setOpacity(0.7);
						}
					}
				}
			}
		};
		xhr.send('');
	}else if(base[0].length > 3){
		var sigungu = base[0];
		var base_val = base[1];
		var xhr = new XMLHttpRequest();
		var url = 'selectWfsLayer.do?service=WFS&version=1.1.0&request=GetFeature&typename=vestap:sgg_layer&outputFormat=application/'
			+ 'json&srsname=EPSG:3857&maxFeatures=1&outputFormat=application/json&cql_filter=sigungu_cd=' + sigungu; //URL
		
		xhr.open('GET', url );
		xhr.setRequestHeader('Content-Type','application/json; charset=UTF-8');
		xhr.onreadystatechange = function () {
			
			if (this.readyState == 4) {
				
				if(this.status == 200) {
					
					var item = JSON.parse(this.responseText);
					var features = new ol.format.GeoJSON().readFeatures(item);
					if(base[0] == features[0].getProperties().sigungu_cd){
						
						if(base_val <= role_1){
							base_fill.setColor('#fef0d9');
							base_source.addFeature(features[0]);
							base_style.setFill(base_fill);
							base_layer.setStyle(base_style);
							base_layer.setOpacity(0.7);
						}else if (base_val > role_1 && base_val <= role_2){
							base_fill.setColor('#fdcc8a');
							base_source.addFeature(features[0]);
							base_style.setFill(base_fill);
							base_layer.setStyle(base_style);
							base_layer.setOpacity(0.7);
						}else if(base_val > role_2 && base_val <= role_3){
							base_fill.setColor('#fc8d59');
							base_source.addFeature(features[0]);
							base_style.setFill(base_fill);
							base_layer.setStyle(base_style);
							base_layer.setOpacity(0.7);
						}else if(base_val > role_3 && base_val <= role_4){
							base_fill.setColor('#e34a33');
							base_source.addFeature(features[0]);
							base_style.setFill(base_fill);
							base_layer.setStyle(base_style);
							base_layer.setOpacity(0.7);
						}else if(base_val > role_4){
							base_fill.setColor('#b30000');
							base_source.addFeature(features[0]);
							base_style.setFill(base_fill);
							base_layer.setStyle(base_style);
							base_layer.setOpacity(0.7);
						}
					}
				}
			}
		};
		xhr.send('');
	}
	
	if(comp[0].length < 3 ){
		var sido = comp[0];
		var comp_val = comp[1];
		var xhr = new XMLHttpRequest();
		var url = 'selectWfsLayer.do?service=WFS&version=1.1.0&request=GetFeature&typename=vestap:sd_layer&outputFormat=application/'
			+ 'json&srsname=EPSG:3857&maxFeatures=1&outputFormat=application/json&cql_filter=sd_cd=' + sido; //URL
		                                                                                       
		xhr.open('GET', url );
		xhr.setRequestHeader('Content-Type','application/json; charset=UTF-8');
		xhr.onreadystatechange = function () {
			
			if (this.readyState == 4) {
				
				if(this.status == 200) {
					
					var item = JSON.parse(this.responseText);
					var features = new ol.format.GeoJSON().readFeatures(item);
					if(comp[0] == features[0].getProperties().sd_cd){
						comp_comp_map.getView().fit(features[0].getGeometry());
						if(comp_val <= role_1){
							comp_fill.setColor('#fef0d9');
							comp_source.addFeature(features[0]);
							comp_style.setFill(comp_fill);
							comp_layer.setStyle(comp_style);
							comp_layer.setOpacity(0.7);
						}else if (comp_val > role_1 && comp_val <= role_2){
							comp_fill.setColor('#fdcc8a');
							comp_source.addFeature(features[0]);
							comp_style.setFill(comp_fill);
							comp_layer.setStyle(comp_style);
							comp_layer.setOpacity(0.7);
						}else if(comp_val > role_2 && comp_val <= role_3){
							comp_fill.setColor('#fc8d59');
							comp_source.addFeature(features[0]);
							comp_style.setFill(comp_fill);
							comp_layer.setStyle(comp_style);
							comp_layer.setOpacity(0.7);
						}else if(comp_val > role_3 && comp_val <= role_4){
							comp_fill.setColor('#e34a33');
							comp_source.addFeature(features[0]);
							comp_style.setFill(comp_fill);
							comp_layer.setStyle(comp_style);
							comp_layer.setOpacity(0.7);
						}else if(comp_val > role_4){
							comp_fill.setColor('#b30000');
							comp_source.addFeature(features[0]);
							comp_style.setFill(comp_fill);
							comp_layer.setStyle(comp_style);
							comp_layer.setOpacity(0.7);
						}
					}
				}
			}
		};
		xhr.send('');
	}else if(comp[0].length > 3){
		var sigungu = comp[0];
		var comp_val = comp[1];
		var xhr = new XMLHttpRequest();
		var url = 'selectWfsLayer.do?service=WFS&version=1.1.0&request=GetFeature&typename=vestap:sgg_layer&outputFormat=application/'
			+ 'json&srsname=EPSG:3857&maxFeatures=1&outputFormat=application/json&cql_filter=sigungu_cd=' + sigungu; //URL
		
		xhr.open('GET', url );
		xhr.setRequestHeader('Content-Type','application/json; charset=UTF-8');
		xhr.onreadystatechange = function () {
			
			if (this.readyState == 4) {
				
				if(this.status == 200) {
					
					var item = JSON.parse(this.responseText);
					var features = new ol.format.GeoJSON().readFeatures(item);
					if(comp[0] == features[0].getProperties().sigungu_cd){
						comp_comp_map.getView().fit(features[0].getGeometry());
						if(comp_val <= role_1){
							comp_fill.setColor('#fef0d9');
							comp_source.addFeature(features[0]);
							comp_style.setFill(comp_fill);
							comp_layer.setStyle(comp_style);
							comp_layer.setOpacity(0.7);
						}else if (comp_val > role_1 && comp_val <= role_2){
							comp_fill.setColor('#fdcc8a');
							comp_source.addFeature(features[0]);
							comp_style.setFill(comp_fill);
							comp_layer.setStyle(comp_style);
							comp_layer.setOpacity(0.7);
						}else if(comp_val > role_2 && comp_val <= role_3){
							comp_fill.setColor('#fc8d59');
							comp_source.addFeature(features[0]);
							comp_style.setFill(comp_fill);
							comp_layer.setStyle(comp_style);
							comp_layer.setOpacity(0.7);
						}else if(comp_val > role_3 && comp_val <= role_4){
							comp_fill.setColor('#e34a33');
							comp_source.addFeature(features[0]);
							comp_style.setFill(comp_fill);
							comp_layer.setStyle(comp_style);
							comp_layer.setOpacity(0.7);
						}else if(comp_val > role_4){
							comp_fill.setColor('#b30000');
							comp_source.addFeature(features[0]);
							comp_style.setFill(comp_fill);
							comp_layer.setStyle(comp_style);
							comp_layer.setOpacity(0.7);
						}
					}
				}
			}
		};
		xhr.send('');
	}
	
	if(comp_base_map.getLayers().a[vestap_layer]){
		comp_base_map.removeLayer(comp_base_map.getLayers().a[vestap_layer]);
	}
	
	if(comp_comp_map.getLayers().a[vestap_layer]){
		comp_comp_map.removeLayer(comp_comp_map.getLayers().a[vestap_layer]);
	}
	comp_base_map.addLayer(base_layer);
	comp_comp_map.addLayer(comp_layer);
}

function colorlamp(type,existMinus){
	var result;
	switch(type){
	case 1 : result = colorGenerator("rdylgn",existMinus); break;
	case 2 : result = colorGenerator("rdylbu",existMinus); break;
	case 3 : result = colorGenerator("bupu",existMinus); break;
	case 4 : result = colorGenerator("orrd",existMinus); break;
	case 5 : result = colorGenerator("ylgn",existMinus); break;
	case 6 : result = colorGenerator("bugn",existMinus); break;
	case 7 : result = colorGenerator("gnbu",existMinus); break;
	default    : result= colorGenerator("rdylgn",existMinus); break;
	}
	
	return result;
}

function colorGenerator(color,existMinus){
	var step = _step;
	if(existMinus === true){
		step = step + 2
	}
	var r=0;
	var g=0;
	var b=0;
	var colorSeries=new Array();
	
	/*초기값*/
	var sb = {r:0, g:0, b:0};
	var sm = {r:0, g:0, b:0};
	var st = {r:0, g:0, b:0};
	var bms = {r:0, g:0, b:0};
	var mts = {r:0, g:0, b:0};
	
	var mid=0;
	switch(color){
	case "greys" : 
			var stVal = 5;
			var std = parseInt((255-stVal)/step);
			for(var i=step;i>0;i--){
				r=std*i;
				g=r;
				b=r;
				colorSeries.push(colorCombine(r,g,b));
			}
		break;
	case "orrd" : 
		/*초기값*/
		sb = {r:254, g:240, b:217};
		sm = {r:252, g:141, b:89};
		st = {r:179, g:0, b:0};
		
		colorSeries = fn_colorSeries(sb,sm,st,step);
	break;
	case "ylgn" : 
		/*초기값*/
		sb = {r:255, g:255, b:204};
		sm = {r:120, g:198, b:121};
		st = {r:0, g:104, b:55};

		colorSeries = fn_colorSeries(sb,sm,st,step);
	break;
	case "bugn" : 
		/*초기값*/
		sb = {r:237, g:248, b:251};
		sm = {r:102, g:194, b:164};
		st = {r:0, g:109, b:44};

		colorSeries = fn_colorSeries(sb,sm,st,step);
	break;
	case "gnbu" : 
		/*초기값*/
		sb = {r:240, g:249, b:232};
		sm = {r:123, g:204, b:196};
		st = {r:8, g:104, b:172};

		colorSeries = fn_colorSeries(sb,sm,st,step);
	break;
	case "rdylgn" : 
		/*초기값*/
		sb = {r:26, g:150, b:65};
		sm = {r:255, g:255, b:191};
		st = {r:215, g:25, b:28};

		colorSeries = fn_colorSeries(sb,sm,st,step);
	break;
	case "rdylbu" : 
		/*초기값*/
		sb = {r:44, g:123, b:182};
		sm = {r:255, g:255, b:191};
		st = {r:215, g:25, b:28};

		colorSeries = fn_colorSeries(sb,sm,st,step);
	break;
	case "brbg" : 
		/*초기값*/
		sb = {r:1, g:133, b:113};
		sm = {r:245, g:245, b:245};
		st = {r:166, g:97, b:26};

		colorSeries = fn_colorSeries(sb,sm,st,step);
	break;
	case "prgn" : 
		/*초기값*/
		sb = {r:0, g:136, b:55};
		sm = {r:247, g:247, b:247};
		st = {r:123, g:50, b:148};

		colorSeries = fn_colorSeries(sb,sm,st,step);
	break;
	case "bupu" : 
		/*초기값*/
		sb = {r:237, g:248, b:251};
		sm = {r:140, g:150, b:198};
		st = {r:129, g:15, b:124};

		colorSeries = fn_colorSeries(sb,sm,st,step);
	break;
	default    : 
		var stVal = 5;
		var std = parseInt((255-stVal)/step);
		for(var i=step;i>0;i--){
			r=std*i;
			g=r;
			b=r;
			colorSeries.push(colorCombine(r,g,b));
		}
		break;
	}
	return colorSeries;
}

function fn_colorSeries(sb,sm,st,step){
	var colorSeries=new Array();
	var r=0;
	var g=0;
	var b=0;
	var mid = parseInt(step/2);
	
	/*차이값*/
	bms = {r:parseInt((sb.r - sm.r)/mid), g:parseInt((sb.g - sm.g)/mid), b:parseInt((sb.b - sm.b)/mid)};
	mts = {r:parseInt((sm.r - st.r)/mid), g:parseInt((sm.g - st.g)/mid), b:parseInt((sm.b - st.b)/mid)};
	
	for(var i=step;i>0;i--){
		if(i>mid){
			r=bms.r*(i-5)+sm.r;
			g=bms.g*(i-5)+sm.g;
			b=bms.b*(i-5)+sm.b;
		}else{
			r=mts.r*i+st.r;
			g=mts.g*i+st.g;
			b=mts.b*i+st.b;
		}
		if(r<0) r=0
		if(g<0) g=0
		if(b<0) b=0
		if(r>255) r=255
		if(g>255) g=255
		if(b>255) b=255
		colorSeries.push(colorCombine(r,g,b));
	}
	
	return colorSeries;
}

function colorCombine(r,g,b){
	if(r>=0 && r<256 && g>=0 && g<256 && b>=0 && b<256){
	return '#'+hex1to255(r)+hex1to255(g)+hex1to255(b);
	}else{
		return 'error';
	}
}

function hex1to255(decimal){
	/*0~255*/
	if(decimal>=0 && decimal<256){
	var temp = decimal;
	var def = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'];
	var hex = def.length;
	var store = new Array();
	var quo = 1;
	var rem = 0;
	var str = '';
	
	for(var i=1;i<3;i++){
		quo = parseInt(temp / hex);
		rem = temp % hex;
		temp = quo;

		store.push(def[rem]);
	}
	for(var i=store.length-1;i>=0;i--){
		str+=store[i];
	}
	
	return str;
	}else{
		return "";
	}
}

function setColorType(colorType){
	_colorType = colorType;
}

function getColorType(){
	return _colorType;
}



function changeLegend(state){
	
}
function variableLegend_origin(val, levels){
	var legend = new Array();	
	var role = (val[0] - val[(val.length)-1])/levels;
	var role_1 = parseFloat(parseFloat(val[(val.length)-1]) + parseFloat(role)).toFixed(2);
	//var role_2, role_3, role_4, role_5, role_6, role_7, role_8, role_9, role_10; 
	legend.push(role_1);
	for(var i=1; i<levels; i++){
		eval("var role_"+(i+1)+";");
		eval("role_"+(i+1)+" = parseFloat(parseFloat(role_"+i+") + parseFloat(role)).toFixed(2);");
		eval("legend.push(parseFloat(parseFloat(role_"+i+") + parseFloat(role)).toFixed(2));");
	}
	
	return legend;
}
function variableLegend(val, levels){
	var legend = new Array();	
	var role = (val[0] - val[(val.length)-1])/9;
	var role_1 = parseFloat(val[(val.length)-1]);
	
	legend.push(role_1);
	for(var i=1; i<levels-1; i++){
		eval("var role_"+(i+1)+";");
		eval("role_"+(i+1)+" = parseFloat(parseFloat(role_"+i+") + parseFloat(role)).toFixed(2);");
		eval("legend.push(parseFloat(parseFloat(role_"+i+") + parseFloat(role)).toFixed(2));");
	}
	legend.push(parseFloat(val[0]));
	return legend;
}

function legendChkOption(val, levels, existMinus){
	var legend = new Array();
	
	if($('input[name="legendChange"]:checked').val() == 'variable'){
		
		var legend = new Array();
		var role = (val[0] - val[(val.length)-1])/9;
		var role_1 = parseFloat(val[(val.length)-1]);
		legend.push(role_1);
		
		for(var i=1; i<levels-1; i++){
			
			eval("var role_"+(i+1)+";");
			eval("role_"+(i+1)+" = parseFloat(parseFloat(role_"+i+") + parseFloat(role)).toFixed(2);");
			eval("legend.push(parseFloat(parseFloat(role_"+i+") + parseFloat(role)).toFixed(2));");
		}
		legend.push(parseFloat(val[0]));
		legend.reverse();
		
	}else if($('input[name="legendChange"]:checked').val() == 'fixed'){
		var zero = 0;
		var step = _step;
		var role = 1/step;

		var legend = new Array();
		if(existMinus === true){
			legend.push(0)
		}
		var color = new Array();

		var stVal = 0;
		for(var i=1;i<=step;i++){
			stVal = parseFloat(role).toFixed(2)*(i);

			legend.push(stVal.toFixed(2));
		}
		if(existMinus === true){
			legend.push(legend.length)
		}
	}else{
		var zero = 0;
		var step = _step;
		var role = 1/step;
		
		var legend = new Array();
		var color = new Array();
		
		var stVal = 0;
		for(var i=1;i<=step;i++){
			stVal = parseFloat(role).toFixed(2)*(i);
			
			legend.push(stVal.toFixed(2));
		}
	}
	
	return legend;
}

function createLayer(existMinus){
	var step = _step;
	if(existMinus === true){
		step = step+2
	}
	for(var i=1;i<=step;i++){
		eval("source_"+i+" = new ol.source.Vector({features:[]});");
		eval("fill_"+i+" = new ol.style.Fill({ color : color["+(i-1)+"], opacity: '0.4' });");
		eval("stroke_"+i+" = new ol.style.Stroke({ color : '#f00', width: 4});");
		eval("style_"+i+" = new ol.style.Style({ stroke: new ol.style.Stroke({ color : '#f00', width: 3 }), fill : fill_"+i+" });");
		eval("level_"+i+" = new ol.layer.Vector({ source: source_"+i+", style: style_"+i+" });");
		//eval("level_"+i+" = new ol.layer.Vector({ source: source_"+i+", style: new ol.style.Style({ stroke: new ol.style.Stroke({ color: '#f00', width: 4 }), fill: new ol.style.Fill({ color: color["+(i-1)+"], opacity : '0.4' }) }) });");
		eval("level_"+i+".setOpacity(0.7);");
	}
}
function initLayer(cd, val, legend, result,onlyCoastNear){
	switch(result){
		case "SGG":
		var datas = []
		var exprFilters = []
		for(var i in cd) {
			var sigungu = cd[i].toString().substr(0,4)
			exprFilters.push(new ol.format.filter.equalTo("sigungu_cd",sigungu))
			datas.push({sigungu : sigungu, val : val[i]})
		}
		var exprFilter = null
		for(var i in exprFilters){
			if(exprFilter === null){
				exprFilter = exprFilters[i]
			}else{
				exprFilter = new ol.format.filter.or(exprFilter,exprFilters[i])
			}
		}
		//TODO : 해안인접체크 / 별도처리 어떤방식으로?
		var coastFilter = null
		if(onlyCoastNear === true){
			var coastFilters = []
			var coastArr = NEAR_COAST_SIGUNGU_CD.split(",")
			for(var i in coastArr){
				coastFilters.push(new ol.format.filter.equalTo("sigungu_cd",coastArr[i]))
			}
			for(var i in coastFilters){
				if(coastFilter === null){
					coastFilter = coastFilters[i]
				}else{
					coastFilter = new ol.format.filter.or(coastFilter,coastFilters[i])
				}
			}
		}
		if(coastFilter){
			exprFilter = new ol.format.filter.and(exprFilter,coastFilter)
		}
		var url = serverIp + "vestap/wms"
		var featureRequest = new ol.format.WFS().writeGetFeature({
			srsName: "EPSG:3857",
			featureNS: url,
			featurePrefix: "vestap",
			featureTypes: ['sgg_layer'],
			outputFormat: 'application/json',
			filter: exprFilter
		})
		jQuery.ajax({
			type: 'POST',
			url: url,
			dataType: "json",
			contentType:"text/plain;charset=UTF-8",
			data: new XMLSerializer().serializeToString(featureRequest),
			success: function (data) {
				var features = new ol.format.GeoJSON().readFeatures(data)
				for(var i in datas) {
					var data = datas[i]
					var feature = null
					for (var f in features) {
						if (data.sigungu == features[f].get("sigungu_cd")) {
							feature = features[f]
							break
						}
					}
					var val = +data.val
					var idx = -1
					if(legend[0] < legend[legend.length-1]){
						for(var l in legend){
							var legendVal = +legend[l]
							if(val <= legendVal){
								idx = l
								break
							}
						}
					}else{
						for(var l in legend){
							var legendVal = +legend[l]
							if(val >= legendVal){
								idx = l
								break
							}
						}
					}
					if(feature != null && idx > -1){
						eval("source_"+( (+idx) +1)).addFeature(feature)
					}
				}
			},
			error:function(data){
				console.error(data);
			}
		})
		/* 기존소스
		for(var i in cd){
			var sigungu = cd[i];
			var xhr = new XMLHttpRequest();
			var url = 'selectWfsLayer.do?service=WFS&version=1.1.0&request=GetFeature&typename=vestap:sgg_layer&outputFormat=application/'
				+ 'json&srsname=EPSG:3857&maxFeatures=1&outputFormat=application/json&cql_filter=sigungu_cd=' + sigungu.toString().substr(0,4); //URL
			xhr.open('GET', url );
			xhr.setRequestHeader('Content-Type','application/json; charset=UTF-8');
			xhr.onreadystatechange = function () {
				
				if (this.readyState == 4) {
					
					if(this.status == 200) {
						
						var item = JSON.parse(this.responseText);
						var features = new ol.format.GeoJSON().readFeatures(item);
						for(var i in cd){
							var sigungu_cd = features[0].getProperties().sigungu_cd;
							
							if(cd[i].toString().substr(0,4) == sigungu_cd){
								for(var j=1;j<=10;j++){
									var k=j-1;  
									if(j==1){
										eval("if(val[i] <= legend["+k+"]) {source_"+j+".addFeature(features[0]);}");
									}else if(j==10){
										eval("if(val[i] > legend["+(k-1)+"]){source_"+j+".addFeature(features[0]);}");
									}else{
										eval("if(val[i] > legend["+(k-1)+"] && val[i] <= legend["+k+"]) {source_"+j+".addFeature(features[0]);}");
									}
								}
							}
						}
					}
				}
			};
			xhr.send('');
		}
		 */
		$(".display").css("display","none");
		break;
		
	case "EMD":
		var sigungu = cd[0].substring(0,5);
		
		for(var i in cd){
			var emd = cd[i];
			var xhr = new XMLHttpRequest();
			var url = 'selectWfsLayer.do?service=WFS&version=1.1.0&request=GetFeature&typename=vestap:emd_layer&outputFormat=application/'
				+ 'json&srsname=EPSG:3857&maxFeatures=1&outputFormat=application/json&cql_filter=adm_cd=' + emd; //URL
			
			xhr.open('GET', url );
			xhr.setRequestHeader('Content-Type','application/json; charset=UTF-8');
			xhr.onreadystatechange = function () {
				
				if (this.readyState == 4) {
					
					if(this.status == 200) {
						
						var item = JSON.parse(this.responseText);
						var features = new ol.format.GeoJSON().readFeatures(item);
						for(var i in cd){
							if(cd[i]== features[0].getProperties().adm_cd){
								for(var j=1;j<=10;j++){
									var k=j-1;
									if(j==1){
										eval("if(val[i] <= legend["+k+"]) {source_"+j+".addFeature(features[0]);}");
									}else if(j==10){
										eval("if(val[i] > legend["+(k-1)+"]){source_"+j+".addFeature(features[0]);}");
									}else{
										eval("if(val[i] > legend["+(k-1)+"] && val[i] <= legend["+k+"]) {source_"+j+".addFeature(features[0]);}");
									}
								}
							}
						}
					}
				}
			};
			xhr.send('');
		}
		$(".display").css("display","none");
		break;
	default:
		break;
		
	}
}

function initShadow11111(cd, result){
	switch(result){
	case "SGG" :
		shadow_source = new ol.source.Vector({
			format : new ol.format.GeoJSON(),
			loader : function(extent,resolution, projection){
				var proj = projection.getCode();
				var url = 'selectWfsLayer.do?service=WFS&version=1.1.0&request=GetFeature&typename=vestap:sd_layer&outputFormat=application/json&'+
				'srsname='+proj+'&bbox='+extent.join(',')+','+proj;
				var xhr = new XMLHttpRequest();
				xhr.open('GET', url);
				
				//$(".display").css("display","block");
				xhr.onload = function(){
					if(xhr.status == 200){
						var item = JSON.parse(xhr.responseText);
						var features = new ol.format.GeoJSON().readFeatures(item);
						shadow_source.addFeatures(features);
						//shadow_source.addFeatures(shadow_source.getFormat().readFeatures(xhr.responseText));
						
						for(var i in features){
							//console.log(i + " 번째 "+cd[0].substring(0,2)  + " => sd_cd :" + features[i].get('sd_cd'))
							if(features[i].get('sd_cd') == cd[0].substring(0,2)){
								//shadow_source.removeFeature(features[i]);
								break;
							}
						}
					}
				}
				xhr.send();
			},
			strategy: ol.loadingstrategy.bbox
		});
		$(".display").css("display","none");
		break;
	case "EMD" : 
		shadow_source = new ol.source.Vector({
			format : new ol.format.GeoJSON(),
			loader : function(extent,resolution, projection){
				var proj = projection.getCode();
				var url = 'selectWfsLayer.do?service=WFS&version=1.1.0&request=GetFeature&typename=vestap:sgg_layer&outputFormat=application/json&'+
				'srsname='+proj+'&bbox='+extent.join(',')+','+proj;
				var xhr = new XMLHttpRequest();
				xhr.open('GET', url);
				//$(".display").css("display","block");
				xhr.onload = function(){
					if(xhr.status == 200){
						var item = JSON.parse(xhr.responseText);
						var features = new ol.format.GeoJSON().readFeatures(item);
						
						//shadow_source.addFeatures(features);
						for(var i in shadow_source.getFormat().readFeatures(xhr.responseText)){
							
							if(cd[0].substring(0,4) == shadow_layer.getSource().getFeatures()[i].get('sigungu_cd')){
								//shadow_layer.getSource().removeFeature(shadow_layer.getSource().getFeatures()[i]);
								$(".display").css("display","none");
								break;
							}
						}
					}
				}
				xhr.send();
			},
			strategy: ol.loadingstrategy.bbox
		});
	break;
	default    : 
		break;
	}
	switch(result){
	case "SGG" :
		shadow_layer = new ol.layer.Vector({
			id:"shadow",
	        source: shadow_source,
			renderMode : 'image',
			minResolution : 70,
	        style: new ol.style.Style({
	          fill: new ol.style.Fill({
	            color: '#ffffff',
	            opacity : '0.7'
	          })
	        })
	      });
		break;
	case "EMD" : 
		shadow_layer = new ol.layer.Vector({
			id:"shadow",
	        source: shadow_source,
			renderMode : 'image',
	        maxResolution : 153,
			minResolution : 20,
	        style: new ol.style.Style({
	          fill: new ol.style.Fill({
	            color: '#ffffff',
	            opacity : '0.7'
	          })
	        })
	      });
		break;
	default    : 
		break;
	}
	
	shadow_layer.setOpacity(0.8);
}

function initShadow(cd, result){
	switch(result){
	case "SGG" :
		shadow_layer = shadow_layer_sd;
		break;
	case "EMD" : 
		shadow_layer = shadow_layer_sgg;
		break;
	default    : 
		break;
	}
	
	shadow_layer.setOpacity(0.8);
}

/* 범례(고정, 가변) 선택 시 */
/*$(document).on("click", "input:radio[name='legendChange']", function() {
	var state = $(this).val();
	
	changeLegend(state);
});*/

function checkCustom(){
	if($('#map-legend').css("display") != 'none'){
		$('#map-legend').hide();
	}else if($('#map-color').css("display") != 'none'){
		$('#map-color').hide();
	}
}
(function() {
    var map, layer, start, end, vectorLayer, markerLayer, startLayer, endLayer,
    globeStartName, globeEndName,
    styleLine = {
        strokeColor: "blue",
        strokeWidth: 3,
        strokeLinecap: "round",
        strokeDashstyle: "solid"
    };

    var size = new SuperMap.Size(21, 21);
    var offset = new SuperMap.Pixel(-(size.w/2), -size.h);
    var iconstart = new SuperMap.Icon("images/start_trans.png", size, offset);
    var iconend = new SuperMap.Icon("images/end_trans.png", size, offset);
    
    //起点搜索文本提示信息框
    function addStartSugText() {
        var inputDiv = $("<div></div>")
        $("<span>起点:</span>").appendTo(inputDiv);
        var input = $("<input type='search' width='60px' id='startInput' value='四中' text='45,5063.808187,-2253.89831719'></input>");
        var p = new SuperMap.Geometry.Point(5063.808187,-2253.89831719);
        startLayer.addMarker(new SuperMap.Marker(new SuperMap.LonLat(p.x, p.y),iconstart));
        input.keyup(findStartStops);
        input.appendTo(inputDiv);
        inputDiv.appendTo($("#func"));
        
        var dataList = $("<div id='startSugPopup' class='sugpopup' style='left:630px; top:206px'></div>");
        $("<ul id='startSugList' class='dropList'></ul>").appendTo(dataList);
        dataList.appendTo($("#func"));
        
        function findStartStops(event) {
            markerLayer.clearMarkers();
            startLayer.clearMarkers();
            vectorLayer.removeAllFeatures();
            
            var txt = $("#endInput").attr("text");
            if(txt) {
                var end = txt.split(",");
                endLayer.addMarker(new SuperMap.Marker(new SuperMap.LonLat(end[1], end[2]), iconend));
            }
            
            var list = $("#startSugList");
            list.empty();
            var popup = $("#startSugPopup");
            var startName = event.target.value;
            if(!startName) {
                popup.css("display","none");
                return; 
            }
            
            var params = new SuperMap.REST.StopQueryParameters({
                keyWord: startName,
                returnPosition: true
            });
            var iserver = new SuperMap.REST.StopQueryService(DemoURL.traffic_transfer, {
                eventListeners: {
                    processCompleted: stopQuerySucceed,
                    processFailed: stopQueryFailed
                }
            });
            iserver.processAsync(params);
             //返回根据模糊的地名搜索得到的跟地名有关的所有公交站点的结果
            function stopQuerySucceed(stopQueryEvent) {
                popup.addClass("show").css("display", "block");
                if(!stopQueryEvent.result.transferStopInfos.length) {
                    $("<li>未找到匹配的起点</li>").appendTo(list);
                }else { 
                    for(var i=0,len=stopQueryEvent.result.transferStopInfos.length; i<len; i++) {
                        var station = stopQueryEvent.result.transferStopInfos[i];
                        var value = station.stopID+","+station.position.x+","+station.position.y;
                        $("<li id='" + value + "'>" + station.name + "</li>").appendTo(list);
                        list.find("li").click(function() {
                            var input = $("#startInput");
                            input.val($(this).text());
                            input.attr("text", $(this).attr("id"));
                            $("#startSugList").empty();
                            popup.removeClass("show");
                            var arr = $(this).attr("id").split(",");
                            startLayer.addMarker(new SuperMap.Marker(new SuperMap.LonLat(arr[1], arr[2]), iconstart));
                            map.setCenter(new SuperMap.LonLat(arr[1], arr[2]), 2);
                        }).hover(
                            function(){$(this).addClass("mouseOver");}, 
                            function(){$(this).removeClass("mouseOver");}    
                        );
                    } 
                }
            }
            function stopQueryFailed(result) {
                doMapAlert("",result,true);
            }
        }
    }
    //终点搜索文本提示信息框
    function addEndSugText() {
        var inputDiv = $("<div></div>")
        $("<span>终点:</span>").appendTo(inputDiv);
        var input = $("<input type='search' width='60px' id='endInput' value='宽平大路' text='148,3681.69291623,-5402.71803067'></input>");
        var p = new SuperMap.Geometry.Point(3681.69291623,-5402.71803067);
        startLayer.addMarker(new SuperMap.Marker(new SuperMap.LonLat(p.x, p.y), iconend));
        input.keyup(findEndStops);
        input.appendTo(inputDiv);
        inputDiv.appendTo($("#func"));
        
        var dataList = $("<div id='endSugPopup' class='sugpopup' style='left:844px; top:206px'></div>");
        $("<ul id='endSugList' class='dropList'></ul>").appendTo(dataList);
        dataList.appendTo($("#func"));
        
        function findEndStops(event) {
            markerLayer.clearMarkers();
            vectorLayer.removeAllFeatures();
            endLayer.clearMarkers();
            
            var txt = $("#startInput").attr("text");
            if(txt) {
                var start = txt.split(",");
                startLayer.addMarker(new SuperMap.Marker(new SuperMap.LonLat(start[1], start[2]), iconstart));
            }
            
            var list = $("#endSugList");
            list.empty();
            var popup = $("#endSugPopup");
            var endName = event.target.value;
            if(!endName) {
                popup.css("display", "none");
                return;
            }
            var params = new SuperMap.REST.StopQueryParameters({
                keyWord: endName,
                returnPosition: true
            });
            var iserver = new SuperMap.REST.StopQueryService(DemoURL.traffic_transfer, {
                eventListeners: {
                    processCompleted: stopQuerySucceed,
                    processFailed: stopQueryFailed
                }
            });
            iserver.processAsync(params);
            function stopQuerySucceed(stopQueryEvent) {
                popup.addClass("show").css("display", "block");
                if(!stopQueryEvent.result.transferStopInfos.length) {
                    $("<li>未找到匹配的起点</li>").appendTo(list);
                }else {
                    for(var i=0,len=stopQueryEvent.result.transferStopInfos.length; i<len; i++) {
                        var station = stopQueryEvent.result.transferStopInfos[i];
                        var value = station.stopID+","+station.position.x+","+station.position.y;
                        $("<li id='" + value + "'>" + station.name + "</li>").appendTo(list);
                        list.find("li").click(function() {
                            var input = $("#endInput");
                            input.val($(this).text());
                            input.attr("text", $(this).attr("id"));
                            $("#endSugList").empty();
                            popup.removeClass("show");
                            var arr = $(this).attr("id").split(",");
                            endLayer.addMarker(new SuperMap.Marker(new SuperMap.LonLat(arr[1], arr[2]), iconend));
                            map.setCenter(new SuperMap.LonLat(arr[1], arr[2]), 2);
                        }).hover(
                            function(){$(this).addClass("mouseOver");}, 
                            function(){$(this).removeClass("mouseOver");}    
                        );
                    } 
                }
            }
            function stopQueryFailed(result) {
                doMapAlert("",result,true);
            }
        }
    }
    
    function dowork() {       
        map = new SuperMap.Map("map",{controls: [                      
            new SuperMap.Control.ScaleLine(),
            new SuperMap.Control.PanZoomBar(),
            new SuperMap.Control.MousePosition(),
            new SuperMap.Control.Navigation({
                dragPanOptions: {
                    enableKinetic: true
                }
            })]
        });
        vectorLayer = new SuperMap.Layer.Vector("Vector Layer");
        markerLayer = new SuperMap.Layer.Markers("Marker Layer");
        startLayer = new SuperMap.Layer.Markers("start Layer");
        endLayer = new SuperMap.Layer.Markers("end Layer");
        layer = new SuperMap.Layer.TiledDynamicRESTLayer("World", DemoURL.changChun_Map, {transparent: true, cacheEnabled: true}, {maxResolution:"auto"}); 
        layer.events.on({"layerInitialized": addLayer});
            
        function addLayer(){
            map.addLayers([layer, vectorLayer, markerLayer, startLayer, endLayer]);
            map.setCenter(new SuperMap.LonLat(4803, -3726), 1);
            addStartSugText();
            addEndSugText();
            addDropDownMenu("换乘策略");
            addDropDownMenuItem("最少时间", lessTime);
            addDropDownMenuItem("最短距离", minDistance);
            addDropDownMenuItem("少步行", lessWalk);
            addDropDownMenuItem("少换乘", lessTransfer);
        }
        function getStartEndStopID() {
            if(!$("#startInput").attr("value")) return;
            if(!$("#endInput").attr("value")) return;

            var start = $("#startInput").attr("text");
            if(start) {
                start = parseInt(start.split(",")[0]);
            } else {
                var startStop = $("#startSugList").find("li")[0];
                var startStopID = startStop.getAttribute("id");
                var startStopName = startStop.innerHTML;
                if(!startStopID) return;
                $("#startInput").attr("text", startStopID);
                $("#startInput").attr("value", startStopName);
                start = parseInt(startStopID.split(",")[0]);
            }
            var end = $("#endInput").attr("text");
            if(end) {
                end = parseInt(end.split(",")[0]);
            } else {
                var endStop = $("#endSugList").find("li")[0];
                var endStopID = endStop.getAttribute("id");
                var endStopName = endStop.innerHTML;
                if(!endStopID) return;
                $("#endInput").attr("text", endStopID);
                $("#endInput").attr("value", endStopName);
                end = parseInt(endStopID.split(",")[0]);
            }
            
            $("#startSugList").empty();
            $("#startSugPopup").removeClass("show");
            $("#endSugList").empty();
            $("#endSugPopup").removeClass("show");
            
            return [start, end];
        }
        //最小时间公交换乘
        function lessTime() {
            var stopIDs = getStartEndStopID();
            if(stopIDs) {
                executeTrafficTransfer(stopIDs, SuperMap.REST.TransferTactic.LESS_TIME);
            }
        }
        //最短距离的公交换乘
        function minDistance() {
            var stopIDs = getStartEndStopID();
            if(stopIDs) {
                executeTrafficTransfer(stopIDs, SuperMap.REST.TransferTactic.MIN_DISTANCE);
            }
        }
        //最少步行公交换乘
        function lessWalk() {
            var stopIDs = getStartEndStopID();
            if(stopIDs) {
                executeTrafficTransfer(stopIDs, SuperMap.REST.TransferTactic.LESS_WALK);
            }
        }
        //少换乘公交换乘
        function lessTransfer() {
            var stopIDs = getStartEndStopID();
            if(stopIDs) {
                executeTrafficTransfer(stopIDs, SuperMap.REST.TransferTactic.LESS_TRANSFER);
            }
        }
        //定义公交换乘，points表示起始点坐标，transferTactic表示换乘策略类型
        //包括时间最短、距离最短、最少换乘、最少步行四种选择
        function executeTrafficTransfer(points, transferTactic) {
            window.paths = {};
            window.paths["points"] = points;
            vectorLayer.removeAllFeatures();
            execTransSolutionsQuery(points, transferTactic, "NONE"); 
        }
    }
    
    
    //transGuide代表换乘方式，transSolutions代表换乘方案，indexX代表方案索引号
    function fillTransferInfo(transGuide, transSolutions, indexX) {
        markerLayer.clearMarkers();
        vectorLayer.removeAllFeatures();
        if(transGuide && transGuide.items.length) {
            var items = transGuide.items;
            for(var itemIndex=0, itemLen=items.length; itemIndex<itemLen; itemIndex++) {
                var feature = new SuperMap.Feature.Vector();
                feature.geometry = items[itemIndex].route;
                feature.style = styleLine;
                vectorLayer.addFeatures(feature);
            }
            markerLayer.addMarker(new SuperMap.Marker(new SuperMap.LonLat(items[0].startPosition.x, items[0].startPosition.y), iconstart));
            markerLayer.addMarker(new SuperMap.Marker(new SuperMap.LonLat(items[items.length-1].endPosition.x, items[items.length-1].endPosition.y), iconend));
        }
                
        var table = $("<table id='trafficRes' class='table table-bordered'></table>");
        var startStop = $("<tr></tr>");
        $("<td class='start_transfer' ></td>").appendTo(startStop);
        $("<td colspan='2'>" + transGuide.items[0].startStopName + "</td>").appendTo(startStop);
        startStop.appendTo(table);
        var indexY = 0;
        for(var iiii=0,iiiiLen=transGuide.items.length; iiii<iiiiLen; iiii++) {
            var item = transGuide.items[iiii];
            var tr2 = $("<tr></tr>");
            if(item.isWalking) {
                $("<td class='step_transfer' ></td>").appendTo(tr2);
                $("<td>步行至:" + item.endStopName + "</td>").appendTo(tr2);
                $("<td>" + parseInt(item.distance) + "米</td>").appendTo(tr2);
            } else {
                var otherLines = transSolutions[indexX].linesItems[indexY],
                    links = "";
                if(otherLines && otherLines.lineItems.length > 1) {
                    links = "</br>还可乘坐:"
                    for(var oti=0,otLen=otherLines.lineItems.length; oti<otLen; oti++) {
                        var line = otherLines.lineItems[oti]; 
                        if(item.lineName !== line.lineName) {
                            var json = "{'lineID':" + line.lineID + ",'startStopIndex':" + line.startStopIndex + ",'endStopIndex':" + line.endStopIndex + "}";
                            links += "<a class='busLink' onclick='setTransferBus(event)'>" + line.lineName + "</a>";
                        }
                    }
                }
                $("<td class='bus_transfer'></td>").appendTo(tr2);
                $("<td>乘坐" + item.lineName + ", 在" + item.endStopName + "下车" + links + "</td>").appendTo(tr2);
                $("<td>" + item.passStopCount + "站</td>").appendTo(tr2);
                indexY ++;
            }
            tr2.appendTo(table);
        }
        var endStop = $("<tr></tr>");
        endStop.appendTo(table);
        $("<td class='end_transfer' ></td>").appendTo(endStop);
        $("<td colspan='2'>" + transGuide.items[transGuide.items.length-1].endStopName + "</td>").appendTo(endStop);
        
        return table;
    }
    
    function execTransSolutionsQuery(points, tactic, preference, chked) {
        //配置公交换乘参数
        var params = new SuperMap.REST.TransferSolutionParameters({
            solutionCount: 6,//最大换乘导引数量
            transferTactic: tactic,//公交换乘策略类型
            transferPreference: preference,
            walkingRatio: 10,//步行与公交的消耗权重比
            points: points  //起始点坐标
        });
        // 配置公交换乘服务,监听两个事件，processCompleted和processFailed,服务端成功返回查询结果时触发 
        //processCompleted 事件，服务端返回查询结果失败时触发 processFailed 事件。
        var iserver = new SuperMap.REST.TransferSolutionService(DemoURL.traffic_transfer, {
            eventListeners: {
                processCompleted: transferSolutionsSucceed,
                processFailed: transferSolutionsFailed
            }
        });
        //execute
        iserver.processAsync(params);
        //执行公交换乘成功返回结果
        function transferSolutionsSucceed(event) {
            $("#result").empty();
            markerLayer.clearMarkers();
            startLayer.clearMarkers();
            endLayer.clearMarkers();
            //在地图上叠加符号信息
            var transGuide = event.result.transferGuide,
                transSolutions = event.result.solutionItems;
            window.paths["transSolutions"] = transSolutions;
            if(!transGuide || !transSolutions) return;
                           
            var transRes = $("<div class='transferResult'></div>");
            var solution, lines, line, 
                dispStatus = "block", 
                strMessage = "";                
            for(var i=0,iLen=transSolutions.length; i<iLen; i++) {
                solution = transSolutions[i];
                var title = "";
                for(var ii=0,iiLen=solution.linesItems.length; ii<iiLen; ii++) {
                    lines = solution.linesItems[ii];
                    for(var iii=0,iiiLen=lines.lineItems.length; iii<iiiLen; iii++) {
                        line = lines.lineItems[iii];
                        if(iii !== iiiLen-1) {
                            title += line.lineName + "/";
                        } else {
                            title += line.lineName;
                        }
                    }
                    if(ii !== iiLen-1) {
                        title += " → ";
                    }
                }
                //存放默认路径,默认取数组的第一个元素
                var path = "[", pLine;
                var pathLines = solution.linesItems;
                for(var pi=0,pLen=pathLines.length; pi<pLen; pi++) {
                    pLine = pathLines[pi].lineItems[0];
                    path += "{'lineID':" + pLine.lineID + ",'startStopIndex':" + pLine.startStopIndex + ",'endStopIndex':" + pLine.endStopIndex + "}";
                    if(pi !== pLen-1) path += ",";
                }
                path += "]";
                var index = "solutions_" + i;
                window.paths[index] = path;
                
                $("<div class='transferSolution' id='transferSolution-" + i + "'><span class='transferIndex'>方案" + (i+1) + "：</span>" + title + "</div>").appendTo(transRes);
                
                if(i !== 0) {
                    dispStatus = "none";
                }
                var list = $("<div class='transferInfo' id='transferInfo-" + i + "' style='display:" + dispStatus + "'></div>").appendTo(transRes);
                list.appendTo(transRes);
                if(i !== 0) {
                    
                } else {
                    fillTransferInfo(transGuide, transSolutions, 0, 0).appendTo(list);
                }
            }
            
            strMessage += transRes.html();
            
            showWindow(strMessage, chked);
            bindSolutionsClickEvent();
            bindPreferenceClickEvent();
            function bindSolutionsClickEvent() {
                for(var i=0; i<6; i++) {
                    $("#transferSolution-"+i).click(toggleGuideItems);
                }
                function toggleGuideItems(e) {
                    for(var i=0; i<6; i++) {
                        $("#transferInfo-" + i).hide(500);
                    }
                    var id = parseInt(e.currentTarget.id.split("-")[1]);
                    $("#transferInfo-" + id).show(500);
                    execTransPathQuery(id);
                }
            }
            function bindPreferenceClickEvent() {
                $("#preferenceCheck").click(setTransferPreference);
                function setTransferPreference(e) {
                    var preference = SuperMap.REST.TransferPreference.NONE;
                    var chked = "unchecked";
                    if(e.currentTarget.checked) {
                        preference = SuperMap.REST.TransferPreference.NO_SUBWAY;
                        chked = "checked";
                    }
                    execTransSolutionsQuery(points, tactic, preference, chked);
                }
            }
        }
        //执行公交换乘失败返回结果
        function transferSolutionsFailed(event) {
            
        }
    }
    function execTransPathQuery(id) {
        var params = new SuperMap.REST.TransferPathParameters({
                points: window.paths["points"],
                transferLines: window.paths["solutions_" + id]
            });
            var iserver = new SuperMap.REST.TransferPathService(DemoURL.traffic_transfer, {
                eventListeners: {
                    processCompleted: transferPathSucceed,
                    processFailed: transferPathFailed
                }
            });
            iserver.processAsync(params);
            function transferPathSucceed(event) {
                $("#transferInfo-" + id).empty();
                var transGuide = event.result.transferGuide;
                transSolutions = window.paths["transSolutions"];
                fillTransferInfo(transGuide, transSolutions, id).appendTo($("#transferInfo-" + id));
            }
            function transferPathFailed(event) {
                
            }
    }
    dowork();
})();
function showWindow(winMessage, chked) {
    if(document.getElementById("popupWin")) {
        $("#popupWin").css("display", "block");
    } else {
        $("<div id='popupWin'></div>").addClass("popupWindow").appendTo($("#result"));
    }
    if(chked !== "checked"){
        chked = "unchecked";
    }
    var str = "";
    str += '<div class="winTitle" onMouseDown="startMove(this,event)" onMouseUp="stopMove(this,event)"><span class="title_left">交通换乘方案</span><span class="title_right"><a href="javascript:closeWindow()" title="关闭窗口">关闭</a></span><br style="clear:right"/></div>';  //标题栏
    str += '<div class="transferPreference"><span class="floatLeft">乘车偏好：</span><input type="checkbox" class="floatLeft" id="preferenceCheck" '+ chked +'/><span class="floatLeft">不乘地铁</span></div>';
    str += '<div class="winContent">';
    str += winMessage;
    str += '</div>';
    $("#popupWin").html(str);
}
window.closeWindow = function(){
    $("#result").empty();
    $("#popupWin").css("display", "none");
    $("#win_bg").css("display", "none");
}
window.startMove = function(o,e){
    var wb;
    console.log(e);
    if(document.all && e.button === 1) wb = true;
    else if(e.button === 0) wb = true;
    if(wb){
        var x_pos = parseInt(e.clientX-o.parentNode.offsetLeft);
        var y_pos = parseInt(e.clientY-o.parentNode.offsetTop);
        if(y_pos<= o.offsetHeight){
          document.documentElement.onmousemove = function(mEvent){
            var eEvent = (document.all)?event:mEvent;
            o.parentNode.style.left = eEvent.clientX-x_pos+"px";
            o.parentNode.style.top = eEvent.clientY-y_pos+"px";
          }
        }
    }
}
window.stopMove = function(o,e){
    document.documentElement.onmousemove = null;
}
              
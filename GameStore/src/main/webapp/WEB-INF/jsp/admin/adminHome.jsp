<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Admin Airo Games</title>
	<link href="<c:url value='/resources/css/admin_styles.css' />" rel="stylesheet" />
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
	<script src="http://code.highcharts.com/highcharts.js"></script>
	<script src="http://code.highcharts.com/highcharts-3d.js"></script>
	<script src="http://code.highcharts.com/modules/exporting.js"></script>

</head>

<body style="margin:0; padding:0">


<!--Wrapper this is the start of the wrapper Wrapper is width 1000px and height is auto, it will contain the following header, log navbar, main and footer. which will be displayed below.-->
<div id="wrapper">

<!--Header-->

<div id="header">

<!--Logo alro-games-->

<div id="logo"><img src="<c:url value='/resources/images/logo.png' />" width="261" height="100" alt="alro logo" /> </div> 


<!--End of alro-games-->

<jsp:include page="adminNav.jsp" />

</div>

<!--End Header-->

<!--navigation--><!--Main--------------------------------------------------------------------------------------------------------------------------->
<!--generally contains all stuff with in the page, this concludes banners, display boxes of verious offers and softwate releases.-->

<div id="main">
  <div class="title">Administration Home</div>
  <div id="dropdownmenu">    Analysis Charts: 
    <form name="analysis" action="goAdminHome" method="get">
      <select name="platform">
        <option value="0" selected="selected">All Platforms</option>
        <option value="3">PS 3</option>
        <option value="1">PS 4</option>
        <option value="2">XBox 360</option>
        <option value="4">XBox One</option>
        <option value="5">WII</option>
        <option value="6">3DS</option>
        <option value="7">PC</option>
      </select>
      <input type="submit" value="Submit"  />
    </form>
  </div>
  
	<div id="Top5OrderedGames">
	
		<script>
		
		$(function () {
			
		    $('#Top5OrderedGames').highcharts({
		        chart: {
		            type: 'pie',
		            options3d: {
		                enabled: true,
		                alpha: 45,
		                beta: 0
		            }
		        },
		        title: {
		            text: 'Top 5 Delivered Games for ${platform}'
		        },
		        tooltip: {
		            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                depth: 35,
		                dataLabels: {
		                    enabled: true,
		                    format: '{point.name}'
		                }
		            }
		        },
		        series: [{
		            type: 'pie',
		            name: 'Share of Top 5',
		            data: [
						
		               	<c:forEach var="row" items="${topXGames}" >
		            		<c:forEach var="col" items="${row}" varStatus="counter" >
		            			<c:if test="${counter.count == 1}">
		                			[' ${col} ',
			                	</c:if>
			                	<c:if test="${counter.count == 2}">
		    	            		${col}],
		        	        	</c:if>
		            		</c:forEach>
		        		</c:forEach>
		                     
		            ]
		        
		        }]
		    });
		});
		
		</script>
		</div>
		
		<div id="DeliveredOrdersByMonth">
		
		<script>
		
		$(function () {
		    $('#DeliveredOrdersByMonth').highcharts({
		        chart: {
		            type: 'column',
		            margin: 75,
		            options3d: {
		                enabled: true,
		                alpha: 10,
		                beta: 25,
		                depth: 70
		            }
		        },
		        title: {
		            text: '${currentyear}'
		        },
		        subtitle: {
		            text: 'Delivered Orders By Month'
		        },
		        plotOptions: {
		            column: {
		                depth: 25
		            }
		        },
		        xAxis: {
		            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
		        },
		        yAxis: {
		            title: {
		                text: null
		            }
		        },
		        series: [{
		            name: 'Orders',
		            data: [
		               	<c:forEach var="row" items="${monthOrders}" >
		        			${row}, 
		        		</c:forEach>
		        	]
		        }]
		    });
		});
		
		</script>
		</div>
		
		<div id="ThirtyDaysOrders">
		
		<script>
		
		$(function () {
		    $('#ThirtyDaysOrders').highcharts({
		        title: {
		            text: 'Orders in the Last 30 Days',
		            x: -20 //center
		        },
		        subtitle: {
		            text: 'Broken down by day',
		            x: -20
		        },
		        xAxis: {
		            categories: [
		                     	<c:forEach var="row" items="${listOrders30Days}" >
		                			'${row.strDate}', 
		                		</c:forEach>
		                	]
		        },
		        yAxis: {
		            title: {
		                text: 'Orders'
		            },
		            plotLines: [{
		                value: 0,
		                width: 1,
		                color: '#808080'
		            }]
		        },
		        tooltip: {
		            valueSuffix: ' Orders'
		        },
		        legend: {
		            layout: 'vertical',
		            align: 'right',
		            verticalAlign: 'middle',
		            borderWidth: 0
		        },
		        series: [{
		            name: 'All Platforms',
		            data: [
		               	<c:forEach var="row" items="${listOrders30Days}" >
		        			${row.count}, 
		        		</c:forEach>
		        	]
		        }]
		    });
		});
		
		</script>
		</div>
		
		<div id="OrdersByLevel">
	
		<script>
		
		$(function () {
			
		    $('#OrdersByLevel').highcharts({
		        chart: {
		            type: 'pie',
		            options3d: {
		                enabled: true,
		                alpha: 45,
		                beta: 0
		            }
		        },
		        title: {
		            text: 'Orders per Level'
		        },
		        tooltip: {
		            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                depth: 35,
		                dataLabels: {
		                    enabled: true,
		                    format: '{point.name}'
		                }
		            }
		        },
		        series: [{
		            type: 'pie',
		            name: 'Share of Top 5',
		            data: [
		           		['${bronzeOrdersCount.level}', ${bronzeOrdersCount.count}],
		           		['${silverOrdersCount.level}', ${silverOrdersCount.count}],
		        		['${goldOrdersCount.level}', ${goldOrdersCount.count}]  
		        	]
		        
		        }]
		    });
		});
		
		</script>
		</div>
</div>

<!--End of Main-------------------------------------------------------------------------------------------------------------------->


<jsp:include page="adminFooter.jsp" />


</div>
</body>
</html>

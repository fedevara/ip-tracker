<html>
<head>
	<title>IP Tracker</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="/css/styles.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container customDiv">
	    <div class="customDiv button-group-table">
                <div class="cell"><a href="/trackip/report" class="btn btn-info bt" role="button">Reports</a></div>
                <div class="cell"><a href="/" class="btn btn-info bt" role="button">Track IP</a></div>
        </div>

		<div>
			<h1>Reporte</h1>
			<table class="report-table" style="width:100%">
              <tr>
                <th>Pais</th>
                <th>Distancia</th>
                <th>Invocaciones</th>
              </tr>
              <tr>
                <td>${statistic.longestDistance.countryName}</td>
                <td>${statistic.longestDistance.countryDistance}</td>
                <td>${statistic.longestDistance.invocations}</td>
              </tr>
              <tr>
                <td>${statistic.shortestDistance.countryName}</td>
                <td>${statistic.shortestDistance.countryDistance}</td>
                <td>${statistic.shortestDistance.invocations}</td>
              </tr>
            </table>
			<h3>Promedio de ejecuciones del servidor: ${statistic.averageDistance}</h3>
		</div>
	</div>
</body>
</html>
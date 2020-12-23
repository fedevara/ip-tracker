<html>
<head>
	<title>IP Tracker</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<div>
			<a href="/trackip/report" class="btn btn-info" role="button">Reports</a>
			<a href="/" class="btn btn-info" role="button">Track IP</a>
		</div>

		<div>
			<h1>Reporte</h1>
			<h3>${statistic.longestDistance.countryName}</h3>
			<h3>${statistic.longestDistance.countryDistance}</h3>
			<h3>${statistic.longestDistance.invocations}</h3>
			<h3>${statistic.shortestDistance.countryName}</h3>
			<h3>${statistic.shortestDistance.countryDistance}</h3>
			<h3>${statistic.shortestDistance.invocations}</h3>
			<h3>${statistic.averageDistance}</h3>
		</div>
	</div>
</body>
</html>
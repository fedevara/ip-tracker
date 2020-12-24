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
            <h1>IP: ${ipInformation.ip}, fecha actual: ${ipInformation.consultDate}</h1>
            <h1>País: ${ipInformation.country} (${ipInformation.countryEng})</h1>
            <h1>ISO Code: ${ipInformation.isoCode}</h1>

            <#list ipInformation.language as language>
                <h1>Idiomas: ${language.name} (${language.iso639_1})</h1>
            </#list>

            <#list ipInformation.currency as currency>
                <h1>Moneda: ${currency.code} (1 ${currency.code} = ${currency.rateToUSD} U$S)</h1>
            </#list>
            <#list ipInformation.timeZone as timeZone>
                <h1>Hora: ${timeZone.time} (${timeZone.timeZone})</h1>
            </#list>

            <h1>Distancia estimada: ${ipInformation.averageDistance} kms (-34, -64) a (${ipInformation.latLong.latitude}, ${ipInformation.latLong.longitude})</h1>
		</div>
	</div>
</body>
</html>
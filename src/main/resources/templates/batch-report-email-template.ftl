<html lang="en">
<head>
    <meta charset="UTF-8" http-equiv="Content-Type" content="text/html">
    <title>Batch completion</title>
    <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 60%;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: .5em;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }
    </style>
</head>
<body>

<!-- EMAIL INTRODUCTION -->
<p>Hello ${user},</p>
<p>${description}</p>

<!-- BATCH REPORT TABLE -->
<h4>BATCH SUMMARY</h4>
<table>
    <tr>
        <th>Name</th>
        <th>Value</th>
    </tr>
    <tr>
        <td>Job name</td>
        <td>${jobName}</td>
    </tr>
    <tr>
        <td>Job parameters</td>
        <td>
            <ul>
                <#list jobParameters.parameters? keys as key>
                    <li>${key}: ${jobParameters.parameters[key]}</li>
                </#list>
            </ul>
        </td>
    </tr>

    <!-- If there are some additional params render as new rows in the table -->
    <#if additionalParams??>
        <#list additionalParams? keys as key>
            <tr>
                <td>${key}</td>
                <td>${additionalParams[key]}</td>
            </tr>
        </#list>
    </#if>
</table>

<p>King regards,</p>
<p>netvid</p>
</body>
</html>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<#if Session["isAll"]?exists>
                   ${Session["isAll"]}
</#if>
${Session["isAll"]}
</body>
</html>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title> ${keyword}ip详情</title>
    <link rel="stylesheet" href="/static/build/css/layui.css" media="all">
</head>
<body>
<table id="test" lay-filter="test"></table>
<script src="/static/build/layui.js"></script>
<script>
    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;

    });
</script>
<script type="text/javascript" src="/static/js/jquery-3.2.1.js"></script>
<script>
    layui.use('table', function () {
        var table = layui.table;
        var tableIns = table.render({ //其它参数在此省略
            elem: '#test' //或 elem: document.getElementById('test') 等
            , cols: [[{field: 'keyword', title: '关键字', width:160, sort: true}
                , {field: 'ip', title: 'ip', width: 160, sort: true}
                , {field: 'addTime', title: '时间', width: 200, sort: true}
            ]]
            , url: '/account/findipinfojson'
            , where: {'keyword': '${keyword}','ip': '${ip}'}
            , method: 'get'
            , page: true //开启分页
            , limit: 20 //默认采用60
        });
    });


</script>
</body>
</html>
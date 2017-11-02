<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title> ${keyword}ip统计</title>
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
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-mini" lay-event="addIntegral">查看</a>
</script>
<script>
    layui.use('table', function () {
        var table = layui.table;
        var tableIns = table.render({ //其它参数在此省略
            elem: '#test' //或 elem: document.getElementById('test') 等
            , cols: [[{field: 'keyword', title: '关键字', width:160, sort: true}
                , {field: 'ip', title: 'ip', width: 160, sort: true}
                , {field: 'num', title: '次数', width: 80, sort: true}
                , {fixed: 'right', width: 150, align: 'center', toolbar: '#barDemo'}
            ]]
            , url: '/account/findipjson'
            , where: {keyword: '${keyword}'}
            , method: 'get'
            , page: true //开启分页
            , limit: 20 //默认采用60
        });

        //监听工具条
        table.on('tool(test)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            window.open("/web/findipinfo?keyword="+data.keyword+"&ip="+data.ip);
        });
    });


</script>
</body>
</html>
$(document).ready(function () {
    htmlobj = $.ajax({
        url: "/account/keywords",
        async: false
    });
    $('#lst_keywords').html(htmlobj.responseText);


    $("#add").click(function () {
        var keyword = $("#keyword").val();
        var pwd = $("#secretKey").val();
        var perod = $("#hours").val();
        if (keyword == "") {
            alert("关键字不能为空");
            return;
        }
        if (perod == "") {
            alert("有效期不能为空");
            return;
        }
        if (isNaN(perod)) {
            alert("有效期非数字");
            return;
        }
        htmlobj = $.ajax({
            url: "/account/add?keyword=" + keyword + "&pwd=" + pwd + "&perod=" + perod,
            async: false
        });
        if (htmlobj.responseText == "密码有误！" || htmlobj.responseText == "数据重复！") {
            alert(htmlobj.responseText);
            return;
        }
        $('#lst_keywords').html(htmlobj.responseText);
    });
});

function del(id, keyword) {
    var pwd = $("#secretKey").val();
    if (pwd == "") {
        alert("密码不能为空");
        return;
    }
    htmlobj = $.ajax({
        url: "/account/del?id=" + id + "&keyword=" + keyword + "&pwd=" + pwd,
        async: false
    });
    if (htmlobj.responseText == "密码有误！" || htmlobj.responseText == "数据重复！") {
        alert(htmlobj.responseText);
        return;
    }
    $('#lst_keywords').html(htmlobj.responseText);
}


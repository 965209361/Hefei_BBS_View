<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%--<base href="<%=basePath%>"/>--%>
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>SpringMVC Page 首页</title>

    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <script src="../../js/jquery-1.10.2.min.js"></script>
    <![endif]-->
    <style>
        ul {
            height: 100%;
            list-style-type: none;
        }

        li {
            line-height: 40px;
            float: left;
        }

        .page_btn {
            border-radius: 4px;
            border: 1px solid #e5e9ef;
            background: #fff;
            margin-right: 10px;
            text-align: center;
            width: 38px;
            height: 38px;
            line-height: 8px;
            margin-top: 6px;
            outline: 0;
        }

        .page_btn:hover {
            border: 1px solid #4f90fb;
            color: #4f90fb;
        }

        span.pages_span {
            margin-right: 10px;
            width: 38px;
            height: 38px;
            position: relative;
            top: 10px;
        }
    </style>
</head>
<body>
<h1>${shoudao}</h1>
<table align="center">
    <h2 id="dqPage" hidden="hidden" class="disabled1 current">${page}</h2>
    <h2 id="pageCount" hidden="hidden" class="disabled1 current">${pageCount}</h2>
</table>
<div class="col-sm-5" align="center">
    <table width="1200px" border="1" cellspacing="0" id="table">
        <tr align="center">
            <th>id</th>
            <th>listUrl</th>
            <th>title</th>
            <th>content</th>
            <th>fbsj</th>
            <th>comment</th>
            <th>commentCount</th>
            <th>修改</th>
            <th>删除</th>
        </tr>
        <c:forEach items="${rows}" var="user">
            <tr>
                <td width="40px" align="center">${user.id}</td>
                <td>${user.listurl}</td>
                <td>${user.title}</td>
                <td>${user.content}</td>
                <td width="120px">${user.fbsj}</td>
                <td>${user.comment}</td>
                <td>${user.commentcount}</td>
                <td><a href="<%=basePath%>user/update/${user.id}">修改</a></td>
                <td><a href="<%=basePath%>user/delete/${user.id}">删除</a></td>
            </tr>
        </c:forEach>
        <ul>
            <li class="page_li">
                <button class="page_btn" style="width:100px" id="prePage" onclick="ClickUP(1)">上一页</button>
            </li>
            <li class="page_li">
                <button class="page_btn" id="page_btn1">1</button>
            </li>
            <li class="page_li">
                <span class="pages_span" id="prePoint">...</span>
            </li>
            <li class="page_li">
                <button class="page_btn" id="page_btn2" onclick="Clickpage(this.id)"></button>
            </li>
            <li class="page_li">
                <button class="page_btn" id="page_btn3" onclick="Clickpage(this.id)"></button>
            </li>
            <li class="page_li">
                <button class="page_btn" id="page_btn4" onclick="Clickpage(this.id)"></button>
            </li>
            <li class="page_li">
                <button class="page_btn" id="page_btn5" onclick="Clickpage(this.id)"></button>
            </li>
            <li class="page_li">
                <button class="page_btn" id="page_btn6" onclick="Clickpage(this.id)"></button>
            </li>
            <li class="page_li">
                <span class="pages_span" id="sufPoint">...</span>
            </li>
            <li class="page_li">
                <button class="page_btn" id="page_btn7" onclick="Clickpage(this.id)"></button>
            </li>
            <li class="page_li">
                <button class="page_btn" style="width:100px" id="sufPage" onclick="ClickUP(2)">下一页</button>
            </li>
        </ul>
    </table>
</div>

</body>
<script type="text/javascript">
    $(function () {
        var currentPage = $("#dqPage").text();//得到当前页数
        currentPage = parseInt(currentPage);//得到的文本转成int
        var pageNum = $("#pageCount").text();//得到当前页数
        pageNum = parseInt(pageNum);//得到的文本转成int
        $("#page_btn2").text(currentPage - 2);
        $("#page_btn3").text(currentPage - 1);
        $("#page_btn4").text(currentPage);
        $("#page_btn5").text(currentPage + 1);
        $("#page_btn6").text(currentPage + 2);
        $("#page_btn7").text(pageNum);


        $("#page_btn4").css("background-color", "#4f90fb");
        $("#page_btn4").css("border", "1px solid #ddd");
        $("#page_btn4").css("color", "#fff");


        if (currentPage == 1) {
            $("#prePage").hide();
        }

        if (currentPage == pageNum) {
            $("#sufPage").hide();
        }


        if (currentPage <= 3) {
            $("#prePoint").hide();
            $("#page_btn1").hide();
        }
        else if (currentPage == 4) {
            $("#prePoint").hide();
        }

        if (currentPage == 1) {
            $("#page_btn2").hide();
            $("#page_btn3").hide();
        }
        else if (currentPage == 2) {
            $("#page_btn2").hide();
        }

        if (currentPage >= pageNum - 2) {
            $("#sufPoint").hide();
            $("#page_btn7").hide();
        }
        else if (currentPage == pageNum - 3) {
            $("#sufPoint").hide();
        }

        if (currentPage == pageNum) {
            $("#page_btn5").hide();
            $("#page_btn6").hide();
        }

        if (currentPage == pageNum - 1) {
            $("#page_btn6").hide();
        }
    });
    $(function () {

    });
</script>
<script type="text/javascript">
    /**
     * 表示点击页码翻页
     * */
    function Clickpage(obj) {
        var currentPage = document.getElementById(obj).innerText;//得到当前页数
        currentP = parseInt(currentPage);//得到的文本转成int
        <%--window.reload("<%=basePath%>user/getnews/"+currentP+"/"+10);--%>
        $.get("<%=basePath%>user/getnews/", {"page": currentP, "pageCount": 10});

    }

    /**
     * ids=1表示上一页,ids=2表示下一页
     * @param ids
     * @constructor
     */
    function ClickUP(ids) {
        var currentPage = $("#dqPage").text();//得到当前页数
        current = parseInt(currentPage);//得到的文本转成int
        $.ajax({
            url: "<%=basePath%>user/UpAndDownPage",
            type: "get",
            data: {"page": current, "pageCount": 10, "param": ids},
            datatype: "text",
            success: function () {
            }
        });
    }

</script>
</html>
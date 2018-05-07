<%--
  Created by IntelliJ IDEA.
  User: zengqt
  Date: 2018/5/7
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        ul{
            height:100%;
            list-style-type:none;
        }

        li{
            line-height:40px;
            float:left;
        }
        .page_btn{
            border-radius:4px;
            border:1px solid #e5e9ef;
            background:#fff;
            margin-right:10px;
            text-align:center;
            width:38px;
            height:38px;
            line-height: 8px;
            margin-top:6px;
            outline:0;
        }

        .page_btn:hover{
            border:1px solid #4f90fb;
            color:#4f90fb;
        }

        span.pages_span{
            margin-right:10px;
            width:38px;
            height:38px;
            position:relative;
            top:10px;
        }
    </style>
</head>
<body>
<ul>
    <li class="page_li">
        <button class="page_btn" style="width:100px" id="prePage">上一页</button>
    </li>
    <li class="page_li">
        <button class="page_btn" id="page_btn1">1</button>
    </li>
    <li class="page_li">
        <span class="pages_span" id="prePoint">...</span>
    </li>
    <li class="page_li">
        <button class="page_btn" id="page_btn2"></button>
    </li>
    <li class="page_li">
        <button class="page_btn" id="page_btn3"></button>
    </li>
    <li class="page_li">
        <button class="page_btn" id="page_btn4"></button>
    </li>
    <li class="page_li">
        <button class="page_btn" id="page_btn5"></button>
    </li>
    <li class="page_li">
        <button class="page_btn" id="page_btn6"></button>
    </li>
    <li class="page_li">
        <span class="pages_span" id="sufPoint">...</span>
    </li>
    <li class="page_li">
        <button class="page_btn" id="page_btn7"></button>
    </li>
    <li class="page_li">
        <button class="page_btn" style="width:100px" id="sufPage">下一页</button>
    </li>
</ul>
</body>
<script type="text/javascript">
    $(function(){
        var currentPage=Number(20);
        var pageNum=Number(50);

        //给每个button赋值（第一个默认为1）
        $("#btn2").text(currentPage-2);
        $("#btn3").text(currentPage-1);
        $("#btn4").text(currentPage);
        $("#btn5").text(currentPage+1);
        $("#btn6").text(currentPage+2);
        $("#btn7").text(pageNum);

        //可改变当前页的button样式
        $("#btn4").css("background-color","#ff6384");

        //先处理"上一页"和"下一页"的情况
        if(currentPage==1)  //如果当前页为首页
        {
            $("#prePage").hide();
        }

        if(currentPage==pageNum)    //如果当前页为末页
        {
            $("#sufPage").hide();
        }

        //处理当前页小于等于3的特殊情况
        if(currentPage<=3){
            $("#prePoint").hide();
            $("#btn1").hide();
        }//当前页是4还需要hide掉第一个省略号按钮（！重要）
        else if(currentPage==4){
            $("#prePoint").hide();
        }
        //当前页是1还需要hide掉第二第三个按钮
        if(currentPage==1)
        {
            $("#btn2").hide();
            $("#btn3").hide();
        }
        //当前页是2则也需要hide掉第二个按钮（此时为-1）
        else if(currentPage==2)
        {
            $("#btn2").hide();
        }

        //最末端的特殊情况处理和最前端是一样的
        if(currentPage>=pageNum-2){
            $("#sufPoint").hide();
            $("#btn7").hide();
        }
        else if(currentPage==pageNum-3){
            $("#sufPoint").hide();
        }

        if(currentPage==pageNum)
        {
            $("#btn5").hide();
            $("#btn6").hide();
        }

        if(currentPage==pageNum-1)
        {
            $("#btn6").hide();
        }
    });
</script>
</html>

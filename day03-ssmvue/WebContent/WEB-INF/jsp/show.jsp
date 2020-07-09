<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/vue.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/axios-0.18.0.js"></script>
</head>
<body>
<table id="app" border="1">
   <tr>
   <td>编号</td>
   <td>姓名</td>
   <td>性别</td>
   <td>年龄</td>
   <td>生日</td>
   <td>头像</td>
   <td>专业</td>
   <td>操作</td>
   </tr>
   <tr v-for="stu in stus">
   <td v-text="stu.sid"></td>
   <td v-text="stu.sname"></td>
   <td v-text="stu.sex"></td>
   <td v-text="stu.age"></td>
   <td v-text="format(stu.birthday)"></td>
   <td ></td>
   <td v-text="stu.mname"></td>
   <td>操作</td>
   </tr>
   
</table>
<script type="text/javascript">
  var table= new Vue({
	  el:"#app",
	  data:{
		  stus:[]
	  },
	  created(){
		  axios.post("${pageContext.request.contextPath }/findAll.action").then(function(res){
			  table.stus=res.data;
			  
		  })
		
		  },
	  methods:{
		  
		  format(birth){
				 var year=new Date(birth).getFullYear();
				 var month=new Date(birth).getMonth()+1;
				 var date=new Date(birth).getDate();
				 return year+"-"+month+"-"+date;
	  }
	  }
  })



</script>
</body>
</html>
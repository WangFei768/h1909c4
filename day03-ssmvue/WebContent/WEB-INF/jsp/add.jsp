<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/vue.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/axios-0.18.0.js"></script>
<title>Insert title here</title>
</head>
<body>
<form action="" id="fid">
姓名：<input type="text" v-model="student.sname"><br>
性别：<input type="radio" value="男" v-model="student.sex">男
<input type="radio" value="女" v-model="student.sex">女<br>
年龄：<input type="text" v-model="student.age"><br>
生日：<input type="date" v-model="student.birthday"><br>
图片：<input type="file"><br>
班级：<select v-model="student.mid">
<option v-for="major in mlist" :value="major.mid" v-text="major.mname"></option>

</select><br>
<input type="button" @click="add" value="添加">
</form>
<script type="text/javascript">

var form = new Vue({
	el:"#fid",
	data:{
		mlist:[],
		student:{}
	},
	created(){
		axios.post("${pageContext.request.contextPath}/findMajor.action").then(function(res){
			//alert(res.data);	
			form.mlist=res.data;
		})
	},
	methods:{
		add(){
			axios.post("${pageContext.request.contextPath}/addStu.action",form.student).then(function(res){
				//alert(res.data);	
				if(res.data>0){
					location.href="${pageContext.request.contextPath}/Toshow.action"
				};
			})
		}
		
	}
	
	
});

</script>
</body>
</html>
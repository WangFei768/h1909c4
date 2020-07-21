<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/vue.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/axios-0.18.0.js"></script>

<style>
	.show{
	display:black
	}
	.hidden{
	display:none
	}

</style>

</head>
<body>
<center>
<a href="${pageContext.request.contextPath }/toAdd.action">添加</a>

<div id="did">
<input type="button" @click="del" value="删除"><br>
姓名：<input type="text" v-model="student.sname"><br>

班级：<select v-model="student.mid">
<option v-for="major in mlist" :value="major.mid" v-text="major.mname"></option>

</select><br>

生日：<input type="date" v-model="start">--<input type="date" v-model="end"><br>

<input type="button" value="查询" @click="jump(1)">





<table id="app" border="1" :class="flag2">
   <tr>
   <td>选择</td>
   <td>编号</td>
   <td>姓名</td>
   <td>性别</td>
   <td>年龄</td>
   <td>生日</td>
   <td>头像</td>
   <td>专业</td>
   <td>操作</td>
   </tr>
   <tr v-for="(stu,index) in stus">
   <td><input type="checkbox" v-model="ids" :value="stu.sid"></td>
   <td v-text="stu.sid"></td>
   <td v-text="stu.sname"></td>
   <td v-text="stu.sex"></td>
   <td v-text="stu.age"></td>
   <td v-text="format(stu.birthday)"></td>
   <td ></td>
   <td v-text="stu.mname"></td>
   <td><input type="button" @click="toUpdate(index)" value="修改"></td>
   </tr>
   <!-- <span>{{stus[0].sname}}</span> -->
</table>
当前页：{{page.pageNum}}/总页数：{{page.pages}} &nbsp;&nbsp;&nbsp;总条数：{{page.total}}<br>
<input type="button" value="首页" @click="jump(page.firstPage)">
<input type="button" value="下一页" @click="jump(page.prePage)">
<input type="button" value="上一页" @click="jump(page.nextPage)">
<input type="button" value="尾页" @click="jump(page.lastPage)">
<!-- style="display:none" -->
<form action="" id="fid" :class="flag">
<input type="hidden" v-model="student.sid">
姓名：<input type="text" v-model="student.sname"><br>
性别：<input type="radio" value="男" v-model="student.sex">男
<input type="radio" value="女" v-model="student.sex">女<br>
年龄：<input type="text" v-model="student.age"><br>
生日：<input type="date" v-model="student.birthday"><br><!--:value="format(student.birthday)" -->
图片：<input type="file"><br>
班级：<select v-model="student.mid">
<option v-for="major in mlist" :value="major.mid" v-text="major.mname"></option>

</select><br>
<input type="button" @click="update" value="修改">
</form>
</div>
</center>
<script type="text/javascript">
  var table= new Vue({
	  el:"#did",
	  data:{
		  stus:[],
		  student:{},
          mlist:[],
          flag:'hidden',
          flag2:'show',
          ids:[],
          page:{}
	  },
	  created(){
		  axios.post("${pageContext.request.contextPath }/findAll.action?pageNum=1").then(function(res){
			  table.stus=res.data.list;
			  table.page=res.data;
		  });
		  axios.post("${pageContext.request.contextPath}/findMajor.action").then(function(res){
				table.mlist=res.data;
			})
		  },
	  methods:{ 
		  format(birth){
			  var year = new Date(birth).getFullYear();
				var month1= new Date(birth).getMonth()+1;
				var month = month1<10?"0"+month1:month1;
				var date = new Date(birth).getDate()<10?"0"+new Date(birth).getDate():new Date(birth).getDate();
				return  year+"-"+month+"-"+date
	  },
	  toUpdate(i){
		  
		  this.student=this.stus[i];
		  //此处调用format格式
		  this.student.birthday = this.format(this.student.birthday);
	     this.flag="show";
	    	 this.flag2="hidden";
	  },
	  update(){
			axios.post("${pageContext.request.contextPath}/updateStu.action",table.student).then(function(res){
				//alert(res.data);	
				if(res.data>0){
					table.flag="hidden";
					table.flag2="show";
					location.href="${pageContext.request.contextPath}/Toshow.action"
				};
			})
		},
	  del(){
			axios.post("${pageContext.request.contextPath}/delStu.action",this.ids).then(function(res){
				if(res.data>0){
					 location.href="${pageContext.request.contextPath}/Toshow.action";
					
				};
			})
	  },
	  jump(pageNum){
		  axios.post("${pageContext.request.contextPath }/findAll.action",{pageNum:pageNum,student:this.student}).then(function(res){
			  table.stus=res.data.list;
			  table.page=res.data;
		  });
	  }
	  }
  })

</script>
</body>
</html>
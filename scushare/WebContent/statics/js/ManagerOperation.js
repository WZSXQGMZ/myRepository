/**
 * 
 */
function exit(){
	var comfirm = confirm("真的要退出吗？");
	
	if(comfirm ){
	$("#subForm").submit();
	}else{
		return false;
	}
}
 
function Delet(fileId){
	var comfirm = confirm("确认删除？");
	if(comfirm){
		DeletData(fileId);
	}else{
		return false;
	}
}

function Delete(userId){
	var comfirm = confirm("确认删除？");
	if(comfirm){
		DeleteUser(userId);
	}else{
		return false;
	}
}

function DeleteUser(user_Id){
	$.ajax({
		type:"post",
		url:"MDeleteUser",
		data:{userId:user_Id},
		success: function(data) {//返回成功后执行的函数，result是返回的数据
			alert("删除成功！");
			}
	});
}

function DeletData(file_Id){
	$.ajax({
		type:"post",
		url:"MDeletData",
		data:{fileId:file_Id},
		success: function(data) {//返回成功后执行的函数，result是返回的数据
			alert("删除成功！");
			}
	});
}

function conCheck(){
	var comfirm = confirm("确认审核完毕？");
	if(comfirm){
		CheckAjax();
	}else{
		return false;
	}
}

function CheckAjax(){
	var formParam = $("#confirmCheck").serialize();//序列化表格内容为字符串
	$.ajax({
		type:'post',      
        url:'MPassCheck',  
        data:formParam,
        cache:false,  
        dataType:'text',  
		
		success:function(data){
			alert("完成审核！");
		},
		error:function(){
			alert("失败!")
		}
	
	});
}

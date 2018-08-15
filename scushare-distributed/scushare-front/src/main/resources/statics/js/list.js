											function change()  
											{  
											   var x = document.getElementById("first");  
											   var y = document.getElementById("second");  
											   y.options.length = 0; // 清除second下拉框的所有内容  
											   if(x.selectedIndex == 0)
											   {
											   	y.options.add(new Option("请选择",""));
											   }
											   if(x.selectedIndex == 1)  
											   {  
											        y.options.add(new Option("材料类", "材料类",false,true));  
											        y.options.add(new Option("金属材料工程", "金属材料工程")); 
											        y.options.add(new Option("生物医学工程", "生物医学工程"));  
											        y.options.add(new Option("无机非金属材料工程", "无机非金属材料工程"));  
											        y.options.add(new Option("新能源材料", "新能源材料"));  
											   }  
											  
											   if(x.selectedIndex == 2)  
											   {  
											        y.options.add(new Option("电气工程及其自动化", "电气工程及其自动化"));  
											        y.options.add(new Option("通信工程", "通信工程", false, true));   
											        y.options.add(new Option("自动化", "自动化"));  
											        y.options.add(new Option("医学信息工程", "医学信息工程"));
											   }  
											   if(x.selectedIndex == 3)  
											   {  
											        y.options.add(new Option("信息安全", "信息安全"));  
											        y.options.add(new Option("电子信息类", "电子信息类", false, true));   
											        y.options.add(new Option("光电信息科学与工程", "光电信息科学与工程")); 
											   }  
											   if(x.selectedIndex == 4)  
											   {  
											        y.options.add(new Option("软件工程", "软件工程"));  
											   } 
											   if(x.selectedIndex == 5)  
											   {  
											        y.options.add(new Option("法学", "法学"));  
											   }  
											   if(x.selectedIndex == 6)  
											   {  
											        y.options.add(new Option("高分子材料与工程", "高分子材料与工程"));  
											   }  
											   if(x.selectedIndex == 7)  
											   {  
											        y.options.add(new Option("档案学", "档案学")); 
											        y.options.add(new Option("公共管理类", "公共管理类"));  
											        y.options.add(new Option("社会工作", "社会工作"));  
											        y.options.add(new Option("信息资源管理", "信息资源管理"));  
											        y.options.add(new Option("哲学", "哲学"));  
											        y.options.add(new Option("信息管理与信息系统", "信息管理与信息系统"));  
											   }  
											   if(x.selectedIndex == 8)  
											   {  
											        y.options.add(new Option("卫生检验与检疫", "卫生检验与检疫"));
											        y.options.add(new Option("预防医学", "预防医学"));
											        y.options.add(new Option("食品卫生与营养学", "食品卫生与营养学"));
											   }  
											   if(x.selectedIndex == 9)  
											   {  
											        y.options.add(new Option("口腔医学", "口腔医学")); 
											        y.options.add(new Option("临床医学（口腔医学）", "临床医学（口腔医学）"));
											        y.options.add(new Option("口腔医学技术", "口腔医学技术"));
											   }  
											   if(x.selectedIndex == 10)  
											   {  
											        y.options.add(new Option("护理学", "护理学"));  
											        y.options.add(new Option("临床医学", "临床医学"));  
											        y.options.add(new Option("医学技术类", "医学技术类"));  											       
											   }  
											   if(x.selectedIndex == 11)  
											   {  
											        y.options.add(new Option("临床药学", "临床药学")); 
											        y.options.add(new Option("药学", "药学"));  
											   }  
											   if(x.selectedIndex == 12)  
											   {  
											        y.options.add(new Option("化学类", "化学类"));  
											   }  
											   if(x.selectedIndex == 13)  
											   {  
											        y.options.add(new Option("过程装备与控制", "过程装备与控制"));  
											        y.options.add(new Option("化学工程与工艺", "化学工程与工艺"));  
											        y.options.add(new Option("制药工程", "制药工程"));  
											        y.options.add(new Option("生物工程", "生物工程"));  
											        y.options.add(new Option("安全工程", "安全工程"));  
											        y.options.add(new Option("冶金工程", "冶金工程"));  
											   }  
											   if(x.selectedIndex == 14)  
											   {  
											        y.options.add(new Option("法医学", "法医学")); 
											        y.options.add(new Option("基础医学", "基础医学"));  
											   }  
											   if(x.selectedIndex == 15)  
											   {  
											        y.options.add(new Option("计算机类", "计算机类"));  
											        y.options.add(new Option("计算机科学与技术（计算金融）", "计算机科学与技术（计算金融）"));  
											        y.options.add(new Option("物联网工程", "物联网工程"));  
											   }  
											   if(x.selectedIndex == 16)  
											   {  
											        y.options.add(new Option("城乡规划", "城乡规划"));  
											        y.options.add(new Option("工程力学", "工程力学")); 
											        y.options.add(new Option("环境工程", "环境工程"));
											        y.options.add(new Option("环境科学", "环境科学"));
											        y.options.add(new Option("建筑学", "建筑学"));
											        y.options.add(new Option("风景园林", "风景园林5"));
											        y.options.add(new Option("土木类", "土木类"));
											   }  
											   if(x.selectedIndex == 17)  
											   {  
											        y.options.add(new Option("国际经济与贸易", "国际经济与贸易"));
											        y.options.add(new Option("经济学类", "经济学类"));
											        y.options.add(new Option("金融学类", "金融学类"));
											   }  
											   if(x.selectedIndex == 18)  
											   {  
											        y.options.add(new Option("机械设计制造及其自动化", "机械设计制造及其自动化"));
											        y.options.add(new Option("材料科学与工程", "材料科学与工程"));
											        y.options.add(new Option("工业工程", "工业工程"));
											   }  
											   if(x.selectedIndex == 19)  
											   {  
											        y.options.add(new Option("历史学类", "历史学类")); 
											        y.options.add(new Option("旅游管理类", "旅游管理类"));
											   }  
											   if(x.selectedIndex == 20)  
											   {  
											        y.options.add(new Option("服装与服饰设计", "服装与服饰设计")); 
											        y.options.add(new Option("纺织工程", "纺织工程"));
											        y.options.add(new Option("轻化工程", "轻化工程"));
											        y.options.add(new Option("食品科学与工程", "食品科学与工程"));
											   }  
											   if(x.selectedIndex == 21)  
											   {  
											        y.options.add(new Option("工商管理类", "工商管理类"));
											        y.options.add(new Option("电子商务", "电子商务"));
											        y.options.add(new Option("工程管理", "工程管理"));
											        y.options.add(new Option("工商管理（运营管理）", "工商管理（运营管理）"));
											        y.options.add(new Option("工业工程", "工业工程"));
											        y.options.add(new Option("管理科学", "管理科学"));
											   }  
											   if(x.selectedIndex == 22)  
											   {  
											        y.options.add(new Option("生物科学类", "生物科学类"));  
											   }  
											   if(x.selectedIndex == 23)  
											   {  
											        y.options.add(new Option("数学与应用数学", "数学与应用数学"));
											        y.options.add(new Option("数学类", "数学类"));
											   }  
											   if(x.selectedIndex == 24)  
											   {  
											        y.options.add(new Option("能源动力与工程", "能源动力与工程"));
											        y.options.add(new Option("水利类", "水利类"));
											   }
											   if(x.selectedIndex == 25)  
											   {  
											        y.options.add(new Option("俄语", "俄语"));
											        y.options.add(new Option("法语", "法语"));
											        y.options.add(new Option("日语", "日语"));
											        y.options.add(new Option("西班牙语", "西班牙语"));
											        y.options.add(new Option("英语", "英语"));
											   }
											   if(x.selectedIndex == 26)  
											   {  
											        y.options.add(new Option("新闻传播学类", "新闻传播学类"));
											        y.options.add(new Option("汉语言文学", "汉语言文学"));
											        y.options.add(new Option("中国语言文学类", "中国语言文学类"));
											   }
											   if(x.selectedIndex == 27)  
											   {  
											        y.options.add(new Option("核工程与核技术", "核工程与核技术"));
											        y.options.add(new Option("微电子科学与工程", "微电子科学与工程"));
											        y.options.add(new Option("物理学类", "物理学类"));
											   }
											   if(x.selectedIndex == 28)  
											   {  
											        y.options.add(new Option("美术学", "美术学"));
											        y.options.add(new Option("绘画", "绘画"));
											        y.options.add(new Option("视觉传达设计", "视觉传达设计"));
											        y.options.add(new Option("环境设计", "环境设计"));
											        y.options.add(new Option("动画", "动画"));
											        y.options.add(new Option("音乐学", "音乐学"));
											        y.options.add(new Option("广播电视编导", "广播电视编导"));
											        y.options.add(new Option("表演", "表演"));
											        y.options.add(new Option("舞蹈表演", "舞蹈表演"));
											   }
											   if(x.selectedIndex == 29)  
											   {  
											        y.options.add(new Option("材料成型及控制工程", "材料成型及控制工程"));
											        y.options.add(new Option("测控技术与仪器", "测控技术与仪器"));
											        y.options.add(new Option("工业设计", "工业设计"));
											        y.options.add(new Option("机械设计制造及自动化", "机械设计制造及自动化"));
											   }
											   if(x.selectedIndex == 30)  
											   {  
											        y.options.add(new Option("航空航天类", "航空航天类"));  
											   }
											  
											}  
											function skip()
											{
											    window.location.href="Manager_DataCheckDeaper.html";
											}
										    
											 major = {
											    	"材料科学与工程学院":
											  	 [	 
											        "材料类",
											        "金属材料工程", 
											        "生物医学工程", 
											        "无机非金属材料工程", 
											        "新能源材料"] ,  
											  
											    "电气信息学院":  
											   	[
											        "电气工程及其自动化",  
											        "通信工程", 
											        "自动化",   
											        "医学信息工程"]  ,

											    "电子信息学院":
											   [
											        "信息安全",  
											        "电子信息类",
											        "光电信息科学与工程"],
											  
											    "软件学院":
											   [  
											        "软件工程"],
											 
											    "法学院":  
											   [ 
											        "法学"],
											    "高分子材料与工程学院":
											   [ 
											        "高分子材料与工程"],

											    "公共管理学院":  
											   [  
											        "档案学",  
											        "公共管理类",  
											        "社会工作",  
											        "信息资源管理",
											        "哲学",   
											        "信息管理与信息系统"],  

											    "华西公共卫生学院":
											   [  
											        "卫生检验与检疫", 
											        "预防医学", 
											        "食品卫生与营养学"],
											    
											    "华西口腔医学院": 
											   [
											        "口腔医学", 
											        "临床医学（口腔医学）",
											        "口腔医学技术"],

											    "华西临床医学院": 
											   [  
											        "护理学",  
											        "临床医学",   
											        "医学技术类"],

											    "华西药学院":  
											   [  
											        "临床药学", 
											        "药学"], 

											    "化学学院": 
											   [  
											        "化学类"],

											    "化学工程学院": 
											   [ 
											        "过程装备与控制",  
											        "化学工程与工艺", 
											        "制药工程",   
											        "生物工程",   
											        "安全工程",   
											        "冶金工程"],

											    "华西基础医学与法医学院": 
											   [  
											        "法医学", 
											        "基础医学"],

											    "计算机学院": 
											   [ 
											        "计算机类",  
											        "计算机科学与技术（计算金融）",  
											        "物联网工程"],

											    "建筑与环境学院":  
											   [  
											        "城乡规划",
											        "工程力学", 
											        "环境工程", 
											        "环境科学",
											        "建筑学",
											        "风景园林",
											        "土木类"],

											    "经济学院":
											   [  
											        "国际经济与贸易",
											        "经济学类",
											        "金融学类"],

											    "匹兹堡":  
											   [  
											        "机械设计制造及其自动化", 
											        "材料科学与工程",
											        "工业工程"],  

											    "历史文化学院(旅游学院)":
											   [  
											        "历史学类", 
											        "旅游管理类"],

											    "轻纺与食品学院":  
											   [ 
											        "服装与服饰设计",
											        "纺织工程",
											        "轻化工程",
											        "食品科学与工程"], 

											    "商学院": 
											   [  
											        "工商管理类", 
											        "电子商务",
											        "工程管理", 
											        "工商管理（运营管理）", 
											        "工业工程", 
											        "管理科学"],
											   
											    "生命科学学院": 
											   [ 
											        "生物科学类"],

											    "数学学院": 
											   [  
											        "数学与应用数学",
											        "数学类"],  

											    "水利水电学院":  
											   [  
											        "能源动力与工程", 
											        "水利类"],

											    "外国语学院":
											   [  
											        "俄语", 
											        "法语",
											        "日语",
											        "西班牙语",
											        "英语"
											   ],
											    "文学与新闻学院":  
											   [  
											        "新闻传播学类", 
											        "汉语言文学",
											        "中国语言文学类"],

											    "物理科学与技术学院": 
											   [  
											        "核工程与核技术", 
											        "微电子科学与工程",
											        "物理学类"],

											    "艺术学院": 
											   [  
											        "美术学", 
											        "绘画",
											        "视觉传达设计",
											        "环境设计", 
											        "动画",
											        "音乐学",
											        "广播电视编导",
											        "表演",
											        "舞蹈表演"],
											   

											    "制造科学与工程学院":
											   [  
											        "材料成型及控制工程", 
											        "测控技术与仪器",
											        "工业设计",
											        "机械设计制造及自动化"],

											    "航天科学与工程学院":  
											   [ 
											        "航空航天类"  
											   ]
											};
											 function getMajorsByCollege(college){
												 return major[college];
											 }
											 function getCollegeByMajor(string){
												 for(key in major){
													 if(major[key].indexOf(string)>=0)
														 return key;
												 }
											 }
											 function getAllColleges(){
												 var colleges = new Array();
												 for(key in major){
													 colleges[colleges.length] = key;
												 }
												 return colleges;
											 }
											  





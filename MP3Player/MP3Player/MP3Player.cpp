#define _CRT_SECURE_NO_WARNINGS
#define _CRT_SECURE_NO_DEPRECATE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <windows.h> 
#include <io.h>
#include <ctype.h>
#include <time.h>

#pragma comment(lib, "WINMM.LIB")

#define N 100
#define M 20
typedef struct
{
	char title[50];
	long length;
	char path[100];
}Music;

char mciStr[N] = "";		//全局变量，用于记录mciSendString的命令
char str[M] = "";
char filepath[50]="";
unsigned int volume = 500;	//音量
int currentListNumber = 1;	//当前播放列表编号
int judgement = 1;
int p = 0;					//当前播放列表歌曲数目
int *H = &p;
int playMode = 1;			//当前播放模式
int playStatu = 1;	//播放状态,1为继续播放，0为停止播放
FILE *fp;
Music *musicList;			//当前播放列表
Music currentMusic;			//当前播放歌曲
HANDLE PLAYMENU;
//HANDLE checkStop;

void menu();
void DisplayList(int judge);
Music SearchMusic();
void DelMusic();
void ModifyList();
void ChangeList();
Music* SearchLocalMusic(Music *musicList, int *H);
void ChangePlayMode();
int PlayMusic(char* path);
int PlayMenu();
void PlaseMusic();
void ContinueMusic();
void SpeedMusic();
void BackwardMusic();
void UpVolume();
void DownVolume();
void SoundOff();
void SoundOn();
Music* ChangeMusic(Music *musicList, Music currentMusic, int musicQuantity, int norl);
void DisplayMusicInfo(Music music);
void LoadMusicList(int listNumber);
void SaveMusicList(int listNumber);
void Play();
void checkStopFun();
DWORD WINAPI PlayMenuThread(LPVOID lParam);

/*
该函数为主函数。

author:彭涵钧2015141463132
version:1.1
v1.1:增加了输入错误的友好提示
*/
void main()
{
	int choice;
	system("color D"); 
	printf("\n\n\n");
	printf("\t\t\t\t****************************\n");
	printf("\t\t\t\t*欢迎使用MP3音乐播放器!    *\n");
	printf("\t\t\t\t*版本信息：V1.0\t\t   *\n\t\t\t\t*作者：彭涵钧、徐含、尹远航*\n");
	printf("\t\t\t\t****************************\n\n\n\n");
	LoadMusicList(currentListNumber);
	printf("请输入1进入主菜单,输入0退出播放器\n");
	scanf("%d", &choice);
	if (choice == 0)
	{
		return;
	}
	else if (choice!=0&&choice!=1)
	{
		printf("输入错误，系统自动退出\n");
		return;
	}
	else
	{
		while (1)
		{
			menu();
			printf("请选择您所需要的功能:\n");
			scanf("%d", &choice);
			getchar();
			switch (choice)
			{
			case 1:
				DisplayList(currentListNumber);
				break;
			case 2:
				SearchMusic();
				break;
			case 3:
				DelMusic();
				break;
			case 4:
				ModifyList();
				break;
			case 5:
				ChangeList();
				break;
			case 6:
				musicList = SearchLocalMusic(musicList, H);
				SaveMusicList(currentListNumber);
				LoadMusicList(currentListNumber);
				break;
			case 7:
				ChangePlayMode();
				break;
			case 8:
				Play();
				break;
			case 0:
				exit(0);
			default:
				break;
			}
		}
	}

	free(musicList);
	musicList = NULL;

	return;
}

/*
该函数用于显示程序的主菜单。

author:彭涵钧2015141463132
version:1.1:打印播放列表改为一行2个，使界面简洁。
v1.1:
*/
void menu()
{
	printf("\n");
	printf("\t1.查看歌曲列表\t	2.查找歌曲\n\n");
	printf("\t3.删除歌曲\t	4.修改歌曲列表\n\n");
	printf("\t5.切换歌曲列表\t	6.检索本地歌曲\n\n");
	printf("\t7.切换播放模式\t	8.播放歌曲\n\n");
	printf("\t0.退出播放器\n\n");

	return;
}//显示主菜单

 /*
 该函数用于显示播放菜单，并根据用户的输入进行函数的跳转。

 author:彭涵钧2015141463132
 version:1.2
 v1.1:增加了返回主菜单时的关闭音乐播放功能
 v1.2:增加了歌曲切换时的关闭音乐播放功能
 */
int PlayMenu()
{
	int choice;
	printf("开始播放歌曲:%s\n\n",currentMusic.title);
	printf("请选择功能：\n\n");
	printf("\t1.暂停\t\t\t\t2.继续\n\n");
	printf("\t3.快进\t\t\t\t4.快退\n\n");
	printf("\t5.上一首\t\t\t6.下一首\n\n");
	printf("\t7.增大音量\t\t\t8.减小音量\n\n");
	printf("\t9.关闭音量\t\t\t10.恢复音量\n\n");
	printf("\t11.查看歌曲信息\t\t\t0.返回主菜单\n\n");
	scanf("%d", &choice);
	switch (choice)
	{
	case 1:
		PlaseMusic();
		break;
	case 2:
		ContinueMusic();
		break;
	case 3:
		SpeedMusic();
		break;
	case 4:
		BackwardMusic();
		break;
	case 5:
		currentMusic = *ChangeMusic(musicList, currentMusic, *H, 0);
		strcpy(mciStr, "close mp3");
		mciSendStringA(mciStr, 0, 0, 0);
		playStatu = 2;
		return 2;
		break;
	case 6:
		currentMusic = *ChangeMusic(musicList, currentMusic, *H, 1);
		strcpy(mciStr, "close mp3");
		mciSendStringA(mciStr, 0, 0, 0);
		playStatu = 2;
		return 2;
		break;
	case 7:
		UpVolume();
		break;
	case 8:
		DownVolume();
		break;
	case 9:
		SoundOff();
		break;
	case 10:
		SoundOn();
		break;
	case 11:
		DisplayMusicInfo(currentMusic);
		break;
	case 0:
		strcpy(mciStr, "close mp3");
		mciSendStringA(mciStr, 0, 0, 0);
		playStatu = 2;
		return 0;
	default:
		break;
	}

	playStatu = 1;
	return 1;
}

 /*
 该函数允许用户对播放歌曲，并跳转至播放菜单

 author:彭涵钧2015141463132
 version:1.0
 author:徐含
 v1.1:2016/7/28 添加检测歌曲播放结束线程
 */
int PlayMusic(char* path)
{
	char mciStr[100];
	char str[20];
	//Music pMusic;
	printf("\n\n");
	//printf("请输入要播放的歌曲的路径：\n");
	//scanf("%s",&pMusic.path);
	strcpy(mciStr, "open ");
	strcat(mciStr, path);
	strcat(mciStr, " alias mp3");
	mciSendStringA(mciStr, 0, 0, 0);
	if (playMode == 2)
	{
		mciSendStringA("play movie repeat", 0, 0, 0);
	}
	//printf("Open\n");
	strcpy(mciStr, "play mp3");
	mciSendStringA(mciStr, 0, 0, 0);
	//printf("Play\n");
	strcpy(mciStr, "setaudio mp3 volume to ");
	strcat(mciStr, _itoa(volume,str,10));
	mciSendStringA(mciStr, 0, 0, 0);

	int playStatu = 1;
	while (playStatu == 1)
	{
		playStatu = PlayMenu();
	}
	
	return playStatu;
}

/*
该函数暂停当前播放的歌曲

author:彭涵钧2015141463132
version:1.0
*/
void PlaseMusic()
{
	strcpy(mciStr, "pause mp3");
	mciSendStringA(mciStr, 0, 0, 0);
	printf("Pause\n");
}
	
/*
该函数继续当前播放的歌曲

author:彭涵钧2015141463132
version:1.0
*/
void ContinueMusic()
{
	strcpy(mciStr, "resume mp3");
	mciSendStringA(mciStr, 0, 0, 0);
	printf("Resume\n");
}

/*
该函数快进当前播放的歌曲10s

author:彭涵钧2015141463132
version:1.1
v1.1:将打印毫秒改为分和秒显示
*/
void SpeedMusic()
{
	int position = 0;
	int step = 10000;
	strcpy(mciStr, "status mp3 position");
	mciSendStringA(mciStr, str, M, 0);
	position = strtol(str, NULL, 10);
	printf("position = %d:%d\n", position/1000/60, (position/1000)%60);
	memset(str, 0, sizeof(str));
	position = position + step;
	//strcpy(mciStr, "play mp3 from ");
	//itoa(position, str, 10);
	//strcat(mciStr, str);
	//mciSendString(mciStr, 0, 0, 0);
	//printf("Go forward 10s\n");

	_itoa(position, str, 10);
	sprintf(mciStr, "seek mp3 to %s", str);
	mciSendStringA(mciStr, 0, 0, 0);
	mciSendStringA("play mp3", 0, 0, 0);
	printf("Go forward 10s\n");
}

/*
该函数快退当前播放的歌曲10s

author:彭涵钧2015141463132
version:1.1
v1.1:将打印毫秒改为分和秒显示
*/
void BackwardMusic()
{
	int position = 0;
	int step = 10000;
	strcpy(mciStr, "status mp3 position");
	mciSendStringA(mciStr, str, M, 0);
	position = strtol(str, NULL, 10);
	printf("position = %d:%d\n", position / 1000 / 60, (position / 1000) % 60);
	memset(str, 0, sizeof(str));
	position = position - step;
	strcpy(mciStr, "play mp3 from ");
	_itoa(position, str, 10);
	strcat(mciStr, str);
	mciSendStringA(mciStr, 0, 0, 0);
	printf("Go backward 10s\n");
}

/*
该函数用于增大音量。

author:彭涵钧2015141463132
version:1.2
v1.1:增加音量的上限，将volume设置为全局变量，初始值500.
v1.2:音量不设置上限，一次音量的改动为200（使音量调节更加快捷）
*/
void UpVolume()
{
	char str1[10];
	volume = volume + 200;
	strcpy(mciStr, "setaudio mp3 volume to ");
	sprintf(str1, "%d", volume);//将int型转换为char型
	strcat(mciStr, str1);
	mciSendStringA(mciStr, 0, 0, 0);
	strcpy(mciStr, "status mp3 volume");
	mciSendStringA(mciStr, str, M, 0);
	volume = strtol(str, NULL, 10);
	printf("volume = %u\n", volume);
	printf("Up the volume\n");
}

/*
该函数用于减小音量。

author:彭涵钧2015141463132
version:1.2
v1.2:改动与UpVolume相对应。
*/
void DownVolume()
{
	char str1[10];
	volume = volume - 200;
	strcpy(mciStr, "setaudio mp3 volume to ");
	sprintf(str1, "%d", volume);//将int型转换为char型
	strcat(mciStr, str1);
	mciSendStringA(mciStr, 0, 0, 0);
	strcpy(mciStr, "status mp3 volume");
	mciSendStringA(mciStr, str, M, 0);
	volume = strtol(str, NULL, 10);
	printf("volume = %u\n", volume);
	printf("Down the volume\n");
}

/*
该函数用于静音。

author:彭涵钧2015141463132
version:1.0
*/
void SoundOff()
{
	strcpy(mciStr, "setaudio mp3 off");
	mciSendStringA(mciStr, 0, 0, 0);
	printf("Set off\n");
}

/*
该函数用于关闭静音。

author:彭涵钧2015141463132
version:1.0
*/
void SoundOn()
{
	strcpy(mciStr, "setaudio mp3 on");
	mciSendStringA(mciStr, 0, 0, 0);
	printf("Set on\n");
}

/*
此函数用于切换播放列表并显示

author:尹远航
v1.0
author:徐含
v1.1:2016/7/28 修改了播放列表读取错误的bug、将加载播放列表功能移出，用LoadMusicList代替
*/
void DisplayList(int judge)
{
	int i = 0;
	printf("当前列表：%d\n", judge);
	if (p == 0)
	{
		printf("播放列表中没有歌曲\n");
		return;
	}
	for (int i = 0; i < p; i++)
	{
		printf("%d.\t%s\n", i + 1, (*(musicList + i)).title);
	}
	printf("\n");

	return;
}

/*
此函数用于查找当前列表内的歌曲

author:尹远航2015141463226
version：1.0

*/
Music SearchMusic()
{
	Music music;//
	int i = 0;
	int j = 0;
	char song[50];
	printf("请输入要查找的歌曲名：\n");
	scanf("%s", song);
	for (i = 0; i < *H;)
	{
		if (strcmp(song, (*(musicList + i)).title) == 0)
		{
			printf("已查找到该歌曲！\n");
			music = *(musicList + i);
			printf("%s %s\n", (*(musicList + i)).title, (*(musicList + i)).path);
			j = 1;
			break;
		}
		else
		{
			i++;
		}
	}
	if (j != 1)
	{
		printf("未找到该歌曲！\n");
		getchar();
	}
	return music;
}

/*
此函数用于删除播放列表中的歌曲

author:尹远航
v1.0
author:徐含
v1.1:2016/7/28 将播放列表保存功能用SaveMusicList代替*/
void DelMusic()
{
	int a = 0;
	int i = 0;
	int j = 0;
	int m = 0;
	int n = 0;
	int song = 0;
	DisplayList(judgement);
	printf("请选择要删除的歌曲编号：\n");
	scanf("%d", &song);
	if (song > 0 && song <= p)
	{
		j = 1;
	}
	if (j != 1)
	{
		printf("未找到该歌曲！\n");
		getchar();
	}
	else
	{
		printf("您确认要删除该歌曲吗？ \n若是，请输入1；若不是，请输入0：\n");
		scanf_s("%d", &a);
		if (a == 1)
		{
			strcpy((*(musicList + song - 1)).path, "");
			printf("%s已删除\n\n", (*(musicList + song - 1)).title);
			SaveMusicList(currentListNumber);
			LoadMusicList(currentListNumber);
		}
		else if (a == 0)
		{
			getchar();
		}
		else
		{
			printf("输入的指令错误！\n");
		}
	}
}

/*
此函数用于修改歌曲信息

author:尹远航
v1.0
author:徐含
v1.1:2016/7/28 将保存功能用SaveMusicList代替
*/
void ModifyList()
{
	int a = 0;
	int i = 0;
	int j = 0;
	int m = 0;
	int n = 0;
	int choice;
	int song=0;
	DisplayList(judgement);
	printf("请选择要修改的歌曲编号：\n");
	scanf("%d", &song);
	if (song > 0 || song <= p)
	{
		j = 1;
		m = song;
	}
	if (j != 1)
	{
		printf("未找到该歌曲！");
		getchar();
	}
	printf("请选择要修改的信息：\n");//让用户输入想要修改的内容
	printf("1、名称   2、长度   3、路径\n");
	scanf("%d", &choice);
	switch (choice)
	{
	case 1:
		printf("请输入修改后的信息：\n");
		scanf("%s", &(*(musicList + m - 1)).title);
		getchar();
		break;
	case 2:
		printf("请输入修改后的信息：\n");
		scanf("%ld", &(*(musicList + m - 1)).length);
		getchar();
		break;
	case 3:
		printf("请输入修改后的信息：\n");
		scanf("%s", &(*(musicList + m - 1)).path);
		getchar();
		break;
	default:
		printf("对不起，没有该选项！\n");
		break;
	}
	SaveMusicList(currentListNumber);
	LoadMusicList(currentListNumber);
}

/*
此函数用于切换歌单

author:尹远航
v1.0
author:徐含
v1.1:2016/7/28 添加SaveMusicList函数
*/
void ChangeList()
{
	printf("请选择要切换的歌单：\n");
	printf("1、2、3、4、\n");
	scanf_s("%d", &judgement);
	switch (judgement)
	{
	case 1:
		SaveMusicList(currentListNumber);
		currentListNumber = judgement;
		LoadMusicList(currentListNumber);
		DisplayList(currentListNumber);
		break;
	case 2:
		SaveMusicList(currentListNumber);
		currentListNumber = judgement;
		LoadMusicList(currentListNumber);
		DisplayList(currentListNumber);
		break;
	case 3:
		SaveMusicList(currentListNumber);
		currentListNumber = judgement;
		LoadMusicList(currentListNumber);
		DisplayList(currentListNumber);
		break;
	case 4:
		SaveMusicList(currentListNumber);
		currentListNumber = judgement;
		LoadMusicList(currentListNumber);
		DisplayList(currentListNumber);
	default:
		printf("输入错误！");
		getchar();
		break;
	}
}

/*
该函数用于显示歌曲信息
music:要显示信息的歌曲

version:1.0
author:徐含2015141463200
*/
void DisplayMusicInfo(Music music)
{
	long length = 0;
	char str[10];
	//获取总长度
	mciSendStringA("status mp3 length", str, M, 0);
	length = strtol(str, NULL, 10);
	printf("歌曲标题：%s\n", music.title);
	printf("歌曲长度：%d:%d\n", length / 1000 / 60, (length / 1000) % 60);
	printf("歌曲路径：%s\n", music.path);
}

/*
该函数用于改变播放模式

version:1.0
author:徐含2015141463200
*/
void ChangePlayMode()
{
	char mode = '0';
	printf("1.播放单曲 2.单曲循环 3.列表循环 4.随机循环\n");
	printf("选择播放模式：");
	scanf_s("%c", &mode, 1);
	getchar();
	switch (mode)
	{
	case '1':
		playMode = 1;
		break;
	case '2':
		playMode = 2;
		break;
	case '3':
		playMode = 3;
		break;
	case '4':
		playMode = 4;
		break;
	default:
		printf("没有 %c 选项", mode);
		break;
	}
}

/*
该函数用于播放上/下一首歌曲，并返回该歌曲的指针
musicList:歌曲列表头指针
H:列表中的歌曲数量
norl:值为0时播放上一首，值为1时播放下一首
currentMusic:当前正在播放的歌曲

version:1.2
author:徐含2015141463200
V1.1:2016/7/25 修改了函数的返回类型为Music*
V1.2:2016/7/27 添加了参数currentMusic
*/
Music* ChangeMusic(Music *musicList, Music currentMusic, int musicQuantity, int norl)
{
	if (musicQuantity <= 0)
	{
		printf("musicQuantity error\n\n");
		return NULL;
	}
	int index = 0;	//记录当前播放歌曲所在位置
	for (index = 0; index < musicQuantity;)
	{
		if (strcmp(currentMusic.path, (*(musicList + index)).path) == 0)
		{
			break;
		}
		index++;
	}
	//char mciStr[100];
	if (norl == 0)
	{
		if (index == 0)
		{
			index = musicQuantity - 1;
		}
		else if (musicQuantity == 1)
		{
			index = 0;
		}
		else
		{
			index--;
		}
		/*
		//停止
		strcpy_s(mciStr, 9, "stop mp3");
		mciSendStringA(mciStr, 0, 0, 0);

		//关闭
		strcpy_s(mciStr ,9, "close mp3");
		mciSendStringA(mciStr, 0, 0, 0);

		//播放上一首
		strcpy_s(mciStr, 6, "open ");
		strcat(mciStr, (*(musicList + index)).path);
		strcat(mciStr, " alias mp3");
		mciSendStringA(mciStr, 0, 0, 0);
		*/

		return (musicList + index);
	}
	else if (norl == 1)
	{
		if (index == musicQuantity - 1)
		{
			index = 0;
		}
		else if (musicQuantity == 1)
		{
			index = 0;
		}
		else
		{
			index++;
		}
		/*
		//停止
		strcpy(mciStr, "stop mp3");
		mciSendStringA(mciStr, 0, 0, 0);

		//关闭
		strcpy(mciStr, "close mp3");
		mciSendStringA(mciStr, 0, 0, 0);

		//播放下一首
		strcpy(mciStr, "open ");
		strcat(mciStr, (*(musicList + index)).path);
		strcat(mciStr, " alias mp3");
		mciSendStringA(mciStr, 0, 0, 0);
		*/
		return (musicList + index);
	}
	else
	{
		return NULL;
	}
}

/*
该函数允许用户对指定路径中的MP3文键进行检索、添加至播放列表，并返回当前播放列表的指针、修改播放列表中的歌曲数量
musicList:播放列表的指针
H:播放列表中的歌曲数目的指针

version:1.2
author:徐含2015141463200
v1.0:2016/7/21
v1.1:2016/7/25 修改返回值为Music*类型、将choice类型修改为int、将参数H的类型修改为int*
v1.2:2016/7/26 修复了无法读入空格的bug、修复了全部添加出错的bug
*/
Music* SearchLocalMusic(Music *musicList,int *H)
{
	//该结构体存放歌曲的路径和标题
	typedef struct
	{
		char filePath[100];
		char fileName[50];
	}TempFileInfo;

	TempFileInfo *list;		//临时列表，存放搜索到的歌曲路径和标题
	Music *musicListBackup = musicList;			//备份musicList
	int musicQuantityBackup = *H;	//备份musicQuantity
	char path[100];			//记录用户输入的路径
	char searchPath[100];	//用于_findfirst函数搜索MP3歌曲
	char mciStr[100];		//mci指令
	int musicCount = 0;		//记录目标路径中的歌曲数量
	int currentListCount = p;			//记录当前播放列表中的歌曲数目
	int addCount = 0;		//记录添加歌曲的数量
	int choice = 0;			//用整型变量存放选项
	printf("键入MP3文件所在文件夹的路径：\n");
	scanf("%[^\n]", path);
	getchar();
	//为路径最后添加'\'
	if (path[strlen(path) - 1] != '\\')
	{
		strcat(path, "\\");
	}
	if (_access(path, 0) != 0)
	{
		printf("路径不存在\n");
		return musicList;
	}

	//检索路径中的歌曲
	strcpy(searchPath, path);
	strcat(searchPath, "*.mp3");
	long Handle;
	_finddata_t FileInfo;
	Handle = _findfirst(searchPath, &FileInfo);
	if (Handle == -1)
	{
		printf("没有找到mp3文件\n");
		return musicList;
	}
	else
	{
		musicCount = 1;
		//若有MP3文件则为list申请空间存放文件路径和名称
		list = (TempFileInfo*)calloc(1, sizeof(TempFileInfo));
		strcpy((*(list)).filePath, path);
		strcat((*(list)).filePath, FileInfo.name);
		strcpy((*(list)).fileName, FileInfo.name);
		printf("1.\t%s\n", FileInfo.name);

		for (; _findnext(Handle, &FileInfo) == 0;)
		{
			musicCount++;
			//每有一个MP3文件就将临时列表空间增加一个TempFileInfo大小
			list = (TempFileInfo*)realloc(list, musicCount*sizeof(TempFileInfo));
			if (list == NULL)
			{
				printf("检索出错！\n\n");
				return musicList;
			}
			//将路径写入临时列表
			strcpy((*(list + musicCount - 1)).filePath, path);
			strcat((*(list + musicCount - 1)).filePath, FileInfo.name);
			//list test(passed)
			//printf("%s\n", (*(list + musicCount - 1)).filePath);
			//将标题写入临时列表
			strcpy((*(list + musicCount - 1)).fileName, FileInfo.name);
			printf("%d.\t%s\n", musicCount, FileInfo.name);
		}

		//test list(passed)
		//for (int i = 0; i < musicCount; i++)
		//{
		//	printf("%s\n%s\n", (*(list + i)).fileName, (*(list + i)).filePath);
		//}

		//允许用户将路径中的歌曲添加至列表
		while (true)
		{
			printf("输入要添加进播放列表的歌曲序号(全部添加输入-1,完成输入0)：");
			scanf("%d", &choice);
			getchar();
			//int digitChoice = choice - '0';
			if (choice == 0)
			{
				printf("添加完成\n\n");

				//通过指针修改播放列表的歌曲数目
				*H = currentListCount;
				return musicList;
			}
			else if (choice > 0 && choice <= musicCount)
			{
				addCount++;
				//为歌曲列表增加addCount个Music大小
				musicList = (Music*)realloc(musicList, (*H + addCount)*sizeof(Music));
				if (musicList == NULL)
				{
					printf("添加出错！\n\n");
					free(list);//释放list
					list = NULL;

					//若添加出错则不修改播放列表中的歌曲数目，下同
					return musicListBackup;
				}
				currentListCount++;//当前播放列表歌曲数目+1
								   //打开歌曲文件
				strcpy(mciStr, "open ");
				strcat(mciStr, (*(list + choice - 1)).filePath);
				strcat(mciStr, " alias mp3");
				mciSendStringA(mciStr, 0, 0, 0);
				//获取总长度
				char str[20] = "";
				strcpy(mciStr, "status mp3 length");
				mciSendStringA(mciStr, str, 20, 0);
				//length test
				//printf("%s\n", str);
				//printf("%ld\n", strtol(str, NULL, 10));
				//向播放列表中填充歌曲信息
				//长度
				(*(musicList + *H + addCount - 1)).length = strtol(str, NULL, 10);
				//标题
				strcpy((*(musicList + *H + addCount - 1)).title, (*(list + choice - 1)).fileName);
				//路径
				strcpy((*(musicList + *H + addCount - 1)).path, (*(list + choice - 1)).filePath);

				//test(passed)
				//printf("%s\n", (*(*musicList + H + addCount - 1)).title);
				//printf("%s\n", (*(*musicList + H + addCount - 1)).path);
				//关闭歌曲文件
				strcpy(mciStr, "close mp3");
				mciSendStringA(mciStr, 0, 0, 0);
				printf("%s 已添加\n", (*(list + choice - 1)).fileName);
			}
			else if (choice == -1)//全部添加
			{
				for (int i = 0; i < musicCount; i++)
				{
					addCount++;
					//申请空间
					musicList = (Music*)realloc(musicList, (*H + addCount)*sizeof(Music));
					if (musicList == NULL)
					{
						printf("添加出错！\n\n");
						free(list);//释放list
						list = NULL;

						return musicListBackup;
					}
					//打开歌曲文件
					strcpy(mciStr, "open ");
					strcat(mciStr, (*(list + choice - 1)).filePath);
					strcat(mciStr, " alias mp3");
					mciSendStringA(mciStr, 0, 0, 0);
					//获取总长度
					char str[20] = "";
					strcpy(mciStr, "status mp3 length");
					mciSendStringA(mciStr, str, 20, 0);
					//向播放列表中填充歌曲信息
					//长度
					(*(musicList + *H + addCount - 1)).length = strtol(str, NULL, 10);
					//标题
					strcpy((*(musicList + *H+ addCount - 1)).title, (*(list + i)).fileName);
					//路径
					strcpy((*(musicList + *H + addCount - 1)).path, (*(list + i)).filePath);
					//关闭歌曲文件
					strcpy(mciStr, "close mp3");
					mciSendStringA(mciStr, 0, 0, 0);
				}
				currentListCount += musicCount;//当前歌曲数目加上路径中的歌曲数目
				printf("全部添加完成\n\n");

				free(list);//释放list
				list = NULL;

				*H = currentListCount;
				return musicList;
			}
			else
			{
				printf("无效选项\n");
			}
		}
	}

	*H = currentListCount;
	return musicList;
}

/*
此函数将txt文件中的歌曲列表读取到MusicList中

version:1.0
author:徐含2015141463200
*/
void LoadMusicList(int listNumber)
{
	Music* musicListBackup = musicList;
	FILE *fp;
	char filePath[100] = "";
	p = 0;
	switch (listNumber)
	{
	case 1:
		strcpy(filePath, "mp3.1.txt");
		break;
	case 2:
		strcpy(filePath, "mp3.2.txt");
		break;
	case 3:
		strcpy(filePath, "mp3.3.txt");
		break;
	default:
		strcpy(filePath, "mp3.4.txt");
		break;
	}

	fp = fopen(filePath, "a+");
	if (fp == NULL)
	{
		printf("播放列表加载失败\n\n");
		return;
	}

	//先执行一次读文件
	musicListBackup = musicList;	//备份musicList
	musicList = (Music*)realloc(musicList, (p + 1)*sizeof(Music));
	if (musicList == NULL)
	{
		printf("播放列表加载错误\n\n");
		musicList = musicListBackup;
	}

	if (!feof(fp))
	{
		char temp[100];
		fgets((*(musicList + p)).path, 100, fp);
		if (feof(fp))
		{
			temp[0] = '\0';
		}
		else
		{
			strcpy(temp, (*(musicList + p)).path);
			if ((*(musicList + p)).path[strlen((*(musicList + p)).path) - 1] == '\n')
			{
				(*(musicList + p)).path[strlen((*(musicList + p)).path) - 1] = '\0';
			}
			fgets((*(musicList + p)).title, 50, fp);
			strcpy(temp, (*(musicList + p)).title);
			if ((*(musicList + p)).title[strlen((*(musicList + p)).title) - 1] == '\n')
			{
				(*(musicList + p)).title[strlen((*(musicList + p)).title) - 1] = '\0';
			}
		}
	}
	//然后进行循环，避免使用feof(fp)后多循环一次
	while (!feof(fp))
	{
		p++;
		musicListBackup = musicList;	//备份musicList
		musicList = (Music*)realloc(musicList, (p + 1)*sizeof(Music));
		if (musicList == NULL)
		{
			printf("播放列表加载错误\n\n");
			musicList = musicListBackup;
		}

		char temp[100];
		fgets((*(musicList + p)).path, 100, fp);
		if (feof(fp))
		{
			temp[0] = '\0';
			break;
		}
		strcpy(temp, (*(musicList + p)).path);
		if ((*(musicList + p)).path[strlen((*(musicList + p)).path) - 1] == '\n')
		{
			(*(musicList + p)).path[strlen((*(musicList + p)).path) - 1] = '\0';
		}
		fgets((*(musicList + p)).title, 50, fp);
		strcpy(temp, (*(musicList + p)).title);
		if ((*(musicList + p)).title[strlen((*(musicList + p)).title) - 1] == '\n')
		{
			(*(musicList + p)).title[strlen((*(musicList + p)).title) - 1] = '\0';
		}
	}

	fclose(fp);
	return;
}

/*
此函数用于保存修改后的播放列表

version:1.0
author:徐含2015141463200
*/
void SaveMusicList(int listNumber)
{
	FILE *fp;
	char filePath[100] = "";
	switch (listNumber)
	{
	case 1:
		strcpy(filePath, "mp3.1.txt");
		break;
	case 2:
		strcpy(filePath, "mp3.2.txt");
		break;
	case 3:
		strcpy(filePath, "mp3.3.txt");
		break;
	default:
		strcpy(filePath, "mp3.txt");
		break;
	}

	fp = fopen(filePath, "w+");
	if (fp == NULL)
	{
		printf("播放列表保存失败\n\n");
		return;
	}
	for (int i = 0; i < p; i++)
	{
		if (strcmp((*(musicList + i)).path, "") != 0)
		{
			fputs((*(musicList + i)).path, fp);
			fputs("\n", fp);
			fputs((*(musicList + i)).title, fp);
			fputs("\n", fp);
		}
	}

	fclose(fp);
	return;
}

/*
此函数用于各种模式的播放

version:1.0
author:徐含
v1.0:2016/7/27
*/
void Play()
{
	playStatu = 1;
	int musicIndex = 0;
	//char str[20];
	currentMusic = *(musicList + musicIndex);
	srand(time(NULL));
	while (playStatu != 0)
	{
		if (playMode == 4)
		{
			musicIndex = rand() % p;
			currentMusic = *(musicList + musicIndex);
		}
		//PLAYMUSIC = CreateThread(NULL, 0, PlayMusicThread, NULL, 0, NULL);
		//checkStop = CreateThread(NULL, 0, checkStopFun, NULL, 0, NULL);

		//unsigned long position = 0;
		//unsigned long length = 1;
		//char mciStr[50];
		//SetTimer(NULL, 1, 1000, (TIMERPROC)checkStopFun);
		playStatu = PlayMusic(currentMusic.path);
		//while (playStatu == 1)
		//{
		//	Sleep(1000);
		//}
		//TerminateThread(PlayMusicThread, NULL);
		//CloseHandle(PlayMusicThread);
		if (playStatu == 2)
		{
			playStatu = 1;
		}
		else if (playMode == 1)
		{
			playStatu = 0;
		}
		else if (playMode == 2)
		{
			playStatu = 1;
		}
		else if (playMode == 3)
		{
			currentMusic = *ChangeMusic(musicList, currentMusic, p, 1);
		}
		else
		{
			playStatu = 0;
		}
	}
	
}


void checkStopFun()
{
	while (true)
	{
		Sleep(1000);
		//获取播放位置
		strcpy(mciStr, "status mp3 mode");
		mciSendStringA(mciStr, str, 20, 0);
		if (strcmp(str,"stopped")==0)
		{
			playStatu = 0;
			break;
		}
	}

	return;
}


DWORD WINAPI PlayMenuThread(LPVOID lParam)
{
	int playStatu = 1;
	while (playStatu == 1)
	{
		playStatu = PlayMenu();
	}
	return 0;
}
#define _CRT_SECURE_NO_WARNINGS
#define _CRT_SECURE_NO_DEPRECATE
#include <stdio.h> 
#include <stdlib.h> 
#include <string.h> 
#include <windows.h> 
#include <io.h>
#include <ctype.h>

#pragma comment(lib, "WINMM.LIB") 

typedef struct
{
	char title[50];
	long length;
	char path[100];
}Music;
int playMode = 0;
Music currentMusic;

void displayMusicInfo(Music music);
void changePlayMode();
Music* changeMusic(Music *musicList, int musicQuantity, int norl);
int searchLocalMusic(Music *musicList, int musicQuantity);

/*
该函数用于显示歌曲信息

author:徐含2015141463200
version:1.0
*/
void displayMusicInfo(Music music)
{
	printf("歌曲标题：%s\n", music.title);
	printf("歌曲长度：%d:%d\n", music.length / 1000 / 60, (music.length / 1000) % 60);
	printf("歌曲路径：%s\n", music.path);
}

/*
该函数用于改变播放模式

author:徐含2015141463200
version:1.0
*/
void changePlayMode()
{
	char mode;
	while (mode != '1' || mode != '2' || mode != '3' || mode != '4')
	{
		printf("1.播放单曲 2.单曲循环 3.列表循环 4.随机循环\n");
		printf("选择播放模式：");
		scanf_s("%c", &mode,1);
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
			printf("No choice of %c", mode);
			break;
		}
	}
}

/*
该函数用于播放上/下一首歌曲，并返回该歌曲的指针
musicList:歌曲列表头指针
musicQuantity:列表中的歌曲数量
norl:值为0时播放上一首，值为1时播放下一首

author:徐含2015141463200
version:1.0
*/
Music* changeMusic(Music *musicList, int musicQuantity, int norl)
{
	if (musicQuantity <= 0)
	{
		printf("musicQuantity error\n\n");
		return NULL;
	}
	int index = 0;	//记录当前播放歌曲所在位置
	char mciStr[100];
	if (norl == 0)
	{
		for (index = 0; index < musicQuantity; index++)
		{
			if (strcmp(currentMusic.path, (*(musicList + index)).path))
			{
				break;
			}
		}

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
		//停止
		strcpy(mciStr, "stop mp3");
		mciSendStringA(mciStr, 0, 0, 0);

		//关闭
		strcpy(mciStr, "close mp3");
		mciSendStringA(mciStr, 0, 0, 0);

		//播放上一首
		strcpy(mciStr, "open ");
		strcat(mciStr, (*(musicList + index)).path);
		strcat(mciStr, " alias mp3");
		mciSendStringA(mciStr, 0, 0, 0);

		return (musicList + index);
	}
	else if (norl == 1)
	{
		for (int i = 0; i < musicQuantity; i++)
		{
			if (strcmp(currentMusic.path, (*(musicList + i)).path))
			{
				break;
			}
		}

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

		return (musicList + index);
	}
	else
	{
		return;
	}
}

/*
该函数允许用户对指定路径中的MP3文键进行检索，若检索失败则返回-1，若检索成功则返回0，若没有文件则返回1
musicList:播放列表的指针
musicQuantity:播放列表中的歌曲数目

author:徐含2015141463200
version:1.0
*/
int searchLocalMusic(Music *musicList, int musicQuantity)
{
	//该结构体存放歌曲的路径和标题
	typedef struct
	{
		char filePath[100];
		char fileName[50];
	}TempFileInfo;

	TempFileInfo *list;		//临时列表，存放搜索到的歌曲路径和标题
	char path[100];			//记录用户输入的路径
	char searchPath[100];	//用于_findfirst函数搜索MP3歌曲
	char mciStr[100];		//mci指令
	int musicCount = 0;		//记录目标路径中的歌曲数量
	char choice;			//用字符变量存放选项
	printf("键入MP3文件所在文件夹的路径：\n");
	scanf("%s", path);
	if (access(path, 0) != 0)
	{
		printf("路径不存在\n");
		return;
	}

	//检索路径中的歌曲
	strcpy(searchPath, path);
	strcat(searchPath, "*.txt");
	long Handle;
	_finddata_t FileInfo;
	if ((Handle = _findfirst(searchPath, &FileInfo)) == -1L)
	{
		printf("没有找到mp3文件\n");
		return 1;
	}
	else
	{
		musicCount = 1;
		//若有MP3文件则为list申请空间存放文件路径和名称
		list = (TempFileInfo*)calloc(1, sizeof(TempFileInfo));
		strcpy((*(list)).filePath, path);
		strcat((*(list)).filePath, FileInfo.name);
		strcpy((*(list)).fileName, FileInfo.name);
		printf("1. %s", FileInfo.name);

		for (; _findnext(Handle, &FileInfo) == 0;)
		{
			musicCount++;
			//每有一个MP3文件就将临时列表空间增加一个TempFileInfo大小
			list = (TempFileInfo*)realloc(list, musicCount*sizeof(TempFileInfo));
			if (list == NULL)
			{
				printf("检索出错！\n\n");
				return -1;
			}
			//将路径写入临时列表
			strcpy((*(list + musicCount - 1)).filePath, path);
			strcat((*(list + musicCount - 1)).filePath, FileInfo.name);
			//将标题写入临时列表
			strcpy((*(list + musicCount - 1)).fileName, FileInfo.name);
			printf("%d. %s\n", musicCount, FileInfo.name);
		}

		//允许用户将路径中的歌曲添加至列表
		while (true)
		{
			int addCount = 0;//记录添加歌曲的数量
			printf("输入要添加进播放列表的歌曲序号(全部添加输入“a”,完成输入0)：");
			scanf("%c", &choice);
			int digitChoice = choice - '0';
			if (digitChoice == 0)
			{
				printf("添加完成\n");
				return 0;
			}
			else if (digitChoice > 0 && digitChoice <= musicCount)
			{
				addCount++;
				//为歌曲列表增加addCount个Music大小
				musicList = (Music*)realloc(musicList, (musicQuantity + addCount)*sizeof(Music));
				if (musicList == NULL)
				{
					printf("添加出错！\n\n");
					free(list);//释放list
					list = NULL;
					return -1;
				}
				//打开歌曲文件
				strcpy(mciStr, "open ");
				strcat(mciStr, (*(list + digitChoice - 1)).filePath);
				strcat(mciStr, " alias mp3");
				mciSendStringA(mciStr, 0, 0, 0);
				//获取总长度
				char str[20] = "";
				strcpy(mciStr, "status mp3 length");
				mciSendStringA(mciStr, str, 20, 0);
				//向播放列表中填充歌曲信息
				//长度
				(*(musicList + musicQuantity + addCount - 1)).length = strtol(str, NULL, 10);
				//标题
				strcpy((*(musicList + musicQuantity + addCount - 1)).title, (*(list + digitChoice - 1)).fileName);
				//路径
				strcpy((*(musicList + musicQuantity + addCount - 1)).path, (*(list + digitChoice - 1)).filePath);
				//关闭歌曲文件
				strcpy(mciStr, "close mp3");
				mciSendStringA(mciStr, 0, 0, 0);
				printf("%s 已添加\n", (*(list + digitChoice - 1)).fileName);
			}
			else if (choice == 'a')//全部添加
			{
				musicList = (Music*)realloc(musicList, (musicQuantity + musicCount)*sizeof(Music));
				if (musicList == NULL)
				{
					printf("添加出错！\n\n");
					free(list);//释放list
					list = NULL;
					return -1;
				}
				for (int i = 0; i < musicCount; i++)
				{
					//打开歌曲文件
					strcpy(mciStr, "open ");
					strcat(mciStr, (*(list + digitChoice - 1)).filePath);
					strcat(mciStr, " alias mp3");
					mciSendStringA(mciStr, 0, 0, 0);
					//获取总长度
					char str[20] = "";
					strcpy(mciStr, "status mp3 length");
					mciSendStringA(mciStr, str, 20, 0);
					//向播放列表中填充歌曲信息
					//长度
					(*(musicList + musicQuantity + addCount - 1)).length = strtol(str, NULL, 10);
					//标题
					strcpy((*(musicList + musicQuantity + addCount - 1)).title, (*(list + digitChoice - 1)).fileName);
					//路径
					strcpy((*(musicList + musicQuantity + addCount - 1)).path, (*(list + digitChoice - 1)).filePath);
					//关闭歌曲文件
					strcpy(mciStr, "close mp3");
					mciSendStringA(mciStr, 0, 0, 0);
				}

				free(list);//释放list
				list = NULL;
				return 0;
			}
			else
			{
				printf("无效选项\n");
			}
		}
	}

	return 0;
}

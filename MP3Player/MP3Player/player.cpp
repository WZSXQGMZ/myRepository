#pragma once
#define _CRT_SECURE_NO_WARNINGS
#define _CRT_SECURE_NO_DEPRECATE
#include <stdio.h> 
#include <stdlib.h> 
#include <string.h> 
#include <windows.h> 
#include <io.h>
#include <ctype.h>
#include "MP3Player.h"

#pragma comment(lib, "WINMM.LIB") 

int playMode = 0;
Music currentMusic;

void displayMusicInfo(Music music);
void changePlayMode();
Music* changeMusic(Music *musicList, int musicQuantity, int norl);
Music* searchLocalMusic(Music *musicList, int *musicQuantity);

/*
//Test
Music *musicList;//播放列表
int musicNumber = 0;
bool searchLocalMusicTest();
bool changeMusicTest();
void main()
{
	searchLocalMusicTest();
	system("pause");
	changeMusicTest();
	system("pause");

	free(musicList);
	musicList = NULL;
}
bool searchLocalMusicTest()
{
	musicList = (Music*)calloc(1, sizeof(Music));
	musicList = searchLocalMusic(musicList, &musicNumber);
	for (int i = 0; i < musicNumber; i++)
	{
		displayMusicInfo(*(musicList + i));
	}

	return true;
}
bool changeMusicTest()
{
	char mciStr[100] = "";
	currentMusic = *(musicList);
	displayMusicInfo(currentMusic);

	strcpy_s(mciStr, 6, "open ");
	strcat(mciStr, currentMusic.path);
	strcat(mciStr, " alias mp3");
	mciSendStringA(mciStr, 0, 0, 0);
	strcpy(mciStr, "play mp3");
	mciSendStringA(mciStr, 0, 0, 0);
	system("pause");

	currentMusic = *changeMusic(musicList, musicNumber, 0);
	displayMusicInfo(currentMusic);
	strcpy_s(mciStr, 6, "open ");
	strcat(mciStr, currentMusic.path);
	strcat(mciStr, " alias mp3");
	mciSendStringA(mciStr, 0, 0, 0);
	strcpy(mciStr, "play mp3");
	mciSendStringA(mciStr, 0, 0, 0);
	system("pause");

	currentMusic = *changeMusic(musicList, musicNumber, 1);
	displayMusicInfo(currentMusic);
	strcpy_s(mciStr, 6, "open ");
	strcat(mciStr, currentMusic.path);
	strcat(mciStr, " alias mp3");
	mciSendStringA(mciStr, 0, 0, 0);
	strcpy(mciStr, "play mp3");
	mciSendStringA(mciStr, 0, 0, 0);
	system("pause");

	return true;
}
//TestEnd
*/

/*
该函数用于显示歌曲信息
music:要显示信息的歌曲

author:徐含2015141463200
version:1.0
*/
void displayMusicInfo(Music music)
{
	printf("歌曲标题：%s\n", music.title);
	printf("歌曲长度：%d:%d\n", music.length / 1000 / 60, (music.length / 1000) % 60);
	printf("歌曲路径：%s\n\n", music.path);
}

/*
该函数用于改变播放模式

author:徐含2015141463200
version:1.0
*/
void changePlayMode()
{
	char mode='0';
	while (mode != '1' || mode != '2' || mode != '3' || mode != '4')
	{
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
version:1.1
V1.1:2016/7/25 修改了函数的返回类型为Music*
*/
Music* changeMusic(Music *musicList, int musicQuantity, int norl)
{
	if (musicQuantity <= 0)
	{
		printf("musicQuantity error\n\n");
		return NULL;
	}
	int index = 0;	//记录当前播放歌曲所在位置
	for (index = 0; index < musicQuantity;)
	{
		if (strcmp(currentMusic.path, (*(musicList + index)).path)==0)
		{
			break;
		}
		index++;
	}
	char mciStr[100];
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
musicQuantity:播放列表中的歌曲数目的指针

author:徐含2015141463200
version:1.2
v1.0:2016/7/21
v1.1:2016/7/25修改返回值为Music*类型、将choice类型修改为int、将参数musicQuantity的类型修改为int*
v1.2:2016/7/26修复了无法读入空格的bug、修复了全部添加出错的bug
*/
Music* searchLocalMusic(Music *musicList, int *musicQuantity)
{
	//该结构体存放歌曲的路径和标题
	typedef struct
	{
		char filePath[100];
		char fileName[50];
	}TempFileInfo;

	TempFileInfo *list;		//临时列表，存放搜索到的歌曲路径和标题
	Music *musicListBackup = musicList;			//备份musicList
	int musicQuantityBackup = *musicQuantity;	//备份musicQuantity
	char path[100];			//记录用户输入的路径
	char searchPath[100];	//用于_findfirst函数搜索MP3歌曲
	char mciStr[100];		//mci指令
	int musicCount = 0;		//记录目标路径中的歌曲数量
	int currentListCount = musicCount;			//记录当前播放列表中的歌曲数目
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
				printf("添加完成\n");

				//通过指针修改播放列表的歌曲数目
				*musicQuantity = currentListCount;
				return musicList;
			}
			else if (choice > 0 && choice <= musicCount)
			{
				addCount++;
				//为歌曲列表增加addCount个Music大小
				musicList = (Music*)realloc(musicList, (*musicQuantity + addCount)*sizeof(Music));
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
				(*(musicList + *musicQuantity + addCount - 1)).length = strtol(str, NULL, 10);
				//标题
				strcpy((*(musicList + *musicQuantity + addCount - 1)).title, (*(list + choice - 1)).fileName);
				//路径
				strcpy((*(musicList + *musicQuantity + addCount - 1)).path, (*(list + choice - 1)).filePath);

				//test(passed)
				//printf("%s\n", (*(*musicList + musicQuantity + addCount - 1)).title);
				//printf("%s\n", (*(*musicList + musicQuantity + addCount - 1)).path);
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
					musicList = (Music*)realloc(musicList, (*musicQuantity + addCount)*sizeof(Music));
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
					(*(musicList + *musicQuantity + addCount - 1)).length = strtol(str, NULL, 10);
					//标题
					strcpy((*(musicList + *musicQuantity + addCount - 1)).title, (*(list + i)).fileName);
					//路径
					strcpy((*(musicList + *musicQuantity + addCount - 1)).path, (*(list + i)).filePath);
					//关闭歌曲文件
					strcpy(mciStr, "close mp3");
					mciSendStringA(mciStr, 0, 0, 0);
				}
				currentListCount += musicCount;//当前歌曲数目加上路径中的歌曲数目
				printf("全部添加完成\n");

				free(list);//释放list
				list = NULL;

				*musicQuantity = currentListCount;
				return musicList;
			}
			else
			{
				printf("无效选项\n");
			}
		}
	}

	*musicQuantity = currentListCount;
	return musicList;
}
